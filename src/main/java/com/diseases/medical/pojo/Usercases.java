package com.diseases.medical.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * 病例主表
 */
@Data
public class Usercases implements Serializable {
    //主键
    private String id;
    //病例id
    private String casesid;
    //标题
    private String title;
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
    //状态
    private String status;
    //创建时间
    private String time;
}
