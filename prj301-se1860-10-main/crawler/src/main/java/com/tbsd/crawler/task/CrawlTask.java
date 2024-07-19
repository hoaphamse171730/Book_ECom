package com.tbsd.crawler.task;

import com.tbsd.crawler.data.BookIndexManager;
import com.tbsd.crawler.data.BookManager;
import com.tbsd.crawler.data.CategoryManager;
import com.tbsd.crawler.model.Category;

import java.util.Map;
import java.util.Set;

public class CrawlTask {
    public static void start() {
        int countCategory = CategoryManager.getCategory().size();
        System.out.println("Crawling " + countCategory + " categories");
        for (Map.Entry<Integer, Category> e : CategoryManager.getCategory().entrySet()) {
            System.out.println("Crawling category " + e.getValue());
            int count = BookIndexManager.getBooks(e.getKey()).size();
            System.out.println(" > " + e.getValue() + " has " + count + " books");
        }
        System.out.println();
        for (Map.Entry<Integer, Category> e : CategoryManager.getCategory().entrySet()) {
            System.out.println("Crawling books for " + e.getValue());
            for (long book : BookIndexManager.getBooks(e.getKey())) {
                BookManager.getBook(book);
            }
        }
        System.out.println();
        int totalReviews = 0;
        int totalBooks = 0;
        for (Map.Entry<Integer, Category> e : CategoryManager.getCategory().entrySet()) {
            System.out.println("Crawling reviews for " + e.getValue());
            Set<Long> books = BookIndexManager.getBooks(e.getKey());
            totalBooks += books.size();
            for (long book : books) {
                BookManager.fetchReview(book);
                totalReviews += BookManager.getBook(book).reviews.size();
            }
        }
        System.out.println("Crawled " + totalBooks + " books and " + totalReviews + " reviews");
    }
}
