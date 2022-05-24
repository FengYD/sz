package org.example.szsubway.rpc.dto;

import lombok.Data;

import java.util.List;

/**
 * @author fengyadong
 * @date 2022/5/23 17:14
 * @Description
 */
@Data
public class SZSubwayResp<T> {

    private Long total;

    private Long page;

    private Long rows;

    private List<T> data;

}
