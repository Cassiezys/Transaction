package com.cassiezys.transaction.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright，2020
 * Author:曾念念
 * Description:页面上所有的信息：包含商品，页面等信息
 */
public class PaginationDTO {
    private List<ProductionDTO> productionDTOS;
    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showEndPage;
    private Integer totalPage;
    private Integer page;
    private List<Integer> pages = new ArrayList<>();

}
