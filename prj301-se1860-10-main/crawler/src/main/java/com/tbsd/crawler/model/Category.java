package com.tbsd.crawler.model;

import java.util.Objects;

public class Category {
    private final int id;
    private final String name;

    private Integer dbId;

    public Category(int id, String name, Integer dbId) {
        this.id = id;
        this.name = name;
        this.dbId = dbId;
    }

    @Override
    public String toString() {
        return String.format("%s(#%d)", name, id);
    }

    public int id() {
        return id;
    }

    public String name() {
        return name;
    }

    public Integer dbId() {
        return dbId;
    }

    public void setDbId(Integer dbId) {
        this.dbId = dbId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        Category that = (Category) obj;
        return this.id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
