package com.diseases.medical.controller;

import com.diseases.medical.pojo.Doctor;
import com.diseases.medical.pojo.User;
import com.diseases.medical.pojo.dto.UserDto;
import com.diseases.medical.service.LoginService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class InfoController {
    private static Logger logger = Logger.getLogger(InfoController.class);

    @Autowired
    private LoginService loginService;

    private User user = new User();
    private Doctor doctor = new Doctor();

    /**
     * 更新用户信息
     *
     * @return
     */
    @PostMapping("/updateInfo")
    public Object updateInfo(@RequestBody UserDto userDto) {
        String mobile = userDto.getMobile();
        String userType = userDto.getUserType();
        String nikeName = userDto.getNickname();
        String name = userDto.getName();
        if (("user").equals(userType)) {
            user = loginService.getUserByName(name);
            user.setNickname(nikeName);
            user.setMobile(mobile);
            int res = loginService.updateInfo(user);
            if (res == 1) {
                logger.info("用户更新成功！");
                return "update success";//更新成功
            } else {
                return "update fail";
            }
        } else if (("doctor").equals(userType)) {
            doctor = loginService.getDoctorByName(name);
            doctor.setNickname(nikeName);
            doctor.setMobile(mobile);
            int res = loginService.updateDoctor(doctor);
            if (res == 1) {
                logger.info("医生更新成功！");
                return "update success";//TODO 更新成功
            } else {
                return "update fail";//TODO 网络异常
            }
        } else {
            return "type error";
        }
    }

    /**
     * 更改密码
     *
     * @param userDto
     * @return
     */
    @PostMapping("/changePwd")
    public Object changePwd(@RequestBody UserDto userDto) {
        String oldPassword = userDto.getOldPassword();
        String repeatPassword = userDto.getRepeatPassword();
        String password = userDto.getPassword();
        String mobile = userDto.getMobile();
        String userType = userDto.getUserType();
        String name = userDto.getName();
        //重复密码验证
        if (!repeatPassword.equals(password)) {
            return "password error";
        }

        if (("user").equals(userType)) {
            User st1 = loginService.getUserByName(name);
            if (st1 != null) {
                if (st1.getPassword().equals(oldPassword.trim())) {
                    st1.setPassword(password.trim());
                    int res = loginService.updateInfo(st1);
                    if (res == 1) {
                        return "change success";
                    } else {
                        return "change fail";//TODO 网络异常
                    }
                } else {
                    return "old password error";//TODO 旧密码输入错误！
                }
            } else {
                return "未知异常";
            }
        } else if (("doctor").equals(userType)) {
            Doctor doctor = loginService.getDoctorByName(name);
            if (doctor != null) {
                if (doctor.getPassword().equals(oldPassword.trim())) {
                    doctor.setPassword(password.trim());
                    int res = loginService.updateDoctor(doctor);
                    if (res == 1) {
                        return "change success";
                    } else {
                        return "change fail";//TODO 网络异常
                    }
                } else {
                    return "old password error";//TODO 旧密码输入错误！
                }
            } else {
                return "未知异常";
            }
        } else {
            return "type error";
        }
    }

    /**
     * 获取用户详细信息
     *
     * @param userDto
     * @return
     */
    @PostMapping("/getUserInfo")
    public Object getUserInfo(@RequestBody UserDto userDto) {
        String name = userDto.getName();
        String type = userDto.getUserType();
        if (("user").equals(type)) {
            return loginService.getUserByName(name);
        } else if (("doctor").equals(type)) {
            return loginService.getDoctorByName(name);
        } else {
            return "type error";
        }
    }
}
