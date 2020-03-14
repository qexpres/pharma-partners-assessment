package com.jvboot.pp.web.dto;

import org.springframework.data.domain.Page;

import java.util.Collection;

public class PagedDto<T> {
    private final Collection<T> data;
    private final PaginationDto pagination;

    public PagedDto(Page<T> page) {
        data = page.getContent();
        pagination = new PaginationDto(page);
    }

    public Collection<T> getData() {
        return data;
    }

    public PaginationDto getPagination() {
        return pagination;
    }
}
