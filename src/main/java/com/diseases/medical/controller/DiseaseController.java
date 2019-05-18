package com.diseases.medical.controller;

import com.diseases.medical.pojo.Diseases;
import com.diseases.medical.pojo.Usercases;
import com.diseases.medical.service.DiseaseService;
import com.diseases.medical.utils.Result;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/disease")
public class DiseaseController {
    private static Logger logger = Logger.getLogger(LoginController.class);
    @Autowired
    private DiseaseService diseaseService;

    private Result result = new Result();

    //获取所有病例
    @RequestMapping("/getDiseases")
    public Object getDiseases() {
        result.setCode("0");
        result.setMsg("病例列表");
        result.setData(diseaseService.getDisease());
        return result;
    }

    //获取所有病人病例
    @RequestMapping("/getUsercase")
    public Object getUsercase() {
        result.setCode("0");
        result.setMsg("病人病例列表");
        result.setData(diseaseService.getUsercase());
        return result;
    }

    //根据病例id获取病例
    @RequestMapping("/getDiseasesById")
    public Object getDiseasesById(HttpServletRequest request) {
        String id = request.getParameter("id");
        Diseases usercases = diseaseService.getDiseaseById(id);
        result.setCode("0");
        result.setMsg("详细病例");
        result.setData(usercases);
        return result;
    }

    //根据用户id获取病例
    @RequestMapping("/getUsercaseById")
    public Object getUsercaseById(HttpServletRequest request) {
        String id = request.getParameter("id");
        Usercases usercases = diseaseService.getUsercaseById(id);
        result.setCode("0");
        result.setMsg("详细病例");
        result.setData(usercases);
        return result;
    }
}
