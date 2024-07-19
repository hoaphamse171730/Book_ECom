package models.user;

import models.SortOrder;
import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class UserQuery {

    private final SearchParam searchBy;
    private final String keyword;
    private final SortParam sortBy;
    private final SortOrder sortOrder;
    private final int page;
    private final int limit;

    public UserQuery(@Nullable SearchParam searchBy, @Nullable String keyword,
            @Nullable UserQuery.SortParam sortBy, @Nullable SortOrder sortOrder,
            int page, int limit) {
        Validate.isTrue(page > 0);
        if (searchBy == null || keyword == null) {
            searchBy = null;
            keyword = null;
        }
        if (sortBy == null || sortOrder == null) {
            sortBy = SortParam.USER_ID;
            sortOrder = SortOrder.ASC;
        }
        this.searchBy = searchBy;
        this.keyword = keyword;
        this.sortBy = sortBy;
        this.sortOrder = sortOrder;
        this.page = page;
        this.limit = Math.max(0, Math.min(50, limit));
    }

    public @Nullable
    SearchParam getSearchBy() {
        return searchBy;
    }

    public @Nullable
    String getKeyword() {
        return keyword;
    }

    public @NotNull
    SortParam getSortBy() {
        return sortBy;
    }

    public @NotNull
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
        EMAIL,
        FULLNAME,
        ADDRESS,
        PHONE_NUMBER
    }

    public enum SortParam {
        USER_ID,
        FULLNAME,
        CREATED_AT
    }
}
