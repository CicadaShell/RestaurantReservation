package com.cicada.library.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * @Author guocongcong
 * @Date 2018/7/16
 * @Describe
 */
public class PageBean<T> implements Serializable {

    private List<T> items;
    private int page = 0;
    private int count = 10;
    private int total = 0;

    public List<T> getItems() {
        return items;
    }

    public int getPage() {
        return page;
    }

    public int getCount() {
        return count;
    }

    public int getTotal() {
        return total;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setTotal(int total) {
        this.total = total;
    }

}
