package com.tbsd.crawler.data;

import com.google.gson.reflect.TypeToken;
import com.tbsd.crawler.Main;
import com.tbsd.crawler.getter.BookListGetter;
import com.tbsd.crawler.util.JsonUtil;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class BookIndexManager {
    private static final int MAX = 100;
    private static final int LIM = 20;
    private static Map<Integer, Set<Long>> map;

    private static File getDataFile() {
        return new File(Main.DATA_DIR, "book_index.json");
    }

    private static void loadData() throws IOException {
        if (!getDataFile().exists()) {
            map = new LinkedHashMap<>();
            return;
        }
        System.out.println("Loading book index from " + getDataFile().getAbsolutePath());
        String str = FileUtils.readFileToString(getDataFile(), "UTF-8");
        map = JsonUtil.GSON.fromJson(str, new TypeToken<Map<Integer, Set<Long>>>() {}.getType());
        map.entrySet().removeIf(it -> it.getValue() == null || it.getValue().isEmpty());
    }

    private static void saveData(){
        System.out.println("Saving book index to " + getDataFile().getAbsolutePath());
        try {
            String str = JsonUtil.GSON.toJson(map);
            FileUtils.writeStringToFile(getDataFile(), str, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Set<Long> getBooks(int category) {
        try {
            if (map == null)
                loadData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (map.containsKey(category))
            return map.get(category);
        generateData(category);
        return map.get(category);
    }

    private static void generateData(int category) {
        try {
            System.out.println("Fetching book index");
            map.put(category, BookListGetter.fetch(category, LIM, MAX));
            saveData();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
