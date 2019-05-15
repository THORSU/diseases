package com.diseases.medical.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Diseases implements Serializable {
    //主键
    private String id;
    //病名
    private String diseasesName;
    //症状
    private String diseases;
    //预防方案
    private String prevention;
}

