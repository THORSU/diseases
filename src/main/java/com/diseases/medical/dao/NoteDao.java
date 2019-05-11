package com.diseases.medical.dao;

import com.diseases.medical.pojo.Note;
import com.diseases.medical.pojo.Note_Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NoteDao {

    //根据文章类型获取文章集合
    List<Note> getNoteByType(@Param("note_type") String note_type);

    //根据文章id获取文章
    Note getNoteById(@Param("note_id") String note_id);

    //更新文章
    int updateNote(Note note);

    //根据文章id获取文章评论内容
    List<Note_Comment> getNoteCommentsByNote_id(@Param("note_id") String note_id);

    //插入评论
    int saveComment(Note_Comment note_comment);

    // 发布文章
    int saveNote(Note note);

    //获取帖子列表
    List<Note> getNoteList();
}
