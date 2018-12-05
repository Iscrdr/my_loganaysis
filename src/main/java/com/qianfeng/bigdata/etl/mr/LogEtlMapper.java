package com.qianfeng.bigdata.etl.mr;


import com.qianfeng.bigdata.common.Constants;
import com.qianfeng.bigdata.etl.util.LogParseUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LogEtlMapper extends Mapper<LongWritable, Text, LogWritable, NullWritable> {

    private static Logger logger = Logger.getLogger(LogEtlMapper.class);
    public static int recordNULL = 0;
    public static int record = 0;
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        record ++;

        if(StringUtils.isBlank(line)){
            recordNULL++;
            logger.debug("本行没有数据: "+key.get()+", 共有 "+recordNULL+" 行无数据 ");
            return;
        }
        Map<String,String> map = new ConcurrentHashMap<>();
        String [] lines = line.split("\\^A");
        if(lines.length==4){

            map.put(Constants.LOG_IP,lines[0]);
            map.put(Constants.LOG_SERVER_TIME,lines[1].replaceAll("\\.",""));
            //参数列表，单独定义方法去处理
            String params = lines[3];
            //处理参数列表的方法
            LogParseUtil.handleParmas(params,map);
            //处理ip
            LogParseUtil.handleIp(map);

            if (record ==2){
                System.out.println("params:"+params);
                System.out.println("map:"+map);
            }
            //处理浏览器信息
            LogParseUtil.handleAgent(map);


            LogWritable logByMap = LogParseUtil.getLogByMap(map);
            System.out.println("============================");
            System.out.println(logByMap);
            context.write(logByMap,NullWritable.get());
        }

    }
}
