package org.example.szsubway.source;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author fengyadong
 * @Date: 2022/5/24 22:36
 */
@Slf4j
public class SZSubwaySource {

    public void saveData() {
        String fileSavePath = "./sz_subway.json";
        String appKey = "77cb81c84e804975b1d71e49197483bc";

        for (int i = 1; i <= 1337; i++) {
            log.info("请求深圳地铁数据，page = {}, rows = {}", i, 1000);

            String s = HttpUtil.get("https://opendata.sz.gov.cn/api/29200_00403601/1/service.xhtml?page=" + i + "&rows=1000&appKey=" + appKey);
            FileUtil.appendUtf8String(s + "\n", fileSavePath);
        }

        int size = FileUtil.readUtf8Lines(fileSavePath).size();

        if (size == 1337) {
            log.info(" 数据完全保存");
        } else {
            log.info(" 数据保存量size = {}", size);
        }
    }
}
