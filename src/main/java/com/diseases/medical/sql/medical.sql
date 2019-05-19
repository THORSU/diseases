# 普通用户
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    id       VARCHAR(255) NOT NULL PRIMARY KEY COMMENT '主键',
    nickname VARCHAR(255) NULL COMMENT '昵称',
    name     VARCHAR(255) NULL COMMENT '用户名',
    password VARCHAR(255) NULL COMMENT '密码',
    status   VARCHAR(255) NULL COMMENT '状态',
    mobile   VARCHAR(255) NULL COMMENT '手机号'
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8;

# 医生
DROP TABLE IF EXISTS `doctor`;
CREATE TABLE `doctor`
(
    id       VARCHAR(255) NOT NULL PRIMARY KEY COMMENT '主键',
    nickname VARCHAR(255) NULL COMMENT '昵称',
    name     VARCHAR(255) NULL COMMENT '用户名',
    password VARCHAR(255) NULL COMMENT '密码',
    status   VARCHAR(255) NULL COMMENT '状态',
    mobile   VARCHAR(255) NULL COMMENT '手机号',
    photo    VARCHAR(255) NULL COMMENT '照片路径'
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8;

# 管理员
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`
(
    id       INT AUTO_INCREMENT PRIMARY KEY COMMENT '主键',
    name     VARCHAR(255) NULL COMMENT '用户名',
    password VARCHAR(255) NULL COMMENT '密码',
    status   VARCHAR(255) NULL COMMENT '状态',
    mobile   VARCHAR(255) NULL COMMENT '手机号'
)
    ENGINE = InnoDB;

# 帖子
DROP TABLE IF EXISTS `note`;
CREATE TABLE `note`
(
    `note_id`             varchar(24) COLLATE utf8_unicode_ci NOT NULL COMMENT '主键',
    `id`                  varchar(24) COLLATE utf8_unicode_ci NOT NULL COMMENT '发帖人主键',
    `title`               varchar(24)                         NOT NULL COMMENT '标题',
    `release_time`        varchar(20) COLLATE utf8_unicode_ci    DEFAULT NULL COMMENT '发帖时间',
    `note_type`           varchar(20) COLLATE utf8_unicode_ci    DEFAULT NULL COMMENT '帖子类型',
    `note_content`        varchar(20000) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '帖子内容',
    `note_likes`          varchar(50) COLLATE utf8_unicode_ci    DEFAULT NULL COMMENT '点赞数',
    `note_comment_counts` varchar(50) COLLATE utf8_unicode_ci    DEFAULT NULL COMMENT '评论数',
    `user_type`           varchar(50) COLLATE utf8_unicode_ci    DEFAULT NULL COMMENT '发帖人类型',
    `status`              varchar(50) COLLATE utf8_unicode_ci    DEFAULT NULL COMMENT '帖子状态',
    PRIMARY KEY (`id`, `note_id`)
) ENGINE = MyISAM
  DEFAULT CHARSET = utf8
  COLLATE = utf8_unicode_ci;

# 评论
DROP TABLE IF EXISTS `note_comment`;
CREATE TABLE `note_comment`
(
    `note_comment_id`           varchar(24) COLLATE utf8_unicode_ci NOT NULL COMMENT '主键',
    `note_id`                   varchar(24) COLLATE utf8_unicode_ci    DEFAULT NULL COMMENT '帖子主键',
    `id`                        varchar(24) COLLATE utf8_unicode_ci    DEFAULT NULL COMMENT '评论人主键',
    `note_comment_content`      varchar(20000) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '评论内容',
    `note_comment_release_time` varchar(20) COLLATE utf8_unicode_ci    DEFAULT NULL COMMENT '评论时间',
    `user_type`                 varchar(50) COLLATE utf8_unicode_ci    DEFAULT NULL COMMENT '用户类型',
    `status`                    varchar(50) COLLATE utf8_unicode_ci    DEFAULT NULL COMMENT '评论状态',
    PRIMARY KEY (`note_comment_id`)
) ENGINE = MyISAM
  DEFAULT CHARSET = utf8
  COLLATE = utf8_unicode_ci;

# 常见病
DROP TABLE IF EXISTS `diseases`;
CREATE TABLE `diseases`
(
    `id`           VARCHAR(255) NOT NULL PRIMARY KEY COMMENT '主键',
    `diseasesName` VARCHAR(255) NULL COMMENT '病名',
    `diseases`     VARCHAR(255) NULL COMMENT '病名症状',
    `prevention`   VARCHAR(255) NULL COMMENT '预防方案',
    `status`       VARCHAR(255) NULL COMMENT '状态'
) ENGINE = MyISAM
  DEFAULT CHARSET = utf8
  COLLATE = utf8_unicode_ci;

# 病例主表
DROP TABLE IF EXISTS `usercases`;
CREATE TABLE `usercases`
(
    `id`        VARCHAR(255) NOT NULL PRIMARY KEY COMMENT '主键',
    `casesid`   VARCHAR(255) NULL COMMENT '病例id',
    `title`     VARCHAR(255) NULL COMMENT '标题',
    `patients`  VARCHAR(255) NULL COMMENT '患者名',
    `symptoms`  VARCHAR(255) NULL COMMENT '患者症状',
    `among`     VARCHAR(255) NULL COMMENT '化验单',
    `diagnosis` VARCHAR(255) NULL COMMENT '病情诊断',
    `advice`    VARCHAR(255) NULL COMMENT '医嘱',
    `status`    VARCHAR(255) NULL COMMENT '状态'
) ENGINE = MyISAM
  DEFAULT CHARSET = utf8
  COLLATE = utf8_unicode_ci;