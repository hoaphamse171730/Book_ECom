package com.tbsd.crawler.getter;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.tbsd.crawler.model.Review;
import com.tbsd.crawler.util.JsonUtil;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ReviewGetter {
    public static List<Review> fetch(long product, int limit, int max) throws IOException, InterruptedException {
        List<Review> reviews = new ArrayList<>();
        int i = 0, page = 1;
        while (i < max) {
            int delta = next(reviews, product, limit, page++);
            if (delta == 0) break;
            i += delta;
            Thread.sleep(ThreadLocalRandom.current().nextInt(500));
        }
        return reviews;
    }

    private static int next(List<Review> reviews, long product, int limit, int page) throws IOException {
        String url = "https://tiki.vn/api/v2/reviews?limit="+limit+"&include=comments,contribute_info,attribute_vote_summary&sort=score%7Cdesc,id%7Cdesc,stars%7Call&page=1&product_id="+product;
        String res = IOUtils.toString(new URL(url), StandardCharsets.UTF_8);
        JsonObject root = JsonUtil.GSON.fromJson(res, JsonObject.class);
        JsonArray data = root.getAsJsonArray("data");
        int i = 0;
        for (JsonElement it : data) {
            JsonObject review = it.getAsJsonObject();
            reviews.add(JsonUtil.GSON.fromJson(review, Review.class));
            i++;
        }
        int lastPage = root.getAsJsonObject("paging").getAsJsonPrimitive("last_page").getAsInt();
        return page >= lastPage ? 0 : i;
    }
}
