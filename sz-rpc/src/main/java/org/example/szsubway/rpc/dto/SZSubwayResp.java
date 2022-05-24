package org.example.szsubway.rpc.dto;

import lombok.Data;

import java.util.List;

/**
 * @author fengyadong
 * @date 2022/5/23 17:14
 * @Description
 */
@Data
public class SZSubwayResp {

    private Long total;

    private Long page;

    private Long rows;

    private List<SZSubwayData> data;

}
