package com.tbsd.crawler.model;

public class FakeOrderDetail {
    public FakeOrderDetail(int product, int quantity, int price, long productCreatedAt) {
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        this.productCreatedAt = productCreatedAt;
    }

    public int product;
    public int quantity;
    public int price;
    public long productCreatedAt;
}
