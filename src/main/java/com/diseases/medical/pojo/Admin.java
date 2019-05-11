package com.diseases.medical.pojo;

import lombok.Data;

import java.io.Serializable;

//管理员
@Data
public class Admin implements Serializable {
    //id
    private String id;
    //姓名
    private String name;
    //密码
    private String password;
    //状态（0已删除，1正常）
    private String status;
    //手机号
    private String mobile;

}
