use hiphop_ghetto;
# 歌曲表
create table song (
    id bigint(20) auto_increment primary key , # 歌曲 Id
    name varchar(50) not null , # 歌曲名
    album_id bigint(20) not null , # 所属专辑 Id
    album_name varchar(20) not null , # 所属专辑名
    singer varchar(100) not null , # 歌手名
    duration bigint(6) not null , # 时长
    url varchar(255) not null # 封面url
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
# 歌曲评分汇总表
create table song_score_summary (
    id bigint(20) , # 歌曲 Id
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
        foreign key (id)
        references song(id)
        on delete cascade  -- 可选：主表记录删除时，从表对应记录自动删除
        on update cascade  -- 可选：主表主键更新时，从表外键自动同步更新
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
# 歌曲评分记录表
create table song_score (
    user_id bigint(20) , # 用户 Id
    song_id bigint(20) , # 歌曲 Id
    score tinyint(1) not null , # 评分
    primary key (user_id, song_id),
    constraint fk_song_score_user_id
        foreign key (user_id)
        references user(id)
        on delete cascade
        on update cascade,
    constraint fk_song_score_song_id
        foreign key (song_id)
        references song(id)
        on delete cascade
        on update cascade
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;