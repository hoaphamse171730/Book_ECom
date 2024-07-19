package com.tbsd.crawler.generator;

import com.tbsd.crawler.data.BookIndexManager;
import com.tbsd.crawler.data.BookManager;
import com.tbsd.crawler.data.CategoryManager;
import com.tbsd.crawler.model.*;
import com.tbsd.crawler.util.DBUtils;
import com.tbsd.crawler.util.FakeUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static java.util.concurrent.ThreadLocalRandom.current;

public class UserGenerator {

    public static int generate() {
        List<FakeUser> users = new ArrayList<>();
        try {
            List<Book> books = new ArrayList<>();
            Map<Integer, Integer> dbIdToBookId = new HashMap<>();

            // generate fake users based on book reviews
            for (Map.Entry<Integer, Category> e : CategoryManager.getCategory().entrySet()) {
                for (long id : BookIndexManager.getBooks(e.getKey())) {
                    Book book = BookManager.getBook(id);
                    dbIdToBookId.put(book.dbId, books.size());
                    books.add(book);
                    if (book.reviews == null)
                        continue;
                    for (Review review : book.reviews) {
                        String cnt = review.content;
                        if (cnt.isEmpty()) cnt = review.title;
                        FakeUser person;
                        if (users.size() < 10 || current().nextFloat() < 0.1) {
                            users.add(person = new FakeUser());
                            if (users.size() % 500 == 0)
                                System.out.println("Preparing fake users: "+users.size());
                        } else
                            person = users.get(current().nextInt(users.size()));
                        person.createdAt = Math.min(review.createdAt, person.createdAt);
                        person.feedbacks.add(new FakeFeedback(book.dbId, review.rating.intValue(), cnt, review.createdAt*1000));
                        if (ThreadLocalRandom.current().nextFloat() < 0.3)
                            person.favourites.add(book.dbId);
                    }
                }
            }

            System.out.println("Preparing fake users: "+users.size());
            System.gc();

            // generate fake users that have not reviews
            for (int i = 0; i < users.size() / 2; i++) {
                users.add(new FakeUser());
                if (users.size() % 500 == 0)
                    System.out.println("Preparing fake users: "+users.size());
            }

            System.out.println("Total prepared fake users: "+users.size());
            System.gc();

            // add more favourites
            System.out.println("Preparing favourites");
            for (FakeUser user : users) {
                for (int j = 0; j < current().nextInt(0, 20); j++) {
                    Book book = books.get(current().nextInt(books.size()));
                    user.favourites.add(book.dbId);
                    user.createdAt = Math.max(book.createdAt, user.createdAt);
                }
            }

            // populate user profiles
            System.out.println("Preparing profiles");
            FakeProfile[] profiles = FakeUtil.generate(users.size());
            for (int i = 0; i < users.size(); i++) {
                FakeUser user = users.get(i);
                user.profile = profiles[i];
            }

            System.gc();
            Connection conn = DBUtils.getConnection();

            System.out.println("Generating data");
            for (int j = 0; j < users.size(); j++) {
                if (j % 500 == 0)
                    System.out.println("Generated: "+j + "/" + users.size());
                FakeUser user = users.get(j);
                List<FakeOrder> orderListMap = new ArrayList<>();
                // generate orders
                {
                    List<FakeOrderDetail> details = new ArrayList<>();

                    // create order detail based on feedback
                    for (FakeFeedback feedback : user.feedbacks) {
                        Book book = books.get(dbIdToBookId.get(feedback.product));
                        details.add(new FakeOrderDetail(
                                feedback.product,
                                current().nextFloat() < 0.05 ? current().nextInt(1, 4) : 1,
                                (int) book.price,
                                book.createdAt
                        ));
                    }

                    // create order detail without feedback
                    for (int i = 0; i < details.size() / 2 + 1; i++) {
                        Book book = books.get(current().nextInt(books.size()));
                        details.add(new FakeOrderDetail(
                                book.dbId,
                                current().nextFloat() < 0.05 ? current().nextInt(1, 4) : 1,
                                (int) book.price,
                                book.createdAt
                        ));
                        user.createdAt = Math.max(book.createdAt, user.createdAt);
                    }

                    Collections.shuffle(details);
                    orderListMap.add(new FakeOrder());

                    for (FakeOrderDetail detail : details) {
                        FakeOrder order = orderListMap.get(orderListMap.size() - 1);
                        if (order.products.contains(detail.product)
                                || order.products.size() > current().nextInt(1, 4)) {
                            orderListMap.add(order = new FakeOrder());
                        }
                        order.products.add(detail.product);
                        order.createdAt = Math.max(order.createdAt, detail.productCreatedAt);
                        user.createdAt = Math.min(order.createdAt, user.createdAt);
                        order.details.add(detail);
                    }
                }

                // generate user
                try {
                    PreparedStatement st = conn.prepareStatement(
                            "insert into [user] (fullname, email, [password], gender, [address], phone_number, [role], created_at)" +
                            "values (?, ?, ?, ?, ?, ?, ?, ?)",
                            Statement.RETURN_GENERATED_KEYS);
                    st.setString(1, user.profile.name);
                    st.setString(2, user.profile.email);
                    st.setString(3, DigestUtils.sha256Hex(user.profile.password));
                    st.setString(4, user.profile.gender.toUpperCase());
                    st.setString(5, user.profile.address);
                    st.setString(6, user.profile.phone);
                    st.setString(7, "USER");
                    st.setDate(8, new Date(user.createdAt));
                    if (st.executeUpdate() > 0) {
                        ResultSet rs = st.getGeneratedKeys();
                        if (rs.next())
                            user.dbId = rs.getInt(1);
                    }
                    st.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                // generate favourites
                try {
                    for (int favourite : user.favourites) {
                        long offset = 1000 * 60 * 60;
                        offset *= current().nextInt(1, 24 * 30);
                        PreparedStatement st = conn.prepareStatement("insert into favorite (product_id, user_id, created_at) values (?, ?, ?)");
                        st.setInt(1, favourite);
                        st.setInt(2, user.dbId);
                        st.setDate(3, new Date(user.createdAt + offset));
                        st.executeUpdate();
                        st.close();
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                // generate feedbacks
                try {
                    for (FakeFeedback feedback : user.feedbacks) {
                        long offset = 1000 * 60 * 60;
                        offset *= current().nextInt(1, 24 * 7);
                        PreparedStatement st = conn.prepareStatement(
                                "insert into feedback (product_id, user_id, rating, content, created_at) values (?, ?, ?, ?, ?)");
                        st.setInt(1, feedback.product);
                        st.setInt(2, user.dbId);
                        st.setInt(3, feedback.rating);
                        st.setString(4, StringUtils.truncate(feedback.comment, 3000));
                        st.setDate(5, new Date(feedback.createdAt + offset));
                        st.executeUpdate();
                        st.close();
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                // generate orders
                for (FakeOrder order : orderListMap) {
                    int totalPrice = order.details.stream().mapToInt(v -> v.price * v.quantity).sum();

                    try {
                        PreparedStatement st = conn.prepareStatement(
                                "insert into [order] ([user_id], total_price, [status], created_at) values (?, ?, ?, ?)",
                                Statement.RETURN_GENERATED_KEYS);
                        st.setInt(1, user.dbId);
                        st.setInt(2, totalPrice);
                        st.setString(3, randomOrderStatus());
                        st.setDate(4, new Date(order.createdAt));
                        if (st.executeUpdate() > 0) {
                            ResultSet rs = st.getGeneratedKeys();
                            if (rs.next())
                                order.dbId = rs.getInt(1);
                        }
                        st.close();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                    for (FakeOrderDetail detail : order.details) {
                        try {
                            PreparedStatement st = conn.prepareStatement(
                                    "insert into order_detail ([order_id], product_id, price, amount) values (?, ?, ?, ?) ");
                            st.setInt(1, order.dbId);
                            st.setInt(2, detail.product);
                            st.setInt(3, detail.price);
                            st.setInt(4, detail.quantity);
                            st.executeUpdate();
                            st.close();
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return users.size();
    }

    private static String randomOrderStatus() {
        return current().nextFloat() > 0.05 ? "COMPLETED" : "CANCELLED";
    }
}
