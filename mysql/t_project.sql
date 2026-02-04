drop table if exists `t_project`;

create table `t_project`
(
    `id`            BIGINT                                                        not null auto_increment comment 'id',
    `name`          varchar(255)                                                  not null comment '项目名称',
    `mark`          varchar(255)                                                  not null comment '项目标识',
    `version`       varchar(255)                                                           default 'v0.0.1' comment '项目版本',
    `type`          varchar(255)                                                  not null comment '项目类型，APP,WEB,Windows',
    `sort`          int                                                           NOT NULL DEFAULT 0 COMMENT '排序',
    `disabled_flag` tinyint                                                       NOT NULL DEFAULT 0 COMMENT '是否禁用',
    `deleted_flag`  tinyint                                                       NOT NULL DEFAULT 0 COMMENT '是否删除',
    `remark`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL,
    `update_time`   datetime(0)                                                   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
    `create_time`   datetime(0)                                                   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    primary key (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 10000
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '项目管理表'
  ROW_FORMAT = Dynamic;

drop table if exists `t_key`;

create table `t_key`
(
    `id`            BIGINT                                                        not null auto_increment comment 'id',
    `project_id`    BIGINT                                                        not null comment '项目ID',
    `key`           varchar(255)                                                  not null comment '密钥',
    `cycle_type`    varchar(255)                                                  not null comment '密钥周期类型，DAY=天卡，Month=月卡，quarter=季卡，年卡=Year',
    `disabled_flag` tinyint                                                       NOT NULL DEFAULT 0 COMMENT '是否禁用',
    `deleted_flag`  tinyint                                                       NOT NULL DEFAULT 0 COMMENT '是否删除',
    `using_flag`    tinyint                                                       NOT NULL DEFAULT 0 COMMENT '是否使用',
    `using_time`    datetime(0) comment '使用时间',
    `expire_time`   timestamp(0) comment '过期时间',
    `remark`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL,
    `update_time`   datetime(0)                                                   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
    `create_time`   datetime(0)                                                   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    primary key (`id`),
    unique key `uk_key` (`key`),
    foreign key (`project_id`) REFERENCES `t_project` (`id`)
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '项目密钥'
  ROW_FORMAT = Dynamic;

