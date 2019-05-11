package com.diseases.medical.controller;

import com.diseases.medical.pojo.Note;
import com.diseases.medical.pojo.Note_Comment;
import com.diseases.medical.pojo.vo.NoteCommentVo;
import com.diseases.medical.pojo.vo.NoteVo;
import com.diseases.medical.service.LoginService;
import com.diseases.medical.service.NoteService;
import com.diseases.medical.utils.GenerateSequenceUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 获取所有帖子
     *
     * @return
     */
    @GetMapping("/getPublicNote")
    public Object getPublicNote() {
        return noteService.getNoteList();
    }


    /**
     * 更新帖子点赞数
     *
     * @param note
     * @return
     */
    @PostMapping("/updateLikes")
    public Object updateLikes(@RequestBody Note note) {
        String noteId = note.getNote_id();
        String noteLikes = note.getNote_likes();
        try {
            Note n = noteService.getNoteById(noteId);
            n.setNote_likes(noteLikes);
            noteService.updateNote(n);
            return "update success";
        } catch (Exception e) {
            return "0";
        }
    }

    /**
     * 获取帖子详情
     *
     * @param noteId
     * @return
     */
    @GetMapping("/getSingleNote")
    public Object getSingleNote(@RequestParam String noteId) {
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
                }
                noteCommentVo.setCommentName(commentName);
                noteCommentVo.setListComment_type(listComment_type);
                noteCommentVo.setNote_comment_content(listComment.get(i).getNote_comment_content());
                noteCommentVo.setNote_comment_release_time(listComment.get(i).getNote_comment_release_time());
                list.add(noteCommentVo);
            }
            //帖子id
            noteVo.setNote_id(note.getNote_id());
            //帖子发表时间
            noteVo.setRelease_time(note.getRelease_time());
            //发帖人
            noteVo.setName(name);
            //发帖用户身份
            noteVo.setNote_type(name_type);
            //帖子内容
            noteVo.setNote_content(note.getNote_content());
            //帖子评论数
            noteVo.setNote_comment_counts(note.getNote_comment_counts());
            //帖子点赞数
            noteVo.setNote_likes(note.getNote_likes());
            //评论列表
            noteVo.setList(list);
            return noteVo;
        } catch (Exception e) {
            return "未知异常";
        }
    }

    /**
     * 发评论
     *
     * @param note_comment
     * @return
     */
    @PostMapping("/releaseComment")
    public Object releaseComment(@RequestBody Note_Comment note_comment) {
        //评论内容
        String note_comment_content = note_comment.getNote_comment_content();
        //评论id
        String note_id = note_comment.getNote_id();
        //评论时间
        String release_time = note_comment.getNote_comment_release_time();
        //评论人
        String id = note_comment.getId();
        //评论人类型
        String type = note_comment.getUser_type();

        try {
            Note n = noteService.getNoteById(note_id);
            Note_Comment nc = new Note_Comment();
            nc.setNote_comment_id("nc" + GenerateSequenceUtil.generateSequenceNo());
            nc.setNote_id(note_id);
            nc.setNote_comment_release_time(release_time);
            nc.setNote_comment_content(note_comment_content);
            nc.setUser_type(type);
            nc.setId(id);
            noteService.saveComment(nc);
            //评论数+1
            n.setNote_comment_counts(String.valueOf(Integer.parseInt(n.getNote_comment_counts()) + 1));
            noteService.updateNote(n);
            return "success";
        } catch (Exception e) {
            return "fail";
        }
    }

    /**
     * 发帖子
     *
     * @param note
     * @return
     */
    @PostMapping("/ReleaseNote")
    public Object ReleaseNote(@RequestBody Note note) {
        //发帖人id
        String id = note.getId();
        //帖子内容
        String note_content = note.getNote_content();
        //发帖人类型
        String user_type = note.getUser_type();
        //发帖时间
        String release_time = note.getRelease_time();
        //帖子类型
        String type = note.getNote_type();
        Note nt = new Note();
        nt.setNote_id("note" + GenerateSequenceUtil.generateSequenceNo());
        nt.setId(id);
        nt.setRelease_time(release_time);
        nt.setNote_type(type);
        nt.setNote_comment_counts("0");
        nt.setNote_likes("0");
        nt.setNote_content(note_content);
        nt.setUser_type(user_type);
        int res = noteService.saveNote(nt);
        logger.info("帖子基本信息插入成功！");
        if (res == 1) {
            return "success";// 帖子发布成功
        }
        return "fail";
    }
}
