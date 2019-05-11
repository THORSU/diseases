package com.diseases.medical.controller;

import com.diseases.medical.service.LoginService;
import com.diseases.medical.service.NoteService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manager")
public class ManagerController {
    private static Logger logger = Logger.getLogger(ManagerController.class);

    @Autowired
    private LoginService loginService;

    @Autowired
    private NoteService noteService;

    /**
     * 用户列表
     *
     * @return
     */
    @GetMapping("/getUserList")
    public Object getUserList() {
        return loginService.getUserList();
    }

    /**
     * 获取医生列表
     *
     * @return
     */
    @GetMapping("/getDoctorList")
    public Object getDoctorList() {
        return loginService.getDoctorList();
    }

    /**
     * 获取帖子列表
     *
     * @return
     */
    @GetMapping("/getNoteList")
    public Object getNoteList() {
        return noteService.getNoteList();
    }
}
