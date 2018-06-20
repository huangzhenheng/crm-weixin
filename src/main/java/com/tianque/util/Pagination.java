package com.tianque.util;

import java.util.List;

public class Pagination<T> {

// 数据总条数
    private Integer totalSize;
// 分的总页数
    private Integer totalPages;
// 当前页
    private Integer pageNum;
// 一页多少数据
    private Integer pageSize;
// 从第几个数据开始查询
    private Integer start;

    private List<T> items;

    public Pagination(Integer pageNum, Integer pageSize, Integer totalSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.totalSize = totalSize;

        //计算总页数
        totalPages = totalSize / pageSize;
        if(totalSize % pageSize != 0) {
            totalPages++;
        }

        if(pageNum > totalPages) {
            this.pageNum = totalPages;
        }
        if(this.pageNum <= 0) {
            this.pageNum = 1;
        }

        start = (this.pageNum - 1) * pageSize;
    }

    //get与set
    public Integer getTotalSize() {
        return totalSize;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public Integer getStart() {
        return start;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}
