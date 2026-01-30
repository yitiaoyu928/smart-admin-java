drop table if exists `t_project`;

create table `t_project`
(
    `id` BIGINT not null auto_increment comment 'id',
    `name` varchar(255) not null comment '项目名称',
    `mark` varchar(255) not null comment '项目标识',
    `version` varchar(255) default 'v0.0.1' comment '项目版本',
    `type` varchar(255) not null comment '项目类型，APP,WEB,Windows',
    `sort` int(0) NOT NULL DEFAULT 0 COMMENT '排序',
    `disabled_flag` tinyint(0) NOT NULL DEFAULT 0 COMMENT '是否禁用',
    `deleted_flag` tinyint(0) NOT NULL DEFAULT 0 COMMENT '是否删除',
    `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
    `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    primary key (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 10000
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '项目管理表'
  ROW_FORMAT = Dynamic;

