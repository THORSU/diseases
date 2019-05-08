package com.diseases.medical.pojo;

import lombok.Data;

import java.util.Date;

/**
 * 地域的实体类
 */
@Data
public class Area {
    private Integer areaId;
    private String areaName;
    private Integer priority;
    private Date createTime;
    private Date lastEditTime;

}
