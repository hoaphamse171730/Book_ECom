package com.tbsd.crawler.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Review {
    @SerializedName("id")
    @Expose
    public Long id;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("content")
    @Expose
    public String content;
    @SerializedName("customer_id")
    @Expose
    public Long customerId;
    @SerializedName("rating")
    @Expose
    public Long rating;
    @SerializedName("images")
    @Expose
    public List<Image> images;
    @SerializedName("created_at")
    @Expose
    public Long createdAt;
    @SerializedName("created_by")
    @Expose
    public CreatedBy createdBy;
    @SerializedName("product_id")
    @Expose
    public Long productId;
    @SerializedName("timeline")
    @Expose
    public Timeline timeline;

    public static class Image {

        @SerializedName("id")
        @Expose
        public Long id;
        @SerializedName("full_path")
        @Expose
        public String fullPath;

    }

    public static class Timeline {

        @SerializedName("review_created_date")
        @Expose
        public String reviewCreatedDate;
        @SerializedName("delivery_date")
        @Expose
        public String deliveryDate;

    }
    public static class CreatedBy {

        @SerializedName("id")
        @Expose
        public Long id;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("full_name")
        @Expose
        public String fullName;
        @SerializedName("created_time")
        @Expose
        public String createdTime;
        @SerializedName("purchased")
        @Expose
        public Boolean purchased;
        @SerializedName("purchased_at")
        @Expose
        public Long purchasedAt;

    }
}