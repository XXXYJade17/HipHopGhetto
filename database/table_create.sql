drop database  hiphop_ghetto;
create database hiphop_ghetto;
use hiphop_ghetto;
# 用户表
create table user (
    id bigint primary key , # 用户ID（雪花算法生成）
    username varchar(20) not null , # 用户名
    password varchar(100) not null , # 密码
    phone varchar(20) default null , # 手机号
    email varchar(100) default null , # 邮箱
    sex tinyint default 0 , # 性别（0-未知，1-男，2-女）
    avatar varchar(255) default null , # 头像url
    background varchar(255) default null , # 背景图url
    description varchar(60) default null, # 简介
    birthday datetime default null , # 生日
    create_time datetime not null , # 创建时间
    update_time datetime not null , # 修改时间
    status tinyint not null default 0 # 数据状态（0-正常，1-已删除）
) engine = InnoDB default charset = utf8mb4;
# 评论区表
create table comment (
    id bigint auto_increment primary key , # 评论id
    user_id bigint not null , # 用户id
    username varchar(20) not null , # 用户名
    comment_section_id bigint not null , # 评论区id（后端雪花生成）
    reply_id bigint default null, # 回复用户id
    content text not null , # 评论内容
    create_time datetime not null , # 创建时间
    update_time datetime not null , # 修改时间
    status tinyint not null default 0 , # 数据状态（0-正常，1-已删除）
    index idx_comment_comment_section_id (comment_section_id),
    constraint fk_comment_user
        foreign key (user_id)
            references user(id)
) engine = InnoDB default charset = utf8mb4;
# 专辑表
create table album (
    id bigint primary key , # 专辑id（雪花算法生成）
    netease_id bigint unique , # 网易云id
    album_name varchar(20) not null , # 专辑名
    singer varchar(50) not null , # 歌手名
    release_time date not null , # 发行日期
    cover_url varchar(255) not null , # 专辑封面
    description text not null , # 简介
    comment_section_id bigint unique not null , # 评论区id
    avg_score decimal(3,1) default null , # 平均评分
    score_count int default 0 , # 评分数
    collect_count int default 0 , # 收藏数
    comment_count int default 0 , # 评论数
    index idx_album_netease_id (netease_id)
) engine = InnoDB default charset = utf8mb4;
# 歌曲表
create table song (
    id bigint primary key , # 歌曲id（雪花算法生成）
    netease_id bigint unique , # 网易云id
    song_name varchar(20) not null , # 歌曲名
    album_id bigint not null , # 所属专辑 Id
    album_name varchar(20) not null , # 所属专辑名
    singer varchar(100) not null , # 歌手名
    release_time date not null , # 发行日期
    duration int not null , # 时长
    cover_url varchar(255) not null , # 封面url
    comment_section_id bigint unique not null , # 评论区id
    avg_score decimal(3,1) default null , # 平均评分
    score_count int default 0 , # 评分数
    collect_count int default 0 , # 收藏数
    comment_count int default 0 , # 评论数
    index idx_song_netease_id (netease_id),
    index idx_song_album_id (album_id)
) engine = InnoDB default charset = utf8mb4;
# 评论点赞记录表
create table comment_like (
                              id bigint auto_increment primary key ,
                              comment_id bigint not null , # 评论id
                              user_id bigint not null , # 用户id
                              `like` tinyint default 1 not null , # 是否点赞 1-点赞 0-未点赞
                              unique index (comment_id, user_id) ,
                              constraint fk_comment_like_comment
                                  foreign key (comment_id)
                                      references comment(id) ,
                              constraint fk_comment_like_user
                                  foreign key (user_id)
                                      references user(id)
) engine = InnoDB default charset = utf8mb4;
# 话题表
create table topic (
    id bigint primary key , # 话题id
    title varchar(20) not null , # 标题
    content text , # 内容
    cover_url varchar(255) , # 封面url
    comment_section_id bigint unique not null , # 评论区id
    comment_count int default 0 , # 评论数
    view_count int default 0 , # 浏览数
    like_count int default 0 , # 点赞数
    create_time datetime not null , # 创建时间
    update_time datetime not null , # 修改时间
    status tinyint not null default 0 # 数据状态（0-正常，1-已删除）
) engine = InnoDB default charset = utf8mb4;
# 评分记录表
create table score (
    id bigint auto_increment primary key , # 主键自增
    user_id bigint not null , # 用户id
    resource_id bigint not null , # 专辑/歌曲id
    score tinyint default -1 not null , # 评分
    unique index uniq_score_user_id_resource_id (user_id, resource_id), # 唯一联合索引
    index idx_score_user_id (user_id),
    index idx_score_resource_id (resource_id),
    constraint fk_score_user_id
        foreign key (user_id)
            references user(id)
) engine = InnoDB default charset = utf8mb4;
# 收藏记录表
create table collect (
    id bigint auto_increment primary key , # 主键自增
    user_id bigint not null , # 用户id
    resource_id bigint not null , # 专辑/歌曲id
    collect tinyint default 1 not null , # 是否收藏
    unique index uniq_collect_user_id_resource_id (user_id, resource_id), # 唯一联合索引
    index idx_collect_user_id (user_id),
    index idx_collect_resource_id (resource_id),
    constraint fk_collect_user_id
        foreign key (user_id)
            references user(id)
)engine = InnoDB default charset = utf8mb4;


