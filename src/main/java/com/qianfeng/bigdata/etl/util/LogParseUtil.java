package com.qianfeng.bigdata.etl.util;

import com.qianfeng.bigdata.common.Constants;
import com.qianfeng.bigdata.etl.mr.LogWritable;
import com.qianfeng.bigdata.model.ReginInfo;
import com.qianfeng.bigdata.model.UserAgentInfo;

import java.util.Map;
import java.util.Set;

public class LogParseUtil {

    /**
     * 日志解析
     * @param map
     * @return
     */
    public static LogWritable getLogByMap(Map<String,String> map){
        Set<Map.Entry<String, String>> entries = map.entrySet();
        LogWritable log = new LogWritable();
        for (Map.Entry<String,String> en : entries) {
           String key =  en.getKey();
           String value = en.getValue();
            switch (key){
                case "ver" : log.setVer(value);break;
                case "s_time" : log.setS_time(value);break;
                case "en" : log.setEn(value);break;
                case "u_ud" : log.setU_ud(value);break;
                case "u_mid" : log.setU_mid(value);break;
                case "u_sd" : log.setU_sd(value);break;
                case "c_time" : log.setC_time(value);break;
                case "l" : log.setL(value);break;
                case "b_iev" : log.setB_iev(value);break;
                case "b_rst" : log.setB_rst(value);break;
                case "p_url" : log.setP_url(value);break;
                case "p_ref" : log.setP_ref(value);break;
                case "tt" : log.setTt(value);break;
                case "pl" : log.setPl(value);break;
                case "ip" : log.setIp(value);break;
                case "oid" : log.setOid(value);break;
                case "on" : log.setOn(value);break;
                case "cua" : log.setCua(value);break;
                case "cut" : log.setCut(value);break;
                case "pt" : log.setPt(value);break;
                case "ca" : log.setCa(value);break;
                case "ac" : log.setAc(value);break;
                case "kv_" : log.setKv_(value);break;
                case "du" : log.setDu(value);break;
                case "browserName" : log.setBrowserName(value);break;
                case "browserVersion" : log.setBrowserVersion(value);break;
                case "osName" : log.setOsName(value);break;
                case "osVersion" : log.setOsVersion(value);break;
                case "country" : log.setCountry(value);break;
                case "province" : log.setProvince(value);break;
                case "city" : log.setCity(value);break;
            }

        }
        return log;

    }
     /**
     * 处理IP
     * @param map
     */
     public static void handleIp(Map<String, String> map) {
        if(map.containsKey(Constants.LOG_IP)){
            ReginInfo info = IpParseUtil.getRegionByIp(map.get(Constants.LOG_IP));
            map.put(Constants.LOG_COUNTRY,info.getCountry());
            map.put(Constants.LOG_PROVINCE,info.getProvince());
            map.put(Constants.LOG_CITY,info.getCity());
        }
    }
    /**
     * 处理浏览器信息
     * @param map
     */
    public static void handleAgent(Map<String, String> map) {
        if(map.containsKey(Constants.LOG_USERAGENT)){
            UserAgentInfo info = UserAgnetParserUtil.parserUserAgent(map.get(Constants.LOG_USERAGENT));
            map.put(Constants.LOG_BROWSER_NAME,info.getBrowserName());
            map.put(Constants.LOG_BROWSER_VERSION,info.getBrowserVersion());
            map.put(Constants.LOG_OS_NAME,info.getOsName());
            map.put(Constants.LOG_OS_VERSION,info.getOsVersion());
        }
    }

    /**
     * 处理浏览器信息
     * @param url
     */
    public static void handleParmas(String url,Map<String, String> map) {

        ParamsParseUtil.getParams(url,map);
    }


}
