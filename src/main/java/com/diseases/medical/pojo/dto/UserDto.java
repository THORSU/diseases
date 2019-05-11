package com.diseases.medical.pojo.dto;

import lombok.Data;

@Data
public class UserDto {

    //姓名
    private String name;
    //密码
    private String password;
    //二次密码
    private String repeatPassword;
    //用户身份
    private String userType;
    //手机号
    private String mobile;
}
