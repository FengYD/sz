package org.example.szsubway.model.domain;


import lombok.Data;

import java.util.Date;

/**
 * @author fengyadong
 * @date 2022/5/25 17:24
 * @Description
 */
@Data
public class SZSubway extends BaseDomain {

    // 卡号
    private String cardNo;

    // 交易日期时间
    private Date dealDate;

    // 交易类型
    private String dealType;

    // 交易金额
    private String dealMoney;

    // 交易值
    private String dealValue;

    // 设备编码
    private String equNo;

    // 公司名称
    private String companyName;

    // 线路站点
    private String station;

    // 车牌号
    private String carNo;

    // 联程标记
    private String connMark;

    // 结算日期
    private Date closeDate;

    private Integer state;
}