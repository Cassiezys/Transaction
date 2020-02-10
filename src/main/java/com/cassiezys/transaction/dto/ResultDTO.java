package com.cassiezys.transaction.dto;

import com.cassiezys.transaction.exception.ErrorCodeEnumImp;
import lombok.Data;

import java.util.List;

/**
 * Copyright，2020
 * Author:曾念念
 * Description:返回操作的结果码：ajax返回到页面 的 success:function(ret) 中的ret
 *
 */
@Data
public class ResultDTO<T> {
    private Integer code;
    private String message;
    //泛型：传结果：任何需要作为传到前端页面的data数据
    private T data;

    private static ResultDTO errorOf(Integer code, String message) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(code);
        resultDTO.setMessage(message);
        return resultDTO;
    }

    public static ResultDTO errorOf(ErrorCodeEnumImp errorCodeEnumImp) {
        return errorOf(errorCodeEnumImp.getCode(),errorCodeEnumImp.getMessage());
    }

    public static ResultDTO successOf() {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(2120);
        resultDTO.setMessage("成功~");
        return resultDTO;
    }

    public static <T> ResultDTO successOf(T t) {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setCode(2120);
        resultDTO.setMessage("成功~");
        resultDTO.setData(t);
        return resultDTO;

    }
}
