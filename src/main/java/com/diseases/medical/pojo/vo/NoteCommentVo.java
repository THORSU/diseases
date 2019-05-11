package com.diseases.medical.pojo.vo;

import lombok.Data;

@Data
public class NoteCommentVo {

    //评论人名
    private String commentName;
    //评论人类型
    private String listComment_type;
    //评论内容
    private String note_comment_content;
    //评论时间
    private String note_comment_release_time;
}
