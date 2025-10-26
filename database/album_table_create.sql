use hiphop_ghetto;
# 专辑表
create table album (
    id bigint(20) auto_increment primary key , # 专辑 Id
    name varchar(20) not null , # 专辑名
    singer varchar(100) not null , # 歌手名
    release_time date not null , # 发行日期
    url varchar(255) not null , # 专辑封面
    introduction text default null# 简介
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
# 专辑评分汇总表
create table album_score_summary (
    id bigint(20) , # 专辑 Id
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
        foreign key (id)
            references album(id)
            on delete cascade  -- 可选：主表记录删除时，从表对应记录自动删除
            on update cascade  -- 可选：主表主键更新时，从表外键自动同步更新
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
# 专辑评分记录表
create table album_score (
    user_id bigint(20) , # 用户 Id
    album_id bigint(20) , # 歌曲 Id
    score tinyint(1) not null , # 评分
    primary key (user_id, album_id),
    constraint fk_album_score_user_id
        foreign key (user_id)
            references user(id)
            on delete cascade
            on update cascade,
    constraint fk_album_score_album_id
        foreign key (album_id)
            references album(id)
            on delete cascade
            on update cascade
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;