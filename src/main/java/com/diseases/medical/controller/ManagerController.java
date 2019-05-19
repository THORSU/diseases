package com.diseases.medical.controller;

import com.diseases.medical.pojo.*;
import com.diseases.medical.service.DiseaseService;
import com.diseases.medical.service.LoginService;
import com.diseases.medical.service.NoteService;
import com.diseases.medical.utils.Result;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/manager")
public class ManagerController {
    private static Logger logger = Logger.getLogger(ManagerController.class);

    @Autowired
    private LoginService loginService;

    @Autowired
    private NoteService noteService;

    @Autowired
    private DiseaseService diseaseService;

    private Result result;

    /**
     * 用户列表
     *
     * @return
     */
    @GetMapping("/getUserList")
    public Object getUserList() {
        result = new Result();
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
        result = new Result();
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
        result = new Result();
        result.setCode("0");
        result.setMsg("帖子列表");
        result.setData(noteService.getNoteList());
        return result;
    }

    /**
     * 获取评论列表
     *
     * @return
     */
    @GetMapping("/getCommitList")
    public Object getCommitList() {
        result = new Result();
        result.setCode("0");
        result.setMsg("评论列表");
        result.setData(noteService.getNoteComments());
        return result;
    }

    /**
     * 删除用户
     *
     * @return
     */
    @PostMapping("/deleteUser")
    public Object deleteUser(HttpServletRequest request) {
        result = new Result();
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
    @PostMapping("/deleteDoctor")
    public Object deleteDoctor(HttpServletRequest request) {
        result = new Result();
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
    @PostMapping("/auditDoctor")
    public Object auditDoctor(HttpServletRequest request) {
        result = new Result();
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

    /**
     * 删除评论
     *
     * @param request
     * @return
     */
    @PostMapping("/delComment")
    public Object delComment(HttpServletRequest request) {
        result = new Result();
        //评论主键
        String noteCommentId = request.getParameter("noteCommentId");
        Note_Comment note_comment = noteService.getNoteCommentById(noteCommentId);
        if (note_comment == null) {
            result.setCode("1");
            result.setMsg("无此评论");
            return result;
        }
        int res = noteService.delComment(noteCommentId);
        if (res != 1) {
            result.setCode("1");
            result.setMsg("删除失败");
            return result;
        }
        if (StringUtils.isEmpty(note_comment.getNote_id())) {
            result.setCode("1");
            result.setMsg("帖子表数据异常");
            return result;
        }
        Note n = noteService.getNoteById(note_comment.getNote_id());
        n.setNote_comment_counts(String.valueOf(Integer.parseInt(n.getNote_comment_counts()) - 1));
        int res1 = noteService.updateNote(n);
        if (res1 != 1) {
            result.setCode("1");
            result.setMsg("评论数修改失败");
            return result;
        }
        result.setCode("0");
        result.setMsg("删除成功");
        return result;
    }

    /**
     * 删除帖子
     *
     * @param request
     * @return
     */
    @PostMapping("/delNote")
    public Object delNote(HttpServletRequest request) {
        result = new Result();
        //帖子主键
        String noteId = request.getParameter("noteId");
        Note n = noteService.getNoteById(noteId);
        if (n == null) {
            result.setCode("1");
            result.setMsg("无此帖子");
            return result;
        }
        List<Note_Comment> listComment = noteService.getNoteCommentsByNote_id(noteId);
        for (int i = 0; i < listComment.size(); i++) {
            if (!StringUtils.isEmpty(listComment.get(i).getNote_comment_id())) {
                int res = noteService.delComment(listComment.get(i).getNote_comment_id());
                if (res != 1) {
                    result.setCode("1");
                    result.setMsg("评论删除失败");
                    return result;
                }
            }
        }
        n.setStatus("0");
        int res = noteService.updateNote(n);
        if (res != 1) {
            result.setCode("1");
            result.setMsg("帖子删除失败");
            return result;
        }
        result.setCode("0");
        result.setMsg("删除成功");
        return result;
    }

    /**
     * 删除病人病例
     *
     * @param request
     * @return
     */
    @PostMapping("/delUsercases")
    public Object delUsercases(HttpServletRequest request) {
        result = new Result();
        String id = request.getParameter("id");
        Usercases usercases = diseaseService.getUsercaseById(id);
        if (usercases == null) {
            result.setCode("1");
            result.setMsg("无此病人病例");
            return result;
        }
        int res = diseaseService.delUsercases(id);
        if (res != 1) {
            result.setCode("1");
            result.setMsg("删除失败");
            return result;
        }
        result.setCode("0");
        result.setMsg("删除成功");
        return result;
    }


    /**
     * 删除病人病例
     *
     * @param request
     * @return
     */
    @PostMapping("/delDisease")
    public Object delDisease(HttpServletRequest request) {
        result = new Result();
        String id = request.getParameter("id");
        Diseases diseases = diseaseService.getDiseaseById(id);
        if (diseases == null) {
            result.setCode("1");
            result.setMsg("无此病例");
            return result;
        }
        List<Usercases> usercases = diseaseService.getUsercaseByCaseId(diseases.getId());
        for (int i = 0; i < usercases.size(); i++) {
            diseaseService.delUsercases(usercases.get(i).getId());
        }
        int res = diseaseService.delDisease(id);
        if (res != 1) {
            result.setCode("1");
            result.setMsg("删除失败");
            return result;
        }
        result.setCode("0");
        result.setMsg("删除成功");
        return result;
    }
}
