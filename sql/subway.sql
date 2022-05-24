CREATE TABLE sz_subway
(
    `id`           bigint(20)  NOT NULL COMMENT '主键',
    `card_no`      varchar(10) NULL COMMENT '卡号',
    `deal_date`    datetime    NULL COMMENT '交易日期时间',
    `deal_type`    varchar(10) NULL COMMENT '交易类型',
    `deal_money`   int         NULL COMMENT '交易金额',
    `deal_value`   int         NULL COMMENT '交易值',
    `equ_no`       varchar(10) NULL COMMENT '设备编码',
    `company_name` varchar(10) NULL COMMENT '公司名称',
    `station`      varchar(10) NULL COMMENT '线路站点',
    `carNo`        varchar(10) NULL COMMENT '车牌号',
    `connMark`     varchar(10) NULL COMMENT '联程标记',
    `closeDate`    datetime    NULL COMMENT '结算日期',
    `state`        int         NULL COMMENT '数据状态',
    `is_delete`    int(1)      NOT NULL DEFAULT 0 COMMENT '是否逻辑删除 1是0否',
    `create_time`  datetime COMMENT '记录创建时间',
    `update_time`  datetime COMMENT '记录更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB COMMENT ='商品上报渠道表';