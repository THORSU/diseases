package com.diseases.medical.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Note_Comment implements Serializable {
    //评论主键
    private String note_comment_id;
    //帖子主键
    private String note_id;
    //发起人主键
    private String id;
    //发起人类型
    private String user_type;
    //评论内容
    private String note_comment_content;
    //评论时间
    private String note_comment_release_time;
    //状态
    private String status;
}
