package org.example.szsubway.source;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import com.alibaba.fastjson2.JSON;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.JobExecutionResult;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.apache.flink.util.Collector;
import org.example.szsubway.common.constant.Constants;
import org.example.szsubway.common.minio.MinIOTemplate;

import org.example.szsubway.model.domain.SZSubway;
import org.example.szsubway.model.dto.SZSubwayData;
import org.example.szsubway.model.dto.SZSubwayResp;
import org.example.szsubway.model.mapper.SZSubwayMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;
import java.util.List;

/**
 * @author fengyadong
 * @date 2022/5/25 16:34
 * @Description
 */
@Service
public class Data2Redis {

    @Autowired
    private MinIOTemplate minIOTemplate;
    @Resource
    private SZSubwayMapper szSubwayMapper;

    public JobExecutionResult execute() throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        File file = FileUtil.createTempFile(new File(Constants.SZ_SUBWAY_FILE));
        BufferedOutputStream out = FileUtil.getOutputStream(file);
        InputStream in = minIOTemplate.downloadStream(Constants.SZ_SUBWAY_FILE);
        IoUtil.copy(in, out, IoUtil.DEFAULT_BUFFER_SIZE);
        DataStream<SZSubwayData> source = (DataStream<SZSubwayData>) env.readTextFile(Constants.SZ_SUBWAY_FILE)
                .filter(StringUtils::isNotEmpty)
                .map(e -> JSON.parseObject(e, SZSubwayResp.class))
                .map(SZSubwayResp::getData)
                .flatMap((FlatMapFunction<List, SZSubwayData>) (list, collector) -> {
                    for(Object d : list){
                        collector.collect(JSON.parseObject(JSON.toJSONString(d), SZSubwayData.class));
                    }
                });

        source.addSink(new SinkFunction<SZSubwayData>() {
            @Override
            public void invoke(SZSubwayData value, Context context) throws Exception {
                SZSubway szSubway = JSON.parseObject(JSON.toJSONString(value), SZSubway.class);
                szSubway.setState(1);
                szSubwayMapper.insert(szSubway);
            }
        });

        return env.execute();
    }

}
