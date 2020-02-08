package com.cassiezys.transaction.cache;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Copyright","2020
 * Author:曾念念
 * Description:类别
 */
public class CategoryCache {
    public static List<String> get(){
        List<String> catogories = new ArrayList<>();
        catogories = Arrays.asList("手机","电脑","配件","电器","书籍","娱乐","运动","活动");
        return catogories;
    }
}
