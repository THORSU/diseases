package com.diseases.medical.service;

import com.diseases.medical.pojo.Admin;
import com.diseases.medical.pojo.Doctor;
import com.diseases.medical.pojo.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface LoginService {

    //用户注册
    int addUser(User user);

    //查看用户有没有注册
    String getUser(String username);

    //医生注册
    int addDoctor(Doctor doctor);

    //查看医生有没有注册
    String getDoctor(String doctorname);

    //用户登录
    User userLogin(User user);

    //医生登录
    Doctor doctorLogin(Doctor doctor);

    //管理员登录
    Admin adminLogin(Admin admin);

    //根据用户的id获取发起人的姓名
    User getUserById(String userId);

    //根据医生的id获取发起人的姓名
    Doctor getDoctorById(String doctorId);

    //根据用户名获得用户详细信息
    User getUserByName(String name);

    //根据医生名获得医生详细信息
    Doctor getDoctorByName(String name);

    //更新用户信息
    int updateInfo(User user);

    //更新医生信息
    int updateDoctor(Doctor doctor);

    //获取用户列表
    List<User> getUserList();

    //获取医生列表
    List<Doctor> getDoctorList();

    //图片上传
    MultipartFile base64toMultipart(String data, String fileName);
}
