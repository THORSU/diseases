package com.diseases.medical.pojo;

import lombok.Data;

import java.io.Serializable;

//医生
@Data
public class Doctor implements Serializable {
    //id
    private String id;
    //姓名
    private String name;
    //昵称
    private String nickname;
    //密码
    private String password;
    //状态（0已删除，1正常）
    private String status;
    //手机号
    private String mobile;
    //执业医师证照片
    private String photo;
}
