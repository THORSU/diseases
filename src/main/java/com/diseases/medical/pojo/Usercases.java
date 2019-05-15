package com.diseases.medical.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Usercases implements Serializable {
    //主键
    private String id;
    //病例id
    private String casesid;
    //患者名
    private String patients;
    //患者症状
    private String symptoms;
    //化验单
    private String among;
    //病情诊断
    private String diagnosis;
    //医嘱
    private String advice;
}
