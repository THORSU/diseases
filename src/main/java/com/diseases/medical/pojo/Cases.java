package com.diseases.medical.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Cases implements Serializable {
    //主键
    private String id;
    //病例名
    private String symptomsName;
    //病例症状
    private String symptoms;
    //预防方案
    private String prevention;
}
