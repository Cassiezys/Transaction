package com.cassiezys.transaction.cache;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Copyright，2020
 * Author:曾念念
 * Description:可以选择的城市
 */
public class CityCache {

    public static List<String> get(){
        List<String> cityList = new ArrayList<>();
        cityList = Arrays.asList("全球","湖南","广州","上海");
        return cityList;
    }
}
