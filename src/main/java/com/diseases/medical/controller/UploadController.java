package com.diseases.medical.controller;

import com.diseases.medical.pojo.Doctor;
import com.diseases.medical.service.LoginService;
import com.diseases.medical.utils.SpringUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/upload")
public class UploadController {

    @Autowired
    private LoginService loginService;

    /**
     * 上传
     *
     * @param request
     * @return
     */
    @PostMapping("/image")
    public Object orderComment(HttpServletRequest request) {

        // 从前台的请求当中取出 file文件类型
        List<MultipartFile> list = ((MultipartHttpServletRequest) request).getFiles("file");
        //上传用户名
        String name = request.getParameter("name");
        String pics = null;
        if (list.size() != 0) {
            //上传图片 调用工具类上传 返回上传后文件的名字
            pics = SpringUploadUtil.uploadPics(list);
            Doctor doctor = loginService.getDoctorByName(name);
            doctor.setPhoto(pics);
            loginService.updateDoctor(doctor);
        }
        // pics = "upload/2019041615094470712493.jpg,upload/2019041615094470712456.jpg"
        return "upload success";
    }
}
