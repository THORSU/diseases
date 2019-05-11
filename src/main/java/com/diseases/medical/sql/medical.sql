# 普通用户
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`
(
    id       VARCHAR(255) NOT NULL PRIMARY KEY,
    nickname VARCHAR(255) NULL,
    name     VARCHAR(255) NULL,
    password VARCHAR(255) NULL,
    status   VARCHAR(255) NULL,
    mobile   VARCHAR(255) NULL
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8;

# 医生
DROP TABLE IF EXISTS `doctor`;
CREATE TABLE `doctor`
(
    id       VARCHAR(255) NOT NULL PRIMARY KEY,
    nickname VARCHAR(255) NULL,
    name     VARCHAR(255) NULL,
    password VARCHAR(255) NULL,
    status   VARCHAR(255) NULL,
    mobile   VARCHAR(255) NULL,
    photo    VARCHAR(255) NULL
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8;

# 管理员
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`
(
    id       INT AUTO_INCREMENT
        PRIMARY KEY,
    name     VARCHAR(255) NULL,
    password VARCHAR(255) NULL,
    status   VARCHAR(255) NULL,
    mobile   VARCHAR(255) NULL
)
    AUTO_INCREMENT = 1,
    ENGINE = InnoDB;

# 文章
DROP TABLE IF EXISTS `note`;
CREATE TABLE `note`
(
    `note_id`             varchar(24) COLLATE utf8_unicode_ci NOT NULL,
    `id`                  varchar(24) COLLATE utf8_unicode_ci NOT NULL,
    `release_time`        varchar(20) COLLATE utf8_unicode_ci    DEFAULT NULL,
    `note_type`           varchar(20) COLLATE utf8_unicode_ci    DEFAULT NULL,
    `note_content`        varchar(20000) COLLATE utf8_unicode_ci DEFAULT NULL,
    `note_likes`          varchar(50) COLLATE utf8_unicode_ci    DEFAULT NULL,
    `note_comment_counts` varchar(50) COLLATE utf8_unicode_ci    DEFAULT NULL,
    `user_type`           varchar(50) COLLATE utf8_unicode_ci    DEFAULT NULL,
    PRIMARY KEY (`id`, `note_id`)
) ENGINE = MyISAM
  AUTO_INCREMENT = 2
  DEFAULT CHARSET = utf8
  COLLATE = utf8_unicode_ci;

# 评论
DROP TABLE IF EXISTS `note_comment`;
CREATE TABLE `note_comment`
(
    `note_comment_id`           varchar(24) COLLATE utf8_unicode_ci NOT NULL,
    `note_id`                   varchar(24) COLLATE utf8_unicode_ci    DEFAULT NULL,
    `id`                        varchar(24) COLLATE utf8_unicode_ci    DEFAULT NULL,
    `note_comment_content`      varchar(20000) COLLATE utf8_unicode_ci DEFAULT NULL,
    `note_comment_release_time` varchar(20) COLLATE utf8_unicode_ci    DEFAULT NULL,
    `user_type`                 varchar(50) COLLATE utf8_unicode_ci    DEFAULT NULL,
    PRIMARY KEY (`note_comment_id`)
) ENGINE = MyISAM
  DEFAULT CHARSET = utf8
  COLLATE = utf8_unicode_ci;