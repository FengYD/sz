package org.example.szsubway.rpc.rest;


import org.example.szsubway.rpc.config.FeignConfiguration;
import org.example.szsubway.rpc.dto.SZSubwayData;
import org.example.szsubway.rpc.dto.SZSubwayResp;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author fengyadong
 * @date 2022/5/23 16:47
 * @Description
 */
@FeignClient(name = "SZGovAPI", url = "${feign.domain.sz}", configuration = FeignConfiguration.class)
public interface SZGovAPI {

    @RequestMapping(value = "/geocoding/v3",
            method = RequestMethod.GET,
            produces = "text/javascript;charset=utf-8",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    SZSubwayResp<SZSubwayData> getSZSubWayData();

}
