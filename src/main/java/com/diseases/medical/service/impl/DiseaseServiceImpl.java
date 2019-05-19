package com.diseases.medical.service.impl;

import com.diseases.medical.dao.DiseaseDao;
import com.diseases.medical.pojo.Diseases;
import com.diseases.medical.pojo.Usercases;
import com.diseases.medical.service.DiseaseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DiseaseServiceImpl implements DiseaseService {
    @Resource
    private DiseaseDao diseaseDao;

    @Override
    public List<Diseases> getDisease() {
        return diseaseDao.getDisease();
    }

    @Override
    public int addDisease(Diseases diseases) {
        return diseaseDao.addDisease(diseases);
    }

    @Override
    public Diseases getDiseaseById(String id) {
        return diseaseDao.getDiseaseById(id);
    }

    @Override
    public List<Usercases> getUsercase() {
        return diseaseDao.getUsercase();
    }

    @Override
    public int addUsecase(Usercases usercases) {
        return diseaseDao.addUsecase(usercases);
    }

    @Override
    public Usercases getUsercaseById(String id) {
        return diseaseDao.getUsercaseById(id);
    }

    @Override
    public int delUsercases(String id) {
        return diseaseDao.delUsercases(id);
    }

    @Override
    public List<Usercases> getUsercaseByCaseId(String caseId) {
        return diseaseDao.getUsercaseByCaseId(caseId);
    }

    @Override
    public int delDisease(String id) {
        return diseaseDao.delDisease(id);
    }
}
