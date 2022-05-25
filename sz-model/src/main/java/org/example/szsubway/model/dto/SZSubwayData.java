package org.example.szsubway.model.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @author fengyadong
 * @date 2022/5/23 17:20
 * @Description
 */
@Data
public class SZSubwayData {

    // 卡号
    @JSONField(name = "card_no")
    private String cardNo;

    // 交易日期时间
    @JSONField(name ="deal_date")
    private String dealDate;

    // 交易类型
    @JSONField(name ="deal_type")
    private String dealType;

    // 交易金额
    @JSONField(name ="deal_money")
    private String dealMoney;

    // 交易值
    @JSONField(name ="deal_value")
    private String dealValue;

    // 设备编码
    @JSONField(name ="equ_no")
    private String equNo;

    // 公司名称
    @JSONField(name ="company_name")
    private String companyName;

    // 线路站点
    private String station;

    // 车牌号
    @JSONField(name ="car_no")
    private String carNo;

    // 联程标记
    @JSONField(name ="conn_mark")
    private String connMark;

    // 结算日期
    @JSONField(name ="close_date")
    private String closeDate;
}
