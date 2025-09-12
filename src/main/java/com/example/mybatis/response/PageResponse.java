package com.example.mybatis.response;

import java.util.List;

public class PageResponse<T> {
    private List<T> data;
    private int page;
    private int size;
    private long total;
    private int totalPages;

    public PageResponse(List<T> data, int page, int size, long total) {
        this.data = data;
        this.page = page;
        this.size = size;
        this.total = total;
        this.totalPages = (int) Math.ceil((double) total / size);
    }
}
