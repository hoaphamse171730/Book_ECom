package com.tbsd.crawler.generator;

import com.tbsd.crawler.data.BookIndexManager;
import com.tbsd.crawler.data.BookManager;
import com.tbsd.crawler.data.CategoryManager;
import com.tbsd.crawler.model.Book;
import com.tbsd.crawler.model.Category;
import com.tbsd.crawler.util.DBUtils;
import com.tbsd.crawler.util.DateUtil;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class BookGenerator {
    public static int generate() {
        int count = 0;
        Map<Integer, Integer> tikiIdToDbId = new HashMap<>();
        Connection conn = DBUtils.getConnection();
        for (Map.Entry<Integer, Category> e : CategoryManager.getCategory().entrySet()) {
            for (long id : BookIndexManager.getBooks(e.getKey())) {
                try {
                    Book book = BookManager.getBook(id);

                    if (book.dbId != null) {
                        PreparedStatement st = conn.prepareStatement(
                                "SELECT 1 FROM product WHERE product_id = ?;"
                        );
                        st.setInt(1, book.dbId);
                        boolean exist = st.executeQuery().next();
                        st.close();
                        if (exist) continue;
                        book.dbId = null;
                    }

                    if (book.authors != null) {
                        for (Book.Author author : book.authors) {
                            if (author.dbId != null) {
                                PreparedStatement st = conn.prepareStatement(
                                        "SELECT 1 FROM author WHERE author_id = ?;"
                                );
                                st.setInt(1, author.dbId);
                                boolean exist = st.executeQuery().next();
                                st.close();
                                if (exist) {
                                    tikiIdToDbId.put(author.id, author.dbId);
                                    continue;
                                }
                            }
                            if (tikiIdToDbId.containsKey(author.id)) {
                                author.dbId = tikiIdToDbId.get(author.id);
                                continue;
                            }
                            PreparedStatement st = conn.prepareStatement(
                                    "insert into author (name) values (?)",
                                    Statement.RETURN_GENERATED_KEYS
                            );
                            st.setString(1, author.name);
                            if (st.executeUpdate() > 0) {
                                ResultSet rs = st.getGeneratedKeys();
                                if (rs.next()) {
                                    author.dbId = rs.getInt(1);
                                    tikiIdToDbId.put(author.id, author.dbId);
                                }
                            }
                            st.close();
                        }
                    }

                    long createdAt = System.currentTimeMillis();

                    if (book.dbId == null) {
                        Date publishDate = DateUtil.fromTikiDate(book.getAttribute("Ngày xuất bản"));
                        long delayPublish = 1000 * 60 * 60 * 24;
                        delayPublish *= ThreadLocalRandom.current().nextInt(3, 30);
                        Date createAt = new Date(publishDate.getTime() + delayPublish);
                        createdAt = createAt.getTime();
                        PreparedStatement st = conn.prepareStatement(
                                "insert into product (category_id, name, short_description, description, price, " +
                                        "manufacturer, publisher, size, format, pages, remain, publish_day, created_at) " +
                                        "values (?,?,?,?,?,?,?,?,?,?,?,?,?)",
                                Statement.RETURN_GENERATED_KEYS);
                        st.setInt(1, e.getValue().dbId());
                        st.setString(2, book.name);
                        st.setString(3, book.shortDescription);
                        st.setString(4, book.description);
                        st.setInt(5, (int) book.price);
                        st.setString(6, book.getAttribute("Công ty phát hành"));
                        st.setString(7, book.getAttribute("Nhà xuất bản"));
                        st.setString(8, book.getAttribute("Kích thước"));
                        st.setString(9, book.getAttribute("Loại bìa"));
                        st.setString(10, book.getAttribute("Số trang"));
                        st.setInt(11, book.stockItem.qty);
                        st.setDate(12, publishDate);
                        st.setDate(13, createAt);
                        if (st.executeUpdate() > 0) {
                            ResultSet rs = st.getGeneratedKeys();
                            if (rs.next())
                                book.dbId = rs.getInt(1);
                        }
                        st.close();
                    }

                    if (book.authors != null) {
                        for (Book.Author author : book.authors) {
                            if (author.dbId == null) continue;
                            PreparedStatement st = conn.prepareStatement(
                                    "SELECT 1 FROM authors_of_product WHERE author_id = ? AND product_id = ?;"
                            );
                            st.setInt(1, author.dbId);
                            st.setInt(2, book.dbId);
                            boolean exist = st.executeQuery().next();
                            st.close();
                            if (exist) continue;

                            st = conn.prepareStatement("insert into authors_of_product (author_id, product_id) values (?, ?)");
                            st.setInt(1, author.dbId);
                            st.setInt(2, book.dbId);
                            st.executeUpdate();
                            st.close();
                        }
                    }

                    for (Book.Image image : book.images) {
                        if (image.dbId != null) {
                            PreparedStatement st = conn.prepareStatement(
                                    "SELECT 1 FROM image WHERE image_id = ?;"
                            );
                            st.setInt(1, image.dbId);
                            boolean exist = st.executeQuery().next();
                            st.close();
                            if (exist) continue;
                        }
                        PreparedStatement st = conn.prepareStatement(
                                "insert into image (product_id, url, created_at) values (?, ?, ?)",
                                Statement.RETURN_GENERATED_KEYS);
                        st.setInt(1, book.dbId);
                        st.setString(2, image.baseURL);
                        st.setDate(3, new Date(createdAt));
                        if (st.executeUpdate() > 0) {
                            ResultSet rs = st.getGeneratedKeys();
                            if (rs.next())
                                image.dbId = rs.getInt(1);
                        }
                        st.close();
                    }

                    book.createdAt = createdAt;
                    BookManager.saveData(book);
                    count++;
                } catch (Exception err) {
                    throw new RuntimeException("Error at book " + id, err);
                }
            }
        }
        try {
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return count;
    }
}
