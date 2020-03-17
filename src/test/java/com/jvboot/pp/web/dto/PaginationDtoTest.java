package com.jvboot.pp.web.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

class PaginationDtoTest {
    private static final int currentPage = 3;
    private static final int pages = 7;
    private static final int size = 5;
    private static final long elements = 31;

    private Page<String> page;

    @BeforeEach
    public void setPage() {
        //noinspection unchecked
        page = mock(Page.class);

        when(page.getNumber()).thenReturn(currentPage);
        when(page.getTotalPages()).thenReturn(pages);
        when(page.getTotalElements()).thenReturn(elements);
        when(page.getSize()).thenReturn(size);
    }

    @Test
    public void constructorShouldCreatePaginatedList() {
        PaginationDto pagination = new PaginationDto(page);

        verify(page).getNumber();
        assertSame(pagination.getPage(), page.getNumber() + 1);
        verify(page).getTotalPages();
        assertSame(pagination.getTotalPages(), page.getTotalPages());
        verify(page).getTotalElements();
        assertSame(pagination.getTotalElements(), page.getTotalElements());
        verify(page).getSize();
        assertSame(pagination.getPageSize(), page.getSize());

    }
}