create database hiphop_ghetto;
use hiphop_ghetto;
# 用户表
CREATE TABLE user (
                      `id` bigint(20) NOT NULL COMMENT '用户ID（雪花算法）',
                      `username` varchar(20) DEFAULT NULL COMMENT '用户名',
                      `name` varchar(10) DEFAULT NULL COMMENT '姓名',
                      `password` varchar(64) NOT NULL COMMENT '密码',
                      `phone` varchar(20) DEFAULT NULL COMMENT '手机号',
                      `sex` tinyint(1) DEFAULT 0 COMMENT '性别（0-未知，1-男，2-女）',
                      `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
                      `create_time` datetime NOT NULL COMMENT '创建时间',
                      `update_time` datetime NOT NULL COMMENT '修改时间',
                      `status` tinyint(1) NOT NULL DEFAULT 0 COMMENT '数据状态（0-正常，1-已删除）',
                      PRIMARY KEY (`id`),
                      index `idx_username` (`username`),
                      index `idx_phone` (`phone`),
                      index `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';
# 评分表
create table score (
                       id bigint(20) comment '评分Id',
                       user_id bigint(20) not null comment '发起用户Id',
                       title varchar(20) not null comment '标题',
                       description text comment '描述',
                       start_time datetime comment '创建时间',
                       status tinyint(1) NOT NULL DEFAULT 0 COMMENT '数据状态（0-正常，1-已删除）',
                       primary key (`id`),
                       index idx_user_id (user_id)
) engine =InnoDB default charset =utf8mb4 comment '评分活动表';
# 评分图片
create table score_image (
                             id bigint auto_increment primary key comment '图片Id',
                             score_id bigint(20) comment '评分Id',
                             url varchar(255) DEFAULT NULL COMMENT '图片URL',
                             constraint fk_activity_image foreign key (score_id)
                                 references score(id)
                                 on delete cascade,
                             index idx_activity_id (score_id)
) engine =InnoDB default charset =utf8mb4 comment '评分图片表';
# 评分详情
create table score_detail (
                              score_id bigint(20) comment '活动Id',
                              one bigint default 0 comment '1分',
                              two bigint default 0 comment '2分',
                              three bigint default 0 comment '3分',
                              four bigint default 0 comment '4分',
                              five bigint default 0 comment '5分',
                              six bigint default 0 comment '6分',
                              seven bigint default 0 comment '7分',
                              eight bigint default 0 comment '8分',
                              nine bigint default 0 comment '9分',
                              ten bigint default 0 comment '10分',
                              primary key (score_id),
                              constraint fk_activity_id foreign key (score_id)
                                  references score(id)
                                  on delete cascade
) engine =InnoDB default charset =utf8mb4 comment '评分详情表';
