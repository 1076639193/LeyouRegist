package com.leyou.common.po;

import lombok.Data;

import javax.swing.*;
import java.util.List;

@Data
public class PageResult<T> {

    private Long total;//一共多少条数据

    //private Long tatalPage;//一共多少页
    private Long totalPage;//一共多少页
    private List<T> items;//每页显示的数据

    public PageResult() {
    }

//    public PageResult(Long total, Long tatalPage, List<T> items) {
//        this.total = total;
//        this.tatalPage = tatalPage;
//        this.items = items;
//    }


    public PageResult(Long total, Long totalPage, List<T> items) {
        this.total = total;
        this.totalPage = totalPage;
        this.items = items;
    }
}
