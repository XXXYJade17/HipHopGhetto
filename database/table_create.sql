create database hiphop_ghetto;
use hiphop_ghetto;
create table user (
    user_id bigint(20) primary key , # 用户ID（主键）
    username varchar(20) not null , # 用户名
    password varchar(100) not null , # 密码
    real_name varchar(20) default null , # 姓名
    id_card varchar(18) default null , # 身份证号
    phone varchar(20) default null , # 手机号
    email varchar(100) default null ,
    sex tinyint(1) default 0 , # 性别（0-未知，1-男，2-女）
    avatar varchar(255) default null , # 头像url
    description varchar(60) default null, # 简介
    birthday datetime default null , # 生日
    create_time datetime not null default current_timestamp , # 创建时间
    update_time datetime not null default current_timestamp on update current_timestamp , # 修改时间
    status tinyint(1) NOT NULL DEFAULT 0 # 数据状态（0-正常，1-已删除）
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
# 专辑表
create table album (
    album_id bigint(20) primary key , # 专辑 Id
    album_name varchar(20) not null , # 专辑名
    singer varchar(100) not null , # 歌手名
    release_time date not null , # 发行日期
    cover_url varchar(255) not null , # 专辑封面
    description text not null # 简介
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
# 专辑评分记录表
create table album_score (
    id bigint auto_increment primary key , # 主键自增
    user_id bigint(20) not null , # 用户 Id
    album_id bigint(20) not null , # 歌曲 Id
    score tinyint(1) not null , # 评分
    unique index (user_id, album_id), # 唯一联合索引
    constraint fk_album_score_user_id
        foreign key (user_id)
            references user(user_id),
    constraint fk_album_score_album_id
        foreign key (album_id)
            references album(album_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
# 专辑评分汇总表
create table album_score_summary (
    album_id bigint(20) primary key , # 专辑 Id
    total bigint(15) default 0 not null , # 记录数
    score decimal(3,1) default null, # 综合评分
    one bigint(10) default 0 not null , # 1分
    two bigint(10) default 0 not null , # 2分
    three bigint(10) default 0 not null , # 3分
    four bigint(10) default 0 not null , # 4分
    five bigint(10) default 0 not null , # 5分
    six bigint(10) default 0 not null , # 6分
    seven bigint(10) default 0 not null , # 7分
    eight bigint(10) default 0 not null , # 8分
    nine bigint(10) default 0 not null , # 9分
    ten bigint(10) default 0 not null , # 10分
    constraint fk_album_score_summary_album_id
        foreign key (album_id)
            references album(album_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
# 歌曲表
create table song (
    song_id bigint(20) primary key , # 歌曲 Id
    song_name varchar(50) not null , # 歌曲名
    album_id bigint(20) not null , # 所属专辑 Id
    album_name varchar(20) not null , # 所属专辑名
    singer varchar(100) not null , # 歌手名
    duration bigint(6) not null , # 时长
    cover_url varchar(255) not null # 封面url
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
# 歌曲评分记录表
create table song_score (
    id bigint auto_increment primary key , # 主键自增
    user_id bigint(20) not null , # 用户 Id
    song_id bigint(20) not null , # 歌曲 Id
    score tinyint(1) not null , # 评分
    unique index (user_id, song_id),
    constraint fk_song_score_user_id
        foreign key (user_id)
            references user(user_id),
    constraint fk_song_score_song_id
        foreign key (song_id)
            references song(song_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
# 歌曲评分汇总表
create table song_score_summary (
    song_id bigint(20) primary key , # 歌曲 Id
    total bigint(15) default 0 not null , # 记录数
    score decimal(3,1) default null, # 综合评分
    one bigint(10) default 0 not null , # 1分
    two bigint(10) default 0 not null , # 2分
    three bigint(10) default 0 not null , # 3分
    four bigint(10) default 0 not null , # 4分
    five bigint(10) default 0 not null , # 5分
    six bigint(10) default 0 not null , # 6分
    seven bigint(10) default 0 not null , # 7分
    eight bigint(10) default 0 not null , # 8分
    nine bigint(10) default 0 not null , # 9分
    ten bigint(10) default 0 not null , # 10分
    constraint fk_song_score_summary_song_id
        foreign key (song_id)
            references song(song_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;




