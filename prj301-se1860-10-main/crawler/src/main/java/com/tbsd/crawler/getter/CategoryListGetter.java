package com.tbsd.crawler.getter;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.tbsd.crawler.model.Category;
import com.tbsd.crawler.util.JsonUtil;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

public class CategoryListGetter {
    public static Map<Integer, Category> fetch(int parent) throws IOException {
        String url = "https://tiki.vn/api/personalish/v1/blocks/listings?limit=3&aggregations=2&page=1&category="+parent;
        String res = IOUtils.toString(new URL(url), StandardCharsets.UTF_8);
        JsonObject root = JsonUtil.GSON.fromJson(res, JsonObject.class);
        JsonArray filters = root.getAsJsonArray("filters");

        Map<Integer, Category> map = new LinkedHashMap<>();

        for (JsonElement it : filters) {
            JsonObject filter = it.getAsJsonObject();
            if (filter.getAsJsonPrimitive("query_name").getAsString().equals("category")) {
                JsonArray values = filter.getAsJsonArray("values");
                for (JsonElement value : values) {
                    JsonObject val = value.getAsJsonObject();
                    int categoryId = val.getAsJsonPrimitive("query_value").getAsInt();
                    String categoryName = val.getAsJsonPrimitive("display_value").getAsString();
                    Category category = map.get(categoryId);
                    if (category == null)
                        map.put(categoryId, new Category(categoryId, categoryName, null));
                }
                break;
            }
        }

        return map;
    }
}
