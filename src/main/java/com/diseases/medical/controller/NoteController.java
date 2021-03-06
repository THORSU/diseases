package com.diseases.medical.controller;

import com.diseases.medical.pojo.Note;
import com.diseases.medical.pojo.Note_Comment;
import com.diseases.medical.pojo.vo.NoteCommentVo;
import com.diseases.medical.pojo.vo.NoteVo;
import com.diseases.medical.service.LoginService;
import com.diseases.medical.service.NoteService;
import com.diseases.medical.utils.DataUtil;
import com.diseases.medical.utils.GenerateSequenceUtil;
import com.diseases.medical.utils.Result;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/note")
public class NoteController {
    private static Logger logger = Logger.getLogger(NoteController.class);

    @Autowired
    private NoteService noteService;

    @Autowired
    private LoginService loginService;

    private Result result;

    /**
     * 获取所有帖子
     *
     * @return
     */
    @PostMapping("/getPublicNote")
    public Object getPublicNote(HttpServletRequest request) {
        result = new Result();
        //帖子类型
        String type = request.getParameter("note_type");
        if (StringUtils.isEmpty(type)) {
            result.setCode("0");
            result.setMsg("帖子列表");
            result.setData(noteService.getNoteList());
            return result;
        } else {
            result.setCode("0");
            result.setMsg("帖子列表");
            result.setData(noteService.getNoteByType(type));
            return result;
        }
    }


    /**
     * 更新帖子点赞数
     *
     * @return
     */
    @PostMapping("/updateLikes")
    public Object updateLikes(HttpServletRequest request) {
        result = new Result();
        //帖子id
        String noteId = request.getParameter("note_id");
        try {
            Note n = noteService.getNoteById(noteId);
            Integer num = Integer.parseInt(n.getNote_likes()) + 1;
            n.setNote_likes(String.valueOf(num));
            noteService.updateNote(n);
            result.setCode("0");
            result.setMsg("点赞成功");
            return result;
        } catch (Exception e) {
            result.setCode("1");
            result.setMsg("点赞失败");
            return result;
        }
    }

    /**
     * 获取帖子详情
     *
     * @return
     */
    @PostMapping("/getSingleNote")
    public Object getSingleNote(HttpServletRequest request) {
        result = new Result();
        String noteId = request.getParameter("note_id");
        NoteVo noteVo = new NoteVo();
        try {
            Note note = noteService.getNoteById(noteId);
            //获取发起人姓名
            String name = "";
            //获取发起人身份
            String name_type = "";
            if (!StringUtils.isEmpty(note.getId()) && note.getUser_type().equals("user")) {
                name_type = "用户";
                name = loginService.getUserById(note.getId()).getName();
            } else if (!StringUtils.isEmpty(note.getId()) && note.getUser_type().equals("doctor")) {
                name_type = "医生";
                name = loginService.getDoctorById(note.getId()).getName();
            } else if (!StringUtils.isEmpty(note.getId()) && note.getUser_type().equals("admin")) {
                name_type = "管理员";
                name = loginService.getAdminById(note.getId()).getName();
            }
            //获取评论
            List<Note_Comment> listComment = noteService.getNoteCommentsByNote_id(noteId);
            List<NoteCommentVo> list = new ArrayList<>();
            for (int i = 0; i < listComment.size(); i++) {
                NoteCommentVo noteCommentVo = new NoteCommentVo();
                //获取评论人姓名
                String commentName = "";
                //获取评论人身份
                String listComment_type = "";
                if (!StringUtils.isEmpty(listComment.get(i).getId()) && listComment.get(i).getUser_type().equals("user")) {
                    listComment_type = "用户";
                    commentName = loginService.getUserById(listComment.get(i).getId()).getName();
                } else if (!StringUtils.isEmpty(listComment.get(i).getId()) && listComment.get(i).getUser_type().equals("doctor")) {
                    listComment_type = "医生";
                    commentName = loginService.getDoctorById(listComment.get(i).getId()).getName();
                } else if (!StringUtils.isEmpty(listComment.get(i).getId()) && listComment.get(i).getUser_type().equals("admin")) {
                    listComment_type = "管理员";
                    commentName = loginService.getAdminById(listComment.get(i).getId()).getName();
                }
                noteCommentVo.setCommentName(commentName);
                noteCommentVo.setListComment_type(listComment_type);
                noteCommentVo.setNote_comment_content(listComment.get(i).getNote_comment_content());
                noteCommentVo.setNote_comment_release_time(listComment.get(i).getNote_comment_release_time());
                list.add(noteCommentVo);
            }
            //发帖人id
            noteVo.setId(note.getId());
            //发帖人
            noteVo.setName(name);
            //发帖人用户身份
            noteVo.setUser_type(name_type);
            //帖子id
            noteVo.setNote_id(note.getNote_id());
            //帖子发表时间
            noteVo.setRelease_time(note.getRelease_time());
            //发帖用户身份
            noteVo.setNote_type(note.getNote_type());
            //帖子内容
            noteVo.setNote_content(note.getNote_content());
            //帖子评论数
            noteVo.setNote_comment_counts(note.getNote_comment_counts());
            //帖子点赞数
            noteVo.setNote_likes(note.getNote_likes());
            //标题
            noteVo.setTitle(note.getTitle());
            //评论列表
            noteVo.setList(list);
            result.setCode("0");
            result.setMsg("帖子列表");
            result.setData(noteVo);
            return result;
        } catch (Exception e) {
            result.setCode("1");
            result.setMsg("异常");
            return result;
        }
    }

    /**
     * 发评论
     *
     * @return
     */
    @PostMapping("/releaseComment")
    public Object releaseComment(HttpServletRequest request) {
        result = new Result();
        //评论内容
        String note_comment_content = request.getParameter("note_comment_content");
        //贴子id
        String note_id = request.getParameter("note_id");
        //评论人id
        String id = request.getParameter("id");
        //评论人类型
        String type = request.getParameter("user_type");

        try {
            //发评论
            Note n = noteService.getNoteById(note_id);
            Note_Comment nc = new Note_Comment();
            nc.setNote_comment_id("nc" + GenerateSequenceUtil.generateSequenceNo());
            nc.setNote_id(note_id);
            nc.setNote_comment_release_time(DataUtil.currentDate("yyyy-MM-dd  HH:mm:ss"));
            nc.setNote_comment_content(note_comment_content);
            nc.setUser_type(type);
            nc.setId(id);
            nc.setStatus("1");
            //插评论
            noteService.saveComment(nc);
            //评论数+1
            n.setNote_comment_counts(String.valueOf(Integer.parseInt(n.getNote_comment_counts()) + 1));
            //更新评论数
            noteService.updateNote(n);
            result.setCode("0");
            result.setMsg("评论成功");
            return result;
        } catch (Exception e) {
            result.setCode("0");
            result.setMsg("评论失败");
            return result;
        }
    }

    /**
     * 发帖子
     *
     * @return
     */
    @PostMapping("/ReleaseNote")
    public Object ReleaseNote(HttpServletRequest request) {
        result = new Result();
        //发帖人id
        String id = request.getParameter("id");
        //帖子标题
        String title = request.getParameter("title");
        //帖子内容
        String note_content = request.getParameter("note_content");
        //发帖人类型
        String user_type = request.getParameter("user_type");
        //帖子类型
        String type = request.getParameter("note_type");

        //初始化一个帖子对象
        Note nt = new Note();
        //表主键
        nt.setNote_id("note" + GenerateSequenceUtil.generateSequenceNo());
        //发帖人id
        nt.setId(id);
        //帖子标题
        nt.setTitle(title);
        //发帖时间
        nt.setRelease_time(DataUtil.currentDate("yyyy-MM-dd  HH:mm:ss"));
        //帖子类型
        nt.setNote_type(type);
        //评论数
        nt.setNote_comment_counts("0");
        //点赞数
        nt.setNote_likes("0");
        //帖子内容
        nt.setNote_content(note_content);
        //发帖人类型
        nt.setUser_type(user_type);
        //状态位（1：未删除，0:已删除）
        nt.setStatus("1");
        int res = noteService.saveNote(nt);
        logger.info("帖子基本信息插入成功！");
        if (res == 1) {
            result.setCode("0");
            result.setMsg("发帖成功");
            return result;
        }
        result.setCode("1");
        result.setMsg("发帖失败");
        return result;
    }
}
