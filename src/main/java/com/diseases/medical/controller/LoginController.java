package com.diseases.medical.controller;

import com.alibaba.fastjson.JSON;
import com.diseases.medical.pojo.Admin;
import com.diseases.medical.pojo.Doctor;
import com.diseases.medical.pojo.User;
import com.diseases.medical.pojo.dto.UserDto;
import com.diseases.medical.service.LoginService;
import com.diseases.medical.utils.GenerateSequenceUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {

    private static Logger logger = Logger.getLogger(LoginController.class);
    @Autowired
    private LoginService loginService;

    private User user = new User();
    private Doctor doctor = new Doctor();
    private Admin admin = new Admin();

    /**
     * 注册接口
     *
     * @param userDto
     * @return
     */
    @PostMapping("/register")
    public Object register(@RequestParam UserDto userDto) {
        String username = userDto.getName();
        String password = userDto.getPassword();
        String repeatPassword = userDto.getRepeatPassword();
        String userType = userDto.getUserType();
        String mobile = userDto.getMobile();
        //重复密码验证
        if (!repeatPassword.equals(password)) {
            return "password error";
        }
        //用户注册
        if (("user").equals(userType)) {
            if (!StringUtils.isEmpty(loginService.getUser(username))) {
                return "have SignUp";
            }
            user.setId("user" + GenerateSequenceUtil.generateSequenceNo());
            user.setName(username);
            user.setPassword(password);
            user.setStatus("1");
            user.setMobile(mobile);
            int res = loginService.addUser(user);
            if (res == 1) {
                logger.info(res);
                return "SignUp success";
            } else {
                logger.error(res);
                return "SignUp fail";
            }
        } else if (("doctor").equals(userType)) {
            if (!StringUtils.isEmpty(loginService.getDoctor(username))) {
                return JSON.toJSONString("have SignUp");
            }
            doctor.setId("doctor" + GenerateSequenceUtil.generateSequenceNo());
            doctor.setName(username);
            doctor.setPassword(password);
            doctor.setStatus("1");
            doctor.setMobile(mobile);
            int res = loginService.addDoctor(doctor);
            if (res == 1) {
                logger.info(res);
                return "SignUp success";
            } else {
                logger.error(res);
                return "SignUp fail";
            }
        } else {
            return "userType error";
        }
    }

    /**
     * 统一的登录接口
     *
     * @param userDto
     * @return
     */
    @PostMapping("login")
    public Object login(@RequestBody UserDto userDto) {
        String userType = userDto.getUserType();
        String mobile = userDto.getMobile();
        String username = userDto.getName();
        String password = userDto.getPassword();
        if (("user").equals(userType)) {
            user.setName(username);
            user.setPassword(password);
            User res = loginService.userLogin(user);
            if (res != null) {
                if (res.getPassword().equals(password) && ("1").equals(res.getStatus())) {
                    return res;
                } else if (("0").equals(res.getStatus())) {
                    return "have deleted";
                } else {
                    return "login failure";
                }
            } else {
                return "not register";
            }
        } else if (("doctor").equals(userType)) {
            doctor.setName(username);
            doctor.setPassword(password);
            Doctor res = loginService.doctorLogin(doctor);
            if (res != null) {
                if (res.getPassword().equals(password) && ("1").equals(res.getStatus())) {
                    return res;
                } else if (("0").equals(res.getStatus())) {
                    return "have deleted";
                } else {
                    return "login failure";
                }
            } else {
                return "not register";
            }
        } else if (("admin").equals(userType)) {
            admin.setName(username);
            admin.setPassword(password);
            Admin res = loginService.adminLogin(admin);
            if (res != null) {
                if (res.getPassword().equals(password)) {
                    return res;
                } else {
                    return "login failure";
                }
            } else {
                return "not register";
            }
        } else {
            return "userType error";
        }
    }

    /**
     * 忘记密码
     *
     * @param userDto
     * @return
     */
    @PostMapping("forgetpwd")
    public Object forgetpwd(@RequestBody UserDto userDto) {
        String username = userDto.getName();
        String password = userDto.getPassword();
        String repeatPassword = userDto.getRepeatPassword();
        String userType = userDto.getUserType();
        String mobile = userDto.getMobile();
        //确认两次输入密码一致
        if (!password.equals(repeatPassword)) {
            return "password error";
        }
        if (("user").equals(userType)) {
            user.setName(username);
            User res = loginService.userLogin(user);//根据用户名查找用户
            if (res == null) {
                return "mobile or name error";
            }
            if (!res.getMobile().equals(mobile)) {
                return "mobile or name error";
            }
            res.setPassword(password);
            loginService.updateInfo(res);//替换数据库密码
            return "change success";
        } else if (("doctor").equals(userType)) {
            doctor.setName(username);
            Doctor res = loginService.doctorLogin(doctor);
            if (res == null) {
                return "mobile or name error";
            }
            if (!res.getMobile().equals(mobile)) {
                return "mobile or name error";
            }
            res.setPassword(password);
            loginService.updateDoctor(res);
            return "change success";
        } else {
            return "identity error";
        }
    }
}
