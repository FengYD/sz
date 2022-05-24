package org.example.szsubway.rpc.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

/**
 * @author fengyadong
 * @date 2022/5/23 17:20
 * @Description
 */
@Data
public class SZSubwayData {

    // 卡号
    @JsonAlias("card_no")
    private String cardNo;

    // 交易日期时间
    @JsonAlias("deal_date")
    private String dealDate;

    // 交易类型
    @JsonAlias("deal_type")
    private String dealType;

    // 交易金额
    @JsonAlias("deal_money")
    private String dealMoney;

    // 交易值
    @JsonAlias("deal_value")
    private String dealValue;

    // 设备编码
    @JsonAlias("equ_no")
    private String equNo;

    // 公司名称
    @JsonAlias("company_name")
    private String companyName;

    // 线路站点
    private String station;

    // 车牌号
    @JsonAlias("car_no")
    private String carNo;

    // 联程标记
    @JsonAlias("conn_mark")
    private String connMark;

    // 结算日期
    @JsonAlias("close_date")
    private String closeDate;
}
