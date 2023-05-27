package com.example.server.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
@AllArgsConstructor
public class PageInfo {

    private int totalPage;
    private int currentPage;
    private int pageSize;
    private int numberOfElements;

    public PageInfo(Page<?> page) {
        this.totalPage = page.getTotalPages();
        this.currentPage = page.getNumber() + 1;
        this.pageSize = page.getSize();
        this.numberOfElements = page.getNumberOfElements();
    }
}
