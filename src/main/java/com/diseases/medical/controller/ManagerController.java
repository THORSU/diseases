package com.diseases.medical.controller;

import com.diseases.medical.pojo.Doctor;
import com.diseases.medical.pojo.User;
import com.diseases.medical.service.LoginService;
import com.diseases.medical.service.NoteService;
import com.diseases.medical.utils.Result;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/manager")
public class ManagerController {
    private static Logger logger = Logger.getLogger(ManagerController.class);

    @Autowired
    private LoginService loginService;

    @Autowired
    private NoteService noteService;

    private Result result = new Result();
    /**
     * 用户列表
     *
     * @return
     */
    @GetMapping("/getUserList")
    public Object getUserList() {
        result.setCode("0");
        result.setMsg("用户列表");
        result.setData(loginService.getUserList());
        return result;
    }

    /**
     * 获取医生列表
     *
     * @return
     */
    @GetMapping("/getDoctorList")
    public Object getDoctorList() {
        result.setCode("0");
        result.setMsg("医生列表");
        result.setData(loginService.getDoctorList());
        return result;
    }

    /**
     * 获取帖子列表
     *
     * @return
     */
    @GetMapping("/getNoteList")
    public Object getNoteList() {
        result.setCode("0");
        result.setMsg("帖子列表");
        result.setData(noteService.getNoteList());
        return result;
    }

    /**
     * 删除用户
     *
     * @return
     */
    @GetMapping("/deleteUser")
    public Object deleteUser(HttpServletRequest request) {
        String userId = request.getParameter("userId");
        User res = loginService.getUserById(userId);
        if (res == null) {
            result.setCode("1");
            result.setMsg("无此用户");
            return result;
        }
        res.setStatus("0");
        loginService.updateInfo(res);
        result.setCode("0");
        result.setMsg("删除成功");
        return result;
    }

    /**
     * 删除医生
     *
     * @return
     */
    @GetMapping("/deleteDoctor")
    public Object deleteDoctor(HttpServletRequest request) {
        String doctorId = request.getParameter("doctorId");
        Doctor res = loginService.getDoctorById(doctorId);
        if (res == null) {
            result.setCode("1");
            result.setMsg("无此用户");
            return result;
        }
        res.setStatus("0");
        loginService.updateDoctor(res);
        result.setCode("0");
        result.setMsg("删除成功");
        return result;
    }

    /**
     * 审核医生
     *
     * @return
     */
    @GetMapping("/auditDoctor")
    public Object auditDoctor(HttpServletRequest request) {
        String doctorId = request.getParameter("doctorId");
        Doctor res = loginService.getDoctorById(doctorId);
        if (res == null) {
            result.setCode("1");
            result.setMsg("无此用户");
            return result;
        }
        res.setStatus("1");
        loginService.updateDoctor(res);
        result.setCode("0");
        result.setMsg("审核成功");
        return result;
    }
}
