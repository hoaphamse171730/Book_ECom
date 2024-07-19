package com.tbsd.crawler.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FakeOrder {
    public long createdAt;
    public List<FakeOrderDetail> details = new ArrayList<>();
    public Set<Integer> products = new HashSet<>();
    public Integer dbId;
}
