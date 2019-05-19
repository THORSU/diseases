package com.diseases.medical.controller;

import com.diseases.medical.pojo.Admin;
import com.diseases.medical.pojo.Doctor;
import com.diseases.medical.pojo.User;
import com.diseases.medical.service.LoginService;
import com.diseases.medical.utils.GenerateSequenceUtil;
import com.diseases.medical.utils.Result;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

@RestController
@RequestMapping("/login")
public class LoginController {

    private static Logger logger = Logger.getLogger(LoginController.class);
    @Autowired
    private LoginService loginService;

    private Result result;

    @Value("${save_path}")
    private String savePath;

    @Value("${sql_path}")
    private String sqlPath;

    private User user = new User();
    private Doctor doctor = new Doctor();
    private Admin admin = new Admin();

    /**
     * 注册接口
     *
     * @return
     */
    @PostMapping("/register")
    public Object register(HttpServletRequest request) {
        result = new Result();
        String username = request.getParameter("name");
        String password = request.getParameter("password");
        String userType = request.getParameter("userType");
        String mobile = request.getParameter("mobile");
        //img
        String imgStr = request.getParameter("file");
        StringBuffer fileName = new StringBuffer();
        fileName.append(UUID.randomUUID().toString().replaceAll("-", ""));
        //用户注册
        if (("user").equals(userType)) {
            if (!StringUtils.isEmpty(loginService.getUser(username))) {
                result.setCode("1");
                result.setMsg("已注册");
                return result;
            }
            user.setId("user" + GenerateSequenceUtil.generateSequenceNo());
            user.setName(username);
            user.setPassword(password);
            user.setStatus("1");
            user.setMobile(mobile);
            int res = loginService.addUser(user);
            User res1 = loginService.userLogin(user);
            res1.setStatus("doctor");
            if (res == 1) {
                logger.info(res);
                result.setCode("0");
                result.setMsg("注册成功");
                result.setData(res1);
                return result;
            } else {
                logger.error(res);
                result.setCode("1");
                result.setMsg("注册失败");
                return result;
            }
        } else if (("doctor").equals(userType)) {
            if (StringUtils.isEmpty(imgStr)) {
                result.setCode("1");
                result.setMsg("file不可缺省");
                return result;
            } else if (imgStr.indexOf("data:image/png;") != -1) {
                imgStr = imgStr.replace("data:image/png;base64,", "");
                fileName.append(".png");
            } else if (imgStr.indexOf("data:image/jpeg;") != -1) {
                imgStr = imgStr.replace("data:image/jpeg;base64,", "");
                fileName.append(".jpeg");
            } else {
                result.setCode("1");
                result.setMsg("请选择.png.jpg格式的图片");
                return result;
            }
            if (!StringUtils.isEmpty(loginService.getDoctor(username))) {
                result.setCode("1");
                result.setMsg("已注册");
                return result;
            }
            File file = new File(savePath, fileName.toString());
            byte[] fileBytes = Base64.getDecoder().decode(imgStr);
            try {
                FileUtils.writeByteArrayToFile(file, fileBytes);
            } catch (IOException e) {
                e.printStackTrace();
                result.setCode("1");
                result.setMsg("保存失败");
                return result;
            }
            doctor.setId("doctor" + GenerateSequenceUtil.generateSequenceNo());
            doctor.setName(username);
            doctor.setPassword(password);
            //默认不能登录 需审核
            doctor.setStatus("2");
            doctor.setMobile(mobile);
            doctor.setPhoto(sqlPath + fileName.toString());
            int res = loginService.addDoctor(doctor);
            Doctor res1 = loginService.doctorLogin(doctor);
            res1.setStatus("doctor");
            if (res == 1) {
                logger.info(res);
                result.setCode("0");
                result.setMsg("注册成功");
                result.setData(res1);
                return result;
            } else {
                logger.error(res);
                result.setCode("1");
                result.setMsg("注册失败");
                return result;
            }
        } else {
            result.setMsg("用户类型错误");
            result.setCode("1");
            return result;
        }
    }

    /**
     * 统一的登录接口
     *
     * @return
     */
    @PostMapping("/login")
    public Object login(HttpServletRequest request) {
        result = new Result();
        String userType = request.getParameter("userType");
        String username = request.getParameter("name");
        String password = request.getParameter("password");
        if (("user").equals(userType)) {
            user.setName(username);
            user.setPassword(password);
            User res = loginService.userLogin(user);
            res.setStatus("user");
            if (null != res) {
                if (res.getPassword().equals(password) && ("1").equals(res.getStatus())) {
                    result.setMsg("登录成功");
                    result.setCode("0");
                    result.setData(res);
                    return result;
                } else if (("0").equals(res.getStatus())) {
                    result.setMsg("已删除");
                    result.setCode("1");
                    return result;
                } else {
                    result.setMsg("密码错误");
                    result.setCode("1");
                    return result;
                }
            } else {
                result.setMsg("用户名未注册");
                result.setCode("1");
                return result;
            }
        } else if (("doctor").equals(userType)) {
            doctor.setName(username);
            doctor.setPassword(password);
            Doctor res = loginService.doctorLogin(doctor);
            res.setStatus("doctor");
            if (null != res) {
                if (res.getPassword().equals(password) && ("1").equals(res.getStatus())) {
                    result.setMsg("登录成功");
                    result.setCode("0");
                    result.setData(res);
                    return result;
                } else if (("0").equals(res.getStatus())) {
                    result.setMsg("已删除");
                    result.setCode("1");
                    return result;
                } else if (("2").equals(res.getStatus())) {
                    result.setMsg("未审核");
                    result.setCode("1");
                    return result;
                } else {
                    result.setMsg("密码错误");
                    result.setCode("1");
                    return result;
                }
            } else {
                result.setMsg("用户名未注册");
                result.setCode("1");
                return result;
            }
        } else if (("admin").equals(userType)) {
            admin.setName(username);
            admin.setPassword(password);
            Admin res = loginService.adminLogin(admin);
            res.setStatus("admin");
            if (null != res) {
                if (res.getPassword().equals(password)) {
                    result.setMsg("登录成功");
                    result.setCode("0");
                    result.setData(res);
                    return result;
                } else {
                    result.setMsg("密码错误");
                    result.setCode("1");
                    return result;
                }
            } else {
                result.setMsg("用户名未注册");
                result.setCode("1");
                return result;
            }
        } else {
            result.setMsg("用户类型错误");
            result.setCode("1");
            return result;
        }
    }

    /**
     * 忘记密码
     *
     * @return
     */
    @PostMapping("/forgetpwd")
    public Object forgetpwd(HttpServletRequest request) {
        result = new Result();
        String username = request.getParameter("name");
        String password = request.getParameter("password");
        String userType = request.getParameter("userType");
        String mobile = request.getParameter("mobile");
        if (("user").equals(userType)) {
            user.setName(username);
            User res = loginService.userLogin(user);//根据用户名查找用户
            if (res == null) {
                result.setCode("1");
                result.setMsg("手机号与用户名不匹配");
                return result;
            }
            if (!res.getMobile().equals(mobile)) {
                result.setCode("1");
                result.setMsg("手机号与用户名不匹配");
                return result;
            }
            res.setPassword(password);
            loginService.updateInfo(res);//替换数据库密码
            result.setCode("0");
            result.setMsg("密码修改成功");
            return result;
        } else if (("doctor").equals(userType)) {
            doctor.setName(username);
            Doctor res = loginService.doctorLogin(doctor);
            if (res == null) {
                result.setCode("1");
                result.setMsg("手机号与用户名不匹配");
                return result;
            }
            if (!res.getMobile().equals(mobile)) {
                result.setCode("1");
                result.setMsg("手机号与用户名不匹配");
                return result;
            }
            res.setPassword(password);
            loginService.updateDoctor(res);
            result.setCode("0");
            result.setMsg("密码修改成功");
            return result;
        } else {
            result.setMsg("用户类型错误");
            result.setCode("1");
            return result;
        }
    }

}
