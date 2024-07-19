package com.tbsd.crawler.getter;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.tbsd.crawler.util.JsonUtil;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class BookListGetter {
    public static Set<Long> fetch(int parent, int limit, int max) throws IOException, InterruptedException {
        Set<Long> books = new LinkedHashSet<>();
        int i = 0, page = 1;
        while (i < max) {
            int delta = next(books, parent, limit, page++);
            if (delta == 0) break;
            i += delta;
            Thread.sleep(ThreadLocalRandom.current().nextInt(500));
        }
        return books;
    }

    private static int next(Set<Long> books, int parent, int limit, int page) throws IOException {
        String url = "https://tiki.vn/api/personalish/v1/blocks/listings?limit="+limit+"&aggregations=2&page="+page+"&category="+parent;
        String res = IOUtils.toString(new URL(url), StandardCharsets.UTF_8);
        JsonObject root = JsonUtil.GSON.fromJson(res, JsonObject.class);
        JsonArray data = root.getAsJsonArray("data");
        int i = 0;
        for (JsonElement it : data) {
            JsonObject book = it.getAsJsonObject();
            books.add(book.get("id").getAsLong());
            i++;
        }
        int lastPage = root.getAsJsonObject("paging").getAsJsonPrimitive("last_page").getAsInt();
        return page >= lastPage ? 0 : i;
    }
}
