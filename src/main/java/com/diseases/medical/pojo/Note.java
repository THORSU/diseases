package com.diseases.medical.pojo;

import lombok.Data;

import java.io.Serializable;

//帖子
@Data
public class Note implements Serializable {
    //文章id
    private String note_id;
    //用户id
    private String id;
    //发布时间
    private String release_time;
    //标题
    private String title;
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
}
