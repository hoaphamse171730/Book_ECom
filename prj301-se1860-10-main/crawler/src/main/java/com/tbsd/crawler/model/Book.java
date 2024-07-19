package com.tbsd.crawler.model;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.List;

public class Book {
    public long id;
    public Integer dbId;
    public Long createdAt;
    public String name;
    @SerializedName("short_description") public String shortDescription;
    public String description;
    @SerializedName("original_price") public double price;
    @SerializedName("thumbnail_url") public String thumbnailURL;
    public Image[] images;
    public Author[] authors;
    public Specification[] specifications;
    @SerializedName("stock_item") public StockItem stockItem;
    @SerializedName("all_time_quantity_sold") public int quantitySold;
    public List<Review> reviews;

    public String getAttribute(String attr) {
        return Arrays.stream(specifications).flatMap(s -> Arrays.stream(s.attributes))
                .filter(a -> a.name.equals(attr))
                .findFirst()
                .map(a -> a.value).orElse("");
    }

    public void setAttribute(String attr, String string) {
        Attribute[] old = Arrays.stream(specifications[0].attributes).filter(a -> !a.name.equals(attr)).toArray(Attribute[]::new);
        Attribute[] attrs = new Attribute[old.length + 1];
        System.arraycopy(old, 0, attrs, 0, old.length);
        attrs[attrs.length - 1] = new Attribute();
        attrs[attrs.length - 1].code = AttributeType.PUBLICATION_DATE;
        attrs[attrs.length - 1].name = attr;
        attrs[attrs.length - 1].value = string;
        specifications[0].attributes = attrs;
    }

    public static class Image {
        @SerializedName("base_url") public String baseURL;
        public Integer dbId;
    }

    public static class Author {
        public int id;
        public String name;
        public Integer dbId;
    }

    public static class Specification {
        public String name;
        public Attribute[] attributes;
    }

    public static class Attribute {
        public AttributeType code;
        public String name;
        public String value;
    }

    public static class StockItem {
        public int qty;
    }
}
