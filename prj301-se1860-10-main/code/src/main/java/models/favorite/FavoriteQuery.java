package models.favorite;

import models.SortOrder;
import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.Nullable;

public class FavoriteQuery {
    private final SortParam sortBy;
    private final SortOrder sortOrder;
    private final int page;
    private final int limit;

    public FavoriteQuery(@Nullable SortParam sortBy, @Nullable SortOrder sortOrder,
                         int page, int limit) {
        Validate.isTrue(page > 0);
        if (sortBy == null || sortOrder == null) {
            sortBy = null;
            sortOrder = null;
        }
        this.sortBy = sortBy;
        this.sortOrder = sortOrder;
        this.page = page;
        this.limit = Math.max(0, Math.min(50, limit));
    }

    public @Nullable SortParam getSortBy() {
        return sortBy;
    }

    public @Nullable SortOrder getSortOrder() {
        return sortOrder;
    }

    public int getPage() {
        return page;
    }

    public int getLimit() {
        return limit;
    }

    public int getStartRow() {
        return (page - 1) * limit;
    }

    public int getEndRow() {
        return page * limit - 1;
    }

    public enum SortParam {
        CREATED_AT
    }
}
