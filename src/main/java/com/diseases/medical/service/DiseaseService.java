package com.diseases.medical.service;

import com.diseases.medical.pojo.Diseases;
import com.diseases.medical.pojo.Usercases;

import java.util.List;

public interface DiseaseService {

    //获取所有常见病例
    List<Diseases> getDisease();

    //添加常见病例
    int addDisease(Diseases diseases);

    //根据病例id获取常见病例
    Diseases getDiseaseById(String id);

    //获取病人病例
    List<Usercases> getUsercase();

    //添加病人病例
    int addUsecase(Usercases usercases);

    //获取某人详细病例
    Usercases getUsercaseById(String id);


}
