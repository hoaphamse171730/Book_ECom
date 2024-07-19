package com.tbsd.crawler.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FakeUser {
    public FakeProfile profile;
    public Set<Integer> favourites = new HashSet<>();
    public List<FakeFeedback> feedbacks = new ArrayList<>();
    public long createdAt;
    public Integer dbId;
}
