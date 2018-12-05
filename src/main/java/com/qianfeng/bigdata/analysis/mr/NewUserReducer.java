package com.qianfeng.bigdata.analysis.mr;

import com.qianfeng.bigdata.analysis.kv.key.StatsUserDimension;
import com.qianfeng.bigdata.analysis.kv.value.OutputMapWritable;
import com.qianfeng.bigdata.analysis.kv.value.TimeOutputValue;
import com.qianfeng.bigdata.common.KpiType;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.MapWritable;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class NewUserReducer extends Reducer<StatsUserDimension, TimeOutputValue,StatsUserDimension, OutputMapWritable> {

    private static final Logger logger = Logger.getLogger(NewUserReducer.class);
    private OutputMapWritable v = new OutputMapWritable();
    //用于UUID去重的集合
    private Set set = new HashSet<>();
    //{1:8000}
    private MapWritable map = new MapWritable();
    @Override
    protected void reduce(StatsUserDimension key, Iterable<TimeOutputValue> values, Context context) throws IOException, InterruptedException {
        for (TimeOutputValue tv:values) {
            this.set.add(tv.getId());
        }
        //设置KPI
        this.v.setKpi(KpiType.valueOfKpiName(key.getStatsCommonDimension().getKpi().getKpiName()));
        //通过集合的size设置新增用户的uuid个数
        this.map.put(new IntWritable(1),new IntWritable(set.size()));
        v.setValue(map);
        context.write(key,v);
        //清空集合
        this.set.clear();
    }
}
