package org.example.szsubway.rpc.rest;


import org.example.szsubway.rpc.config.FeignConfiguration;
import org.example.szsubway.rpc.dto.SZSubwayData;
import org.example.szsubway.rpc.dto.SZSubwayResp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author fengyadong
 * @date 2022/5/23 16:47
 * @Description
 */
@FeignClient(name = "SZGovAPI", url = "${feign.domain.sz}", configuration = FeignConfiguration.class)
public interface SZGovAPI {

    @RequestMapping(value = "/api/29200_00403601/1/service.xhtml",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    SZSubwayResp<SZSubwayData> getSZSubWayData(
            @RequestParam("page") Long page,
            @RequestParam("rows") Long rows,
            @RequestParam("appKey") String appKey
    );

}
