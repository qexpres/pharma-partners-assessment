package com.jvboot.pp.web.dto;

import org.springframework.data.domain.Page;

import java.util.List;

public class PagedDto<T> extends DataDto<List<T>> {
    private final PaginationDto pagination;

    public PagedDto(Page<T> page) {
        super(page.getContent());
        pagination = new PaginationDto(page);
    }

    public PaginationDto getPagination() {
        return pagination;
    }
}
