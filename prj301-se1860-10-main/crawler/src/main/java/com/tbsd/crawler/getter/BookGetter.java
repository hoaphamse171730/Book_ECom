package com.tbsd.crawler.getter;

import com.tbsd.crawler.model.Book;
import com.tbsd.crawler.util.JsonUtil;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class BookGetter {
    public static Book getBookData(long id) throws IOException {
        String url = "https://tiki.vn/api/v2/products/" + id;
        String res = IOUtils.toString(new URL(url), StandardCharsets.UTF_8);
        return JsonUtil.GSON.fromJson(res, Book.class);
    }
}
