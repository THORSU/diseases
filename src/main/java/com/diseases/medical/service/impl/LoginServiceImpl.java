package com.diseases.medical.service.impl;

import com.diseases.medical.dao.LoginDao;
import com.diseases.medical.pojo.Admin;
import com.diseases.medical.pojo.Doctor;
import com.diseases.medical.pojo.User;
import com.diseases.medical.service.LoginService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private LoginDao loginDao;

    @Override
    public int addUser(User user) {
        return loginDao.addUser(user);
    }

    @Override
    public String getUser(String username) {
        return loginDao.getUser(username);
    }

    @Override
    public int addDoctor(Doctor doctor) {
        return loginDao.addDoctor(doctor);
    }

    @Override
    public String getDoctor(String doctorname) {
        return loginDao.getDoctor(doctorname);
    }

    @Override
    public User userLogin(User user) {
        return loginDao.userLogin(user);
    }

    @Override
    public Doctor doctorLogin(Doctor doctor) {
        return loginDao.doctorLogin(doctor);
    }

    @Override
    public Admin adminLogin(Admin admin) {
        return loginDao.adminLogin(admin);
    }

    @Override
    public User getUserById(String userId) {
        return loginDao.getUserById(userId);
    }

    @Override
    public Doctor getDoctorById(String doctorId) {
        return loginDao.getDoctorById(doctorId);
    }

    @Override
    public User getUserByName(String name) {
        return loginDao.getUserByName(name);
    }

    @Override
    public Doctor getDoctorByName(String name) {
        return loginDao.getDoctorByName(name);
    }

    @Override
    public int updateInfo(User user) {
        return loginDao.updateInfo(user);
    }

    @Override
    public int updateDoctor(Doctor doctor) {
        return loginDao.updateDoctor(doctor);
    }

    @Override
    public List<User> getUserList() {
        return loginDao.getUserList();
    }

    @Override
    public List<Doctor> getDoctorList() {
        return loginDao.getDoctorList();
    }
}
