package com.tbsd.crawler.data;

import com.tbsd.crawler.Main;
import com.tbsd.crawler.getter.BookGetter;
import com.tbsd.crawler.getter.ReviewGetter;
import com.tbsd.crawler.model.Book;
import com.tbsd.crawler.util.JsonUtil;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class BookManager {
    private static final LinkedHashMap<Long, Book> books = new LinkedHashMap<Long, Book>(){
        @Override
        protected boolean removeEldestEntry(Map.Entry<Long, Book> eldest) {
            return size() > 500;
        }
    };

    public static Book getBook(long id){
        if(!books.containsKey(id)) {
            try {
                books.put(id, getOrCrawl(id));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return books.get(id);
    }

    public static void fetchReview(long id) {
        Book b = getBook(id);
        if (b.reviews != null)
            return;
        try {
            System.out.println("Fetching reviews of book " + id);
            b.reviews = ReviewGetter.fetch(id, 5, 20);
            saveData(b);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static Book getOrCrawl(long id) throws IOException {
        File dir = new File(Main.DATA_DIR, "book");
        dir.mkdir();
        File file = new File(dir, id + ".json");
        if (file.exists()) {
            return JsonUtil.GSON.fromJson(FileUtils.readFileToString(file, "UTF-8"), Book.class);
        }
        System.out.println("Fetching book " + id);
        Book book = BookGetter.getBookData(id);
        FileUtils.writeStringToFile(file, JsonUtil.GSON.toJson(book), "UTF-8");
        return book;
    }

    public static void saveData(Book b) throws IOException {
        File dir = new File(Main.DATA_DIR, "book");
        File file = new File(dir, b.id + ".json");
        FileUtils.writeStringToFile(file, JsonUtil.GSON.toJson(b), "UTF-8");
    }
}
