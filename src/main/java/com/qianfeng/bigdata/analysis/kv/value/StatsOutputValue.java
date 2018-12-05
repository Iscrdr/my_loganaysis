package com.qianfeng.bigdata.analysis.kv.value;
import com.qianfeng.bigdata.common.KpiType;
import org.apache.hadoop.io.Writable;

/**
 * @Description :封装map或者reduce输出的value的顶级父类
 * @Author cqh <caoqingghai@1000phone.com>
 * @Version V1.0
 * @Since 1.0
 * @Date 2018/12/1 10：53
 */
public abstract class StatsOutputValue implements Writable {
    //获取kpi的抽象方法，决定了reduce输出的结果写入到哪儿张表中的哪儿个字段
    //以便于自定义输出到mysql的格式化类区别数据要输出到哪儿张表的哪儿个字段
    public abstract KpiType getKpi();
}













