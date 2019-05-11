package com.diseases.medical.pojo.vo;

import lombok.Data;

import java.util.List;

@Data
public class NoteVo {

    //文章id
    private String note_id;
    //用户id
    private String id;
    //发起人姓名
    private String name;
    //发布时间
    private String release_time;
    //文章类型
    private String note_type;
    //文章内容
    private String note_content;
    //点赞数
    private String note_likes;
    //发起人类型
    private String user_type;
    //评论数
    private String note_comment_counts;
    //评论
    private List<NoteCommentVo> list;
}
