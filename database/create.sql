create database hiphop_ghetto;
use hiphop_ghetto;
# 用户表
create table user (
    id bigint(20) primary key , # 用户ID（主键）
    username varchar(20) not null unique , # 用户名
    password varchar(100) not null , # 密码
    name varchar(20) default null , # 姓名
    id_card varchar(18) default null unique , # 身份证号
    phone varchar(20) default null unique , # 手机号
    email varchar(100) default null unique ,
    sex tinyint(1) default 0 , # 性别（0-未知，1-男，2-女）
    avatar varchar(255) default null , # 头像url
    create_time datetime not null default current_timestamp , # 创建时间
    update_time datetime not null default current_timestamp on update current_timestamp , # 修改时间
    status tinyint(1) NOT NULL DEFAULT 0 # 数据状态（0-正常，1-已删除）
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
# 评分表
create table score (
                       id bigint(20) primary key , # 评分Id（主键）
                       user_id bigint(20) not null , # 发起用户Id（外键）
                       title varchar(20) not null , # 标题
                       description text , # 描述
                       views bigint(10) default 0 ,
                       create_time datetime not null , # 创建时间
                       update_time datetime not null , # 修改时间
                       status tinyint(1) NOT NULL DEFAULT 0 , # 数据状态（0-正常，1-已删除）
                       constraint fk_score_user_id foreign key (user_id)
                           references user(id)
) engine =InnoDB default charset =utf8mb4;
# 评分图片表
create table score_image (
                             id bigint auto_increment primary key , # 图片Id（主键）
                             score_id bigint(20) not null , # 评分Id（外键）
                             url varchar(255) not null , # 图片URL
                             constraint fk_score_image_score_id foreign key (score_id)
                                 references score(id)
) engine =InnoDB default charset =utf8mb4;
# 评分详情表
create table score_detail (
                              score_id bigint(20) primary key, # 活动Id（主键）
                              one bigint(10) default 0 , # 1分
                              two bigint(10) default 0 , # 2分
                              three bigint(10) default 0 , # 3分
                              four bigint(10) default 0 , # 4分
                              five bigint(10) default 0 , # 5分
                              six bigint(10) default 0 , # 6分
                              seven bigint(10) default 0 , # 7分
                              eight bigint(10) default 0 , # 8分
                              nine bigint(10) default 0 , # 9分
                              ten bigint(10) default 0 , # 10分
                              constraint fk_score_detail_score_id foreign key (score_id)
                                  references score(id)
) engine =InnoDB default charset =utf8mb4;
# 帖子表
create table posting (
                         id bigint(20) primary key , # 帖子ID（主键）
                         user_id bigint(20) not null , # 发起用户Id（外键）
                         title varchar(20) not null , # 标题
                         content text , # 内容
                         view bigint(10) default 0 , # 浏览量
                         `like` bigint(10) default 0 , # 点赞
                         create_time datetime not null , # 创建时间
                         update_time datetime not null , # 修改时间
                         status tinyint(1) NOT NULL DEFAULT 0 , # 数据状态（0-正常，1-已删除）
                         constraint fk_posting_user_id foreign key (user_id)
                             references user(id)
) engine =InnoDB default charset =utf8mb4;
# 帖子图片表
create table posting_image (
                               id bigint auto_increment primary key , # 图片Id（主键）
                               posting_id bigint(20) not null , # 帖子Id（外键）
                               url varchar(255) not null , # 图片URL
                               constraint fk_posting_image_posting_id foreign key (posting_id)
                                   references posting(id)
) engine =InnoDB default charset =utf8mb4;
# 帖子评论表
create table posting_comment (
                                 id bigint(20) primary key , # 评论ID（主键）
                                 posting_id bigint(20) not null , # 帖子ID（外键）
                                 user_id bigint(20) not null , # 评论用户ID（外键）
                                 content text,
                                 create_time datetime not null , # 创建时间
                                 update_time datetime not null , # 修改时间
                                 status tinyint(1) NOT NULL DEFAULT 0, # 数据状态（0-正常，1-已删除）
                                 constraint fk_posting_comment_posting_id foreign key (posting_id)
                                     references posting(id),
                                 constraint fk_posting_comment_user_id foreign key (user_id)
                                     references user(id)
) engine =InnoDB default charset =utf8mb4;
# 帖子点赞记录表
create table posting_like (
                              id bigint auto_increment primary key , # 点赞记录ID
                              posting_id bigint(20) not null , # 帖子ID（外键）
                              user_id bigint(20) not null , # 点赞用户ID
                              create_time datetime not null , # 创建时间
                              update_time datetime not null , # 修改时间
                              status tinyint(1) NOT NULL DEFAULT 0 , # 数据状态（0-正常，1-已删除）
                              constraint fk_posting_like_posting_id foreign key (posting_id)
                                  references posting(id),
                              constraint fk_posting_like_user_id foreign key (user_id)
                                  references user(id)
) engine =InnoDB default charset =utf8mb4;
















