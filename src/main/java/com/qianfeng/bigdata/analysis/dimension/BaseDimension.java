package com.qianfeng.bigdata.analysis.dimension;

import org.apache.hadoop.io.WritableComparable;

/**
 * @Description :所有基础维度类的顶级父类
 * 规定所有实现这个类的子类序列化、比较
 * @Author cqh <caoqingghai@1000phone.com>
 * @Version V1.0
 * @Since 1.0
 * @Date 2018/11/30 11：25
 */

public abstract class BaseDimension implements WritableComparable<BaseDimension> {

}