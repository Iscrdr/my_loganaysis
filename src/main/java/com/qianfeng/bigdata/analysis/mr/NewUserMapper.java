package com.qianfeng.bigdata.analysis.mr;

import com.qianfeng.bigdata.analysis.dimension.BrowserDimension;
import com.qianfeng.bigdata.analysis.dimension.DateDimension;
import com.qianfeng.bigdata.analysis.dimension.KpiDimension;
import com.qianfeng.bigdata.analysis.dimension.PlatformDimension;
import com.qianfeng.bigdata.analysis.kv.key.StatsCommonDimension;
import com.qianfeng.bigdata.analysis.kv.key.StatsUserDimension;
import com.qianfeng.bigdata.analysis.kv.value.TimeOutputValue;
import com.qianfeng.bigdata.common.Constants;
import com.qianfeng.bigdata.common.DateEnum;
import com.qianfeng.bigdata.common.KpiType;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.log4j.Logger;

import java.io.IOException;

public class NewUserMapper extends Mapper<LongWritable, Text, StatsUserDimension, TimeOutputValue> {
    private static final Logger logger = Logger.getLogger(NewUserMapper.class);
    private StatsUserDimension k = new StatsUserDimension();
    private TimeOutputValue v = new TimeOutputValue();

    //获取用户模块下新增用户的kpi
    private KpiDimension newUserKpi = new KpiDimension(KpiType.NEW_USER.kpiName);
    private KpiDimension newBrowserUserKpi = new KpiDimension(KpiType.BROWSER_NEW_USER.kpiName);

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String lines = value.toString();
        String[] fields = lines.split(",");
        String en = fields[2];
        if(StringUtils.isNotEmpty(en)&&en.equals(Constants.EventEnum.LANUCH.alias)){
            //取出我们想要的字段
            String platform = fields[13];
            String serverTime = fields[1];
            String uuid = fields [3];
            String browserName = fields[24];
            String browserVersion = fields[25];
            if(StringUtils.isEmpty(serverTime)||StringUtils.isEmpty(uuid)){
                logger.info("serverTime或者uuid不能为空");
                return;
            }
            //构造输出的key
            long time = Long.valueOf(serverTime);
            PlatformDimension pl = PlatformDimension.getInstance(platform);
            DateDimension dateDimension = DateDimension.buildDate(time, DateEnum.DAY);
            //k.getStatsCommonDimension().
            StatsCommonDimension statsCommonDimension = k.getStatsCommonDimension();
            statsCommonDimension.setDt(dateDimension);
            statsCommonDimension.setPl(pl);
            statsCommonDimension.setKpi(newUserKpi);
            k.setStatsCommonDimension(statsCommonDimension);
            BrowserDimension browserDimension = new BrowserDimension("","");
            k.setBrowserDimension(browserDimension);
            v.setId(uuid);
            context.write(k,v);

            //浏览器模块下的赋值
            statsCommonDimension.setKpi(newBrowserUserKpi);
            browserDimension = new BrowserDimension(browserName,browserVersion);
            k.setBrowserDimension(browserDimension);
            k.setStatsCommonDimension(statsCommonDimension);
            context.write(k,v);
        }
    }
}
