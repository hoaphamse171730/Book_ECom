package models.custom.pagination;

public class PaginationDTO {

    private int length;
    private int firstPage;
    private int lastPage;
    private int currentPage;
    private int limit;
    private String itemLink;

    public PaginationDTO() {
    }

    public PaginationDTO(int length, int firstPage,  int currentPage, int limit, String itemLink) {
        this.length = length;
        this.firstPage = firstPage;
        this.lastPage = calculateLastPage(firstPage, length, limit);
        this.currentPage = currentPage;
        this.limit = limit;
        this.itemLink = itemLink;
    }

    private int calculateLastPage(int firstPage, int length, int limit) {
        int numOfPages;
        if (length % limit == 0)
            numOfPages = length / limit;
        else
            numOfPages = Math.floorDiv(length, limit) + 1;
        return firstPage + numOfPages - 1;
    }

    public PaginationDTO(int length, int firstPage, int lastPage, int currentPage, int limit, String itemLink) {
        this.length = length;
        this.firstPage = firstPage;
        this.lastPage = lastPage;
        this.currentPage = currentPage;
        this.limit = limit;
        this.itemLink = itemLink;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getFirstPage() {
        return firstPage;
    }

    public void setFirstPage(int firstPage) {
        this.firstPage = firstPage;
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getItemLink() {
        return itemLink;
    }

    public void setItemLink(String itemLink) {
        this.itemLink = itemLink;
    }

}
