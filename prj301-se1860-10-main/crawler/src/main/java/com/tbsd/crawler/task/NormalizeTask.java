package com.tbsd.crawler.task;

import com.tbsd.crawler.data.BookIndexManager;
import com.tbsd.crawler.data.BookManager;
import com.tbsd.crawler.data.CategoryManager;
import com.tbsd.crawler.model.Book;
import com.tbsd.crawler.model.Category;
import com.tbsd.crawler.model.Review;
import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;

import java.util.*;

import static java.util.concurrent.ThreadLocalRandom.current;

public class NormalizeTask {
    public static void start() throws Exception {
        Set<Long> uniqueReviews = new HashSet<>();
        for (Map.Entry<Integer, Category> e : CategoryManager.getCategory().entrySet()) {
            for (long id : BookIndexManager.getBooks(e.getKey())) {
                Book book = BookManager.getBook(id);

                for (Review review : book.reviews) {
                    uniqueReviews.add(review.customerId);
                }

                String name =  book.name;
                if (name != null && name.length() > 150)
                    System.out.println("Book " + id + " has too long name " + name);

                String publishDate = book.getAttribute("Ngày xuất bản");
                if (publishDate == null || publishDate.isEmpty()) {
                    Long newDate = null;
                    if (book.reviews != null) {
                        newDate = book.reviews.stream()
                                .map(review -> review.createdAt)
                                .min(Comparator.naturalOrder())
                                .map(v -> v * 1000L)
                                .map(v -> v - 1000L * 60 * 60 * 24 * current().nextInt(3, 14))
                                .orElse(null);
                    }
                    if (newDate == null)
                        newDate = System.currentTimeMillis();
                    book.setAttribute("Ngày xuất bản", new Date(newDate).toString());
                }

                String manufacture =  book.getAttribute("Công ty phát hành");
                if (manufacture != null && manufacture.length() > 50)
                    System.out.println("Book " + id + " has too long manufacture " + manufacture);

                String publisher = book.getAttribute("Nhà xuất bản");
                if (publisher != null && publisher.length() > 50)
                    System.out.println("Book " + id + " has too long publisher " + publisher);

                String size = book.getAttribute("Kích thước");
                if (size != null && !size.isEmpty()) {
                    size = StringEscapeUtils.unescapeJava(size);
                    size = Jsoup.parse(size).text();
                    book.setAttribute("Kích thước", size);
                    if (size.length() > 30)
                        System.out.println("Book " + id + " has too long size " + size);
                }

                String format = book.getAttribute("Loại bìa");
                if (format != null && format.length() > 15)
                    System.out.println("Book " + id + " has too long format " + format);

                String pages = book.getAttribute("Số trang");
                if (pages != null && !pages.isEmpty() && !pages.matches("[0-9]+"))
                    System.out.println("Book " + id + " has invalid pages");

                if (book.authors != null) {
                    for (Book.Author author : book.authors) {
                        if (author.name.length() > 30)
                            System.out.println("Book " + id + " has too long author " + author.name);
                    }
                }

                BookManager.saveData(book);
            }
        }

        System.out.println("Unique review users: " + uniqueReviews.size());
    }
}
