package engine.model;

import org.springframework.data.domain.Sort;

public class QuestionsPage {
    private int page = 0;
    private int size = 10;
    private Sort.Direction sortDirection = Sort.Direction.ASC;
    private String sortBy = "title";

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page= page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Sort.Direction getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(Sort.Direction sortDirection) {
        this.sortDirection = sortDirection;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }
}
