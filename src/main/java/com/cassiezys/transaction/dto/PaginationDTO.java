package com.cassiezys.transaction.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright，2020
 * Author:曾念念
 * Description:页面上所有的信息：包含商品，页面等信息
 */
@Data
public class PaginationDTO<T> {
    private List<T> dataDtos;
    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showEndPage;
    private Integer totalPage;
    private Integer page;
    private List<Integer> pages = new ArrayList<>();


    /**
     * 分页标签的显示
     * @param totalCount  总数量
     * @param page 当前页面
     * @param size 页内显示的商品数量
     */
    public void setPagination(int totalCount, Integer page, Integer size) {
        /*先添加当前的这一页*/
        pages.add(page);
        for(int i=1;i<=3; ++i){
            if(page-i>0){
                //向前添加2个数字
                pages.add(0,page-i);
            }
            if (page+i<= this.totalPage){
                //向后添加数字
                pages.add(page+i);
            }
        }

        if (page == 1){
            //是否展示上一页
            showPrevious = false;
        }else {
            showPrevious = true;
        }
        if (page == this.totalPage) {
            showNext = false;
        } else {
            showNext = true;
        }

        //是否展示第一页
        if (pages.contains(1)) {
            showFirstPage = false;
        } else {
            showFirstPage = true;
        }

        //last page
        if (pages.contains(this.totalPage)) {
            showEndPage = false;
        } else {
            showEndPage = true;
        }
    }
}
