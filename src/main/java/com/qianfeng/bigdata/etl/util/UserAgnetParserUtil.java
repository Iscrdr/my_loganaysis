package com.qianfeng.bigdata.etl.util;

import com.qianfeng.bigdata.model.UserAgentInfo;
import cz.mallat.uasparser.OnlineUpdater;
import cz.mallat.uasparser.UASparser;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.IOException;

public class UserAgnetParserUtil {

    private static Logger logger = Logger.getLogger(UserAgnetParserUtil.class);

    private static UASparser uaSparser =null;
    static {
        try {
            uaSparser = new UASparser(OnlineUpdater.getVendoredInputStream());
        } catch (IOException e) {
            logger.error("获取uaSparser异常",e);
        }
    }
    /**
     * 解析userAgent的方法
     * @param userAgent
     * @return
     */
    public static UserAgentInfo parserUserAgent(String userAgent){
        UserAgentInfo agentInfo = null;
        try {
            if(StringUtils.isNotEmpty(userAgent)){
                cz.mallat.uasparser.UserAgentInfo parse = uaSparser.parse(userAgent);

                if(parse!=null){
                    agentInfo = new UserAgentInfo();
                    agentInfo.setBrowserName(parse.getUaFamily());
                    agentInfo.setBrowserVersion(parse.getBrowserVersionInfo());
                    agentInfo.setOsName(parse.getOsName());
                    agentInfo.setOsVersion(parse.getOsFamily());
                    agentInfo.setType(parse.getType());
                    agentInfo.setDeviceType(parse.getDeviceType());
                }
            }
        } catch (IOException e) {
            logger.error("解析useragent异常",e);
        }
        return agentInfo;
    }
}
