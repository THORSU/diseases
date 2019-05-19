package com.diseases.medical.controller;

import com.diseases.medical.pojo.Diseases;
import com.diseases.medical.pojo.Usercases;
import com.diseases.medical.service.DiseaseService;
import com.diseases.medical.utils.GenerateSequenceUtil;
import com.diseases.medical.utils.Result;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/disease")
public class DiseaseController {
    private static Logger logger = Logger.getLogger(LoginController.class);
    @Autowired
    private DiseaseService diseaseService;

    private Result result;

    //获取所有病例
    @GetMapping("/getDiseases")
    public Object getDiseases() {
        result = new Result();
        result.setCode("0");
        result.setMsg("病例列表");
        result.setData(diseaseService.getDisease());
        return result;
    }

    //获取所有病人病例
    @GetMapping("/getUsercase")
    public Object getUsercase() {
        result = new Result();
        result.setCode("0");
        result.setMsg("病人病例列表");
        result.setData(diseaseService.getUsercase());
        return result;
    }

    //根据病例id获取病例
    @PostMapping("/getDiseasesById")
    public Object getDiseasesById(HttpServletRequest request) {
        result = new Result();
        String id = request.getParameter("id");
        Diseases usercases = diseaseService.getDiseaseById(id);
        result.setCode("0");
        result.setMsg("详细病例");
        result.setData(usercases);
        return result;
    }

    //根据用户id获取病例
    @PostMapping("/getUsercaseById")
    public Object getUsercaseById(HttpServletRequest request) {
        result = new Result();
        String id = request.getParameter("id");
        Usercases usercases = diseaseService.getUsercaseById(id);
        result.setCode("0");
        result.setMsg("详细病例");
        result.setData(usercases);
        return result;
    }

    //添加常见病例
    @PostMapping("/addDisease")
    public Object addDisease(HttpServletRequest request) {
        result = new Result();
        //病名
        String diseasesName = request.getParameter("diseasesName");
        //症状
        String diseases = request.getParameter("diseases");
        //预防方案
        String prevention = request.getParameter("prevention");
        Diseases diseases1 = new Diseases();
        diseases1.setDiseases(diseases);
        diseases1.setDiseasesName(diseasesName);
        diseases1.setPrevention(prevention);
        diseases1.setId("disease" + GenerateSequenceUtil.generateSequenceNo());
        diseases1.setStatus("1");
        int res = diseaseService.addDisease(diseases1);
        if (res != 1) {
            result.setCode("1");
            result.setMsg("添加病例失败");
            return result;
        }
        result.setCode("0");
        result.setMsg("添加病例成功");
        return result;
    }

    //添加用户病例
    @PostMapping("/addUsercases")
    public Object addUsercases(HttpServletRequest request) {
        result = new Result();
        //常见病主键
        String caseid = request.getParameter("caseid");
        //标题
        String title = request.getParameter("title");
        //患者名
        String patients = request.getParameter("patients");
        //症状
        String symptoms = request.getParameter("symptoms");
        //化验单
        String among = request.getParameter("among");
        //病情诊断
        String diagnosis = request.getParameter("diagnosis");
        //医嘱
        String advice = request.getParameter("advice");
        Usercases usercases = new Usercases();
        usercases.setAdvice(advice);
        usercases.setAmong(among);
        usercases.setCasesid(caseid);
        usercases.setPatients(patients);
        usercases.setTitle(title);
        usercases.setSymptoms(symptoms);
        usercases.setDiagnosis(diagnosis);
        usercases.setStatus("1");
        usercases.setId("uc" + GenerateSequenceUtil.generateSequenceNo());
        int res = diseaseService.addUsecase(usercases);
        if (res != 1) {
            result.setCode("1");
            result.setMsg("添加病人病例失败");
            return result;
        }
        result.setCode("0");
        result.setMsg("添加病人病例成功");
        return result;
    }
}
