package com.jvboot.pp.web.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PagedDtoTest {
    private static final String message1 = "Help me,";
    private static final String message2 = "I'm paged!";

    private Page<String> page;

    @BeforeEach
    public void setPage() {
        //noinspection unchecked
        page = mock(Page.class);

        List<String> messages = Arrays.asList(message1, message2);
        when(page.getContent()).thenReturn(messages);
        when(page.getNumber()).thenReturn(0);
        when(page.getTotalPages()).thenReturn(0);
        when(page.getTotalElements()).thenReturn(0L);
        when(page.getSize()).thenReturn(0);
    }

    @Test
    public void constructorShouldCreatePaginatedList() {
        PagedDto<?> paged = new PagedDto<>(page);

        assertNotNull(paged.getPagination());

        assertThat(paged.getData())
            .hasSize(2)
            .isSameAs(page.getContent());
    }
}