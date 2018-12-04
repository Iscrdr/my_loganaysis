package com.qianfeng.bigdata.common;

/**
 * @Description
 * @Author cqh <caoqingghai@1000phone.com>
 * @Version V1.0
 * @Since 1.0
 * @Date 2018/12/1 11：01
 */
public enum KpiType {
    NEW_USER("new_user"),
    BROWSER_NEW_USER("browser_new_user"),
    ACTIVE_USER("active_user")
    ;
    public String kpiName;
    KpiType(String kpiName){
        this.kpiName = kpiName;
    }

    /**
     * 根据kpi的name获取对应的指标
     * @param name
     * @return
     */
    public static KpiType valueOfKpiName(String name){
        for (KpiType kpi : values()){
            if(kpi.kpiName.equals(name)){
                return kpi;
            }
        }
        return null;
    }

}