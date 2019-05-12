package com.diseases.medical.utils;

import lombok.Data;

@Data
public class Result<T> {
    //错误码（String）
    private String code;
    //返回信息
    private String msg;
    //返回数据
    private T data;
}
