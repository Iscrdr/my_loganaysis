package com.qianfeng.bigdata.etl.util;

import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

public class ParamsParseUtil {
    private static Logger logger = Logger.getLogger(ParamsParseUtil.class);

    /**
     * 参数解析
     * @param url
     * @return
     */
    public static void getParams(String url,Map<String,String> map){

        if (url == null) {
            return ;
        }
        url = url.trim();
        if (url.equals("")) {
            return ;
        }
        String[] urlParts = url.split("\\?");
        String baseUrl = urlParts[0];
        //没有参数
        if (urlParts.length == 1) {
            return ;
        }
        //有参数
        String[] params = urlParts[1].split("&");

        for (String param : params) {
            String[] keyValue = param.split("=");
            String key = keyValue[0];
            String value = null;
            try {
                value = URLDecoder.decode(keyValue[1],"utf-8");
            } catch (UnsupportedEncodingException e) {
                logger.error("url解码异常",e);
            }
            map.put(key, value);
        }
        return ;

    }
}
