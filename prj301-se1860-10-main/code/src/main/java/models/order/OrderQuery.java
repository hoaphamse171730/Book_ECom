package models.order;

import models.SortOrder;
import org.jetbrains.annotations.Nullable;

public class OrderQuery {

    private final SearchParam search;
    private final String keyword;
    private final SortParam sortBy;
    private final SortOrder sortOrder;
    private final int page;
    private final int limit;

    public OrderQuery(
            @Nullable SearchParam search,
            @Nullable String keyword,
            @Nullable SortParam sortBy,
            @Nullable SortOrder sortOrder,
            int page,
            int limit
    ) {
        if (search == null || keyword == null) {
            search = null;
            keyword = null;
        }
        if (sortBy == null || sortOrder == null) {
            sortBy = null;
            sortOrder = null;
        }
        this.search = search;
        this.keyword = keyword;
        this.sortBy = sortBy;
        this.sortOrder = sortOrder;
        this.page = Math.max(0, page);
        this.limit = Math.max(0, Math.min(50, limit));
    }

    public @Nullable
    SearchParam getSearchBy() {
        return search;
    }

    public @Nullable
    String getKeyword() {
        return keyword;
    }

    public @Nullable
    SortParam getSortBy() {
        return sortBy;
    }

    public @Nullable
    SortOrder getSortOrder() {
        return sortOrder;
    }

    public int getPage() {
        return page;
    }

    public int getLimit() {
        return limit;
    }

    public int getStartRow() {
        return (page - 1) * limit + 1;
    }

    public int getEndRow() {
        return page * limit;
    }

    public enum SearchParam {
        STATUS,
        CREATE_AT,
    }

    public enum SortParam {
        ORDER_ID,
        USER_ID,
        TOTAL_PRICE,
        STATUS,
        CREATED_AT,
    }
}
