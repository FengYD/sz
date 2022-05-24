package org.example.szsubway.rpc.rest;

import lombok.extern.slf4j.Slf4j;
import org.example.szsubway.rpc.config.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author fengyadong
 * @date 2022/5/23 16:47
 * @Description
 */
@Slf4j
@FeignClient(name = "SZGovAPI", url = "${feign.szgov.url}", configuration = FeignConfiguration.class)
public class SZGovAPI {
}
