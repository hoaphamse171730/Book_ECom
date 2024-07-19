package com.tbsd.crawler.model;

public class FakeFeedback {

    public FakeFeedback(int product, int rating, String comment, long createdAt) {
        this.product = product;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = createdAt;
    }

    public int product;
    public int rating;
    public String comment;
    public long createdAt;
}
