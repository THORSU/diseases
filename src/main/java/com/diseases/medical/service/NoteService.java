package com.diseases.medical.service;

import com.diseases.medical.pojo.Note;
import com.diseases.medical.pojo.Note_Comment;

import java.util.List;

public interface NoteService {

    //根据文章类型获取文章集合
    List<Note> getNoteByType(String note_type);

    //根据文章id获取文章
    Note getNoteById(String note_id);

    //更新文章
    int updateNote(Note note);

    // 根据文章id获取文章评论内容
    List<Note_Comment> getNoteCommentsByNote_id(String note_id);

    // 插入评论
    int saveComment(Note_Comment note_comment);

    // 发布文章
    int saveNote(Note note);

    //获取帖子列表
    List<Note> getNoteList();

    //获取所有评论
    List<Note_Comment> getNoteComments();

    //删除评论
    int delComment(String commentId);

    //获取某个评论
    Note_Comment getNoteCommentById(String commentId);

}
