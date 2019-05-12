package com.diseases.medical.controller;

import com.diseases.medical.pojo.Doctor;
import com.diseases.medical.pojo.User;
import com.diseases.medical.service.LoginService;
import com.diseases.medical.utils.Result;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/info")
public class InfoController {
    private static Logger logger = Logger.getLogger(InfoController.class);

    @Autowired
    private LoginService loginService;

    private Result result = new Result();
    private User user = new User();
    private Doctor doctor = new Doctor();

    /**
     * 更新用户信息
     *
     * @return
     */
    @PostMapping("/updateInfo")
    public Object updateInfo(HttpServletRequest request) {
        String name = request.getParameter("name");
        String nikeName = request.getParameter("nickname");
        String userType = request.getParameter("userType");
        String mobile = request.getParameter("mobile");
        if (("user").equals(userType)) {
            user = loginService.getUserByName(name);
            user.setNickname(nikeName);
            user.setMobile(mobile);
            int res = loginService.updateInfo(user);
            if (res == 1) {
                logger.info("用户更新成功！");
                result.setCode("0");
                result.setMsg("用户更新成功");
                return result;
            } else {
                result.setCode("1");
                result.setMsg("用户更新失败");
                return result;
            }
        } else if (("doctor").equals(userType)) {
            doctor = loginService.getDoctorByName(name);
            doctor.setNickname(nikeName);
            doctor.setMobile(mobile);
            int res = loginService.updateDoctor(doctor);
            if (res == 1) {
                logger.info("医生更新成功！");
                result.setCode("0");
                result.setMsg("医生更新成功");
                return result;
            } else {
                result.setCode("1");
                result.setMsg("医生更新失败");
                return result;
            }
        } else {
            result.setMsg("用户类型错误");
            result.setCode("1");
            return result;
        }
    }

    /**
     * 更改密码
     *
     * @return
     */
    @PostMapping("/changePwd")
    public Object changePwd(HttpServletRequest request) {
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String repeatPassword = request.getParameter("repeatPassword");
        String userType = request.getParameter("userType");
        String oldPassword = request.getParameter("oldPassword");
        //重复密码验证
        if (!repeatPassword.equals(password)) {
            result.setCode("1");
            result.setMsg("两次密码不一致");
            return result;
        }

        if (("user").equals(userType)) {
            User st1 = loginService.getUserByName(name);
            if (st1 != null) {
                if (st1.getPassword().equals(oldPassword.trim())) {
                    st1.setPassword(password.trim());
                    int res = loginService.updateInfo(st1);
                    if (res == 1) {
                        result.setCode("0");
                        result.setMsg("修改成功");
                        return result;
                    } else {
                        result.setCode("1");
                        result.setMsg("修改失败");
                        return result;
                    }
                } else {
                    result.setCode("1");
                    result.setMsg("旧密码输入错误");
                    return result;
                }
            } else {
                result.setCode("1");
                result.setMsg("未知异常");
                return result;
            }
        } else if (("doctor").equals(userType)) {
            Doctor doctor = loginService.getDoctorByName(name);
            if (doctor != null) {
                if (doctor.getPassword().equals(oldPassword.trim())) {
                    doctor.setPassword(password.trim());
                    int res = loginService.updateDoctor(doctor);
                    if (res == 1) {
                        result.setCode("0");
                        result.setMsg("修改成功");
                        return result;
                    } else {
                        result.setCode("1");
                        result.setMsg("修改失败");
                        return result;
                    }
                } else {
                    result.setCode("1");
                    result.setMsg("旧密码输入错误");
                    return result;
                }
            } else {
                result.setCode("1");
                result.setMsg("未知异常");
                return result;
            }
        } else {
            result.setMsg("用户类型错误");
            result.setCode("1");
            return result;
        }
    }

    /**
     * 获取用户详细信息
     *
     * @return
     */
    @PostMapping("/getUserInfo")
    public Object getUserInfo(HttpServletRequest request) {
        String name = request.getParameter("name");
        String type = request.getParameter("userType");
        if (("user").equals(type)) {
            result.setMsg("用户信息");
            result.setCode("0");
            result.setData(loginService.getUserByName(name));
            return result;
        } else if (("doctor").equals(type)) {
            result.setMsg("医生信息");
            result.setCode("0");
            result.setData(loginService.getDoctorByName(name));
            return result;
        } else {
            result.setMsg("用户类型错误");
            result.setCode("1");
            return result;
        }
    }
}
