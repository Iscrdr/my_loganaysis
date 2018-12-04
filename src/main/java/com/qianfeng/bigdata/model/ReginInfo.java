package com.qianfeng.bigdata.model;

import com.qianfeng.bigdata.common.GlobalConstants;

public class ReginInfo {

    private String country = GlobalConstants.DEFAULT_VALUE; //国家，如果为null，默认值：unknow
    private String province = GlobalConstants.DEFAULT_VALUE;//省，如果为null，默认值：unknow
    private String city = GlobalConstants.DEFAULT_VALUE; // 市，如果为null，默认值：unknow


    public ReginInfo(String country, String province, String city) {
        this.country = country;
        this.province = province;
        this.city = city;
    }

    public ReginInfo() {
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "ReginInfo{" +
                "country='" + country + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
