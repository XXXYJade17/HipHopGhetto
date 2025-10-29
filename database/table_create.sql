drop database hiphop_ghetto;
create database hiphop_ghetto;
use hiphop_ghetto;
create table user (
    user_id bigint primary key , # 用户ID（主键）
    username varchar(20) not null , # 用户名
    password varchar(100) not null , # 密码
    real_name varchar(20) default null , # 姓名
    id_card varchar(18) default null , # 身份证号
    phone varchar(20) default null , # 手机号
    email varchar(100) default null ,
    sex tinyint default 0 , # 性别（0-未知，1-男，2-女）
    avatar varchar(255) default null , # 头像url
    description varchar(60) default null, # 简介
    birthday datetime default null , # 生日
    create_time datetime not null default current_timestamp , # 创建时间
    update_time datetime not null default current_timestamp on update current_timestamp , # 修改时间
    status tinyint NOT NULL DEFAULT 0 # 数据状态（0-正常，1-已删除）
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
# 专辑表
create table album (
    album_id bigint primary key , # 专辑 Id
    album_name varchar(20) not null , # 专辑名
    singer varchar(100) not null , # 歌手名
    release_time date not null , # 发行日期
    cover_url varchar(255) not null , # 专辑封面
    description text not null # 简介
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
# 专辑评分记录表
create table album_score (
    id bigint auto_increment primary key , # 主键自增
    user_id bigint not null , # 用户 Id
    album_id bigint not null , # 歌曲 Id
    score varchar(5) not null , # 评分
    unique index (user_id, album_id), # 唯一联合索引
    constraint fk_album_score_user_id
        foreign key (user_id)
            references user(user_id),
    constraint fk_album_score_album_id
        foreign key (album_id)
            references album(album_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
# 专辑收藏记录表
create table album_collect (
    id bigint auto_increment primary key , # 主键自增
    user_id bigint not null , # 用户 Id
    album_id bigint not null , # 专辑 Id
    collect tinyint not null , # 是否收藏
    unique index (user_id, album_id), # 唯一联合索引
    constraint fk_album_collect_user_id
        foreign key (user_id)
            references user(user_id),
    constraint fk_album_collect_album_id
        foreign key (album_id)
            references album(album_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
# 专辑评论记录表
create table album_comment (
    id bigint auto_increment primary key , # 主键自增
    user_id bigint not null , # 用户 Id
    album_id bigint not null , # 专辑 Id
    comment text not null , # 评论
    create_time datetime not null default current_timestamp , # 创建时间
    update_time datetime not null default current_timestamp on update current_timestamp , # 修改时间
    status tinyint NOT NULL DEFAULT 0 , # 数据状态（0-正常，1-已删除）
    constraint fk_album_comment_user_id
        foreign key (user_id)
            references user(user_id),
    constraint fk_album_comment_album_id
        foreign key (album_id)
            references album(album_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
# 专辑汇总表
create table album_stats (
    album_id bigint primary key , # 专辑 Id
    avg_score decimal(3,1) default null , # 平均评分
    score_count bigint default 0 not null , # 评分数
    collect_count bigint default 0 not null , # 收藏数
    comment_count bigint default 0 not null , # 评论数
    one bigint default 0 not null , # 1分
    two bigint default 0 not null , # 2分
    three bigint default 0 not null , # 3分
    four bigint default 0 not null , # 4分
    five bigint default 0 not null , # 5分
    six bigint default 0 not null , # 6分
    seven bigint default 0 not null , # 7分
    eight bigint default 0 not null , # 8分
    nine bigint default 0 not null , # 9分
    ten bigint default 0 not null , # 10分
    constraint fk_album_stats_album_id
        foreign key (album_id)
            references album(album_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
# 歌曲表
create table song (
    song_id bigint primary key , # 歌曲 Id
    song_name varchar(50) not null , # 歌曲名
    album_id bigint not null , # 所属专辑 Id
    album_name varchar(20) not null , # 所属专辑名
    singer varchar(100) not null , # 歌手名
    duration bigint not null , # 时长
    cover_url varchar(255) not null # 封面url
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
# 歌曲评分记录表
create table song_score (
    id bigint auto_increment primary key , # 主键自增
    user_id bigint not null , # 用户 Id
    song_id bigint not null , # 歌曲 Id
    score varchar(5) not null , # 评分
    unique index (user_id, song_id),
    constraint fk_song_score_user_id
        foreign key (user_id)
            references user(user_id),
    constraint fk_song_score_song_id
        foreign key (song_id)
            references song(song_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
# 歌曲收藏记录表
create table song_collect (
    id bigint auto_increment primary key , # 主键自增
    user_id bigint not null , # 用户 Id
    song_id bigint not null , # 歌曲 Id
    collect tinyint not null , # 是否收藏
    unique index (user_id, song_id), # 唯一联合索引
    constraint fk_song_collect_user_id
        foreign key (user_id)
            references user(user_id),
    constraint fk_song_collect_song_id
        foreign key (song_id)
            references song(song_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
# 歌曲评论记录表
create table song_comment (
    id bigint auto_increment primary key , # 主键自增
    user_id bigint not null , # 用户 Id
    song_id bigint not null , # 专辑 Id
    comment text not null , # 是否收藏
    create_time datetime not null default current_timestamp , # 创建时间
    update_time datetime not null default current_timestamp on update current_timestamp , # 修改时间
    status tinyint NOT NULL DEFAULT 0 , # 数据状态（0-正常，1-已删除）
    constraint fk_song_comment_user_id
        foreign key (user_id)
            references user(user_id),
    constraint fk_song_comment_song_id
        foreign key (song_id)
            references song(song_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
# 歌曲汇总表
create table song_stats (
    song_id bigint primary key , # 歌曲 Id
    avg_score decimal(3,1) default null, # 平均评分
    score_count bigint default 0 not null , # 评分数
    collect_count bigint default 0 not null , # 收藏数
    comment_count bigint default 0 not null , # 评论数
    one bigint default 0 not null , # 1分
    two bigint default 0 not null , # 2分
    three bigint default 0 not null , # 3分
    four bigint default 0 not null , # 4分
    five bigint default 0 not null , # 5分
    six bigint default 0 not null , # 6分
    seven bigint default 0 not null , # 7分
    eight bigint default 0 not null , # 8分
    nine bigint default 0 not null , # 9分
    ten bigint default 0 not null , # 10分
    constraint fk_song_stats_song_id
        foreign key (song_id)
            references song(song_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;




