package com.jvboot.pp.web.dto;

import org.springframework.data.domain.Page;

public class PaginationDto {
    private final int page;
    private final int pageSize;
    private final int totalPages;
    private final int totalElements;

    PaginationDto(Page<?> page) {
        this.page = page.getNumber() + 1;
        this.pageSize = page.getSize();
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getNumberOfElements();
    }

    public int getPage() {
        return page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getTotalElements() {
        return totalElements;
    }
}
