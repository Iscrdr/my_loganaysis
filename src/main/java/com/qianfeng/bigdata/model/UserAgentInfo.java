package com.qianfeng.bigdata.model;

public class UserAgentInfo {

    private String osName;//操作系统名称
    private String osVersion;//操作系统版本

    private String browserName;//浏览器名称
    private String browserVersion;//浏览器版本

    private String deviceType;//设备类型
    private String type;//类型



    public UserAgentInfo() {
    }


    public UserAgentInfo(String osName, String osVersion, String browserName, String browserVersion, String deviceType, String type) {
        this.osName = osName;
        this.osVersion = osVersion;
        this.browserName = browserName;
        this.browserVersion = browserVersion;
        this.deviceType = deviceType;
        this.type = type;
    }

    public String getBrowserName() {
        return browserName;
    }

    public void setBrowserName(String browserName) {
        this.browserName = browserName;
    }

    public String getBrowserVersion() {
        return browserVersion;
    }

    public void setBrowserVersion(String browserVersion) {
        this.browserVersion = browserVersion;
    }

    public String getOsName() {
        return osName;
    }

    public void setOsName(String osName) {
        this.osName = osName;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "UserAgentInfo{" +
                "osName='" + osName + '\'' +
                ", osVersion='" + osVersion + '\'' +
                ", browserName='" + browserName + '\'' +
                ", browserVersion='" + browserVersion + '\'' +
                ", deviceType='" + deviceType + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
