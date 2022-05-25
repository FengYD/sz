package org.example.szsubway.source;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.example.szsubway.common.constant.Constants;
import org.example.szsubway.common.minio.MinIOTemplate;
import org.example.szsubway.common.redis.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author fengyadong
 * @Date: 2022/5/24 22:36
 */
@Slf4j
public class SZSubwaySource {

    @Autowired
    private MinIOTemplate minIOTemplate;
    @Autowired
    private RedisUtils redisUtils;


    public void saveData() {
        String appKey = "77cb81c84e804975b1d71e49197483bc";

        for (int i = 1; i <= 1337; i++) {
            log.info("请求深圳地铁数据，page = {}, rows = {}", i, 1000);
            String s = HttpUtil.get("https://opendata.sz.gov.cn/api/29200_00403601/1/service.xhtml?page=" + i + "&rows=1000&appKey=" + appKey);
            FileUtil.appendUtf8String(s + "\n", Constants.SZ_SUBWAY_FILE);
        }

        int size = FileUtil.readUtf8Lines(Constants.SZ_SUBWAY_FILE).size();

        if (size == 1337) {
            log.info(" 数据完全保存");
            String filePath = minIOTemplate.upload(Constants.SZ_SUBWAY_FILE, FileUtil.getInputStream(Constants.SZ_SUBWAY_FILE));
            redisUtils.set(Constants.SZ_SUBWAY_FILE, filePath);
        } else {
            log.info(" 数据保存量size = {}", size);
        }
    }
}
