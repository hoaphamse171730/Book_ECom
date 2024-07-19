package com.tbsd.crawler.data;

import com.google.gson.reflect.TypeToken;
import com.tbsd.crawler.Main;
import com.tbsd.crawler.getter.CategoryListGetter;
import com.tbsd.crawler.model.Category;
import com.tbsd.crawler.util.JsonUtil;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class CategoryManager {
    private static Map<Integer, Category> categoryMap;

    private static File getDataFile() {
        return new File(Main.DATA_DIR, "category.json");
    }

    public static void saveData(){
        System.out.println("Saving category data to " + getDataFile().getAbsolutePath());
        try {
            String str = JsonUtil.GSON.toJson(categoryMap);
            FileUtils.writeStringToFile(getDataFile(), str, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<Integer, Category> getCategory() {
        if (categoryMap != null)
            return categoryMap;

        File caregoryFile = getDataFile();

        if (!caregoryFile.exists())
            generateData();
        else {
            try {
                System.out.println("Loading category data from " + getDataFile().getAbsolutePath());
                String str = FileUtils.readFileToString(caregoryFile, "UTF-8");
                categoryMap = JsonUtil.GSON.fromJson(str, new TypeToken<Map<Integer, Category>>(){}.getType());
            } catch (IOException e) {
                e.printStackTrace();
                generateData();
            }
        }

        return categoryMap;
    }

    private static void generateData() {
        try {
            System.out.println("Fetching category data");
            categoryMap = CategoryListGetter.fetch(839);
            saveData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
