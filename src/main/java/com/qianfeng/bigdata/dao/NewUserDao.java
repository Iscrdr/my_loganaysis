package com.qianfeng.bigdata.dao;

import com.qianfeng.bigdata.analysis.dimension.*;
import com.qianfeng.bigdata.analysis.kv.key.StatsCommonDimension;
import com.qianfeng.bigdata.analysis.kv.key.StatsUserDimension;
import com.qianfeng.bigdata.analysis.kv.value.StatsOutputValue;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.*;


@Repository
public class NewUserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static List<String> kpiList = new ArrayList<>();
    public static List<String> platformList = new ArrayList<>();
    public static List<String> browserList = new ArrayList<>();
    public static List<String> dateList = new ArrayList<>();


    public static Set<String> set = new HashSet<>();//用于去重

    public void insertDemission(){

        if(kpiList.size()>0){
            String [] kpiArr = kpiList.toArray(new String[kpiList.size()]);
            jdbcTemplate.batchUpdate(kpiArr);
            kpiList.clear();
        }
        if(platformList.size()>0){
            String [] platformArr = platformList.toArray(new String[platformList.size()]);
            jdbcTemplate.batchUpdate(platformArr);
            platformList.clear();
        }
        if(browserList.size()>0){
            String [] browserArr = browserList.toArray(new String[browserList.size()]);
            jdbcTemplate.batchUpdate(browserArr);
            browserList.clear();
        }

        if(dateList.size()>0){
            String [] dateArr = dateList.toArray(new String[dateList.size()]);
            jdbcTemplate.batchUpdate(dateArr);
            dateList.clear();
        }


    }
    public void addDemission(BaseDimension baseDimension, StatsOutputValue statsOutputValue){



        if(baseDimension instanceof StatsUserDimension){
            StatsUserDimension sud = (StatsUserDimension)baseDimension;
            BrowserDimension browserDimension = sud.getBrowserDimension();
            if(null != browserDimension && StringUtils.isNotBlank(browserDimension.getBrowserName())){
                if(selectBrowserDimension(browserDimension)){
                    boolean result = set.contains(browserDimension.getBrowserName() + "_" + browserDimension.getBrowserVersion());
                    if(!result){
                        set.add(browserDimension.getBrowserName() + "_" + browserDimension.getBrowserVersion());
                        String  sql3 = " INSERT INTO `dimension_browser`(`browser_name`, `browser_version`) VALUES('"+browserDimension.getBrowserName()+"','"+browserDimension.getBrowserVersion()+"')";
                        browserList.add(sql3);
                    }

                }
            }
            StatsCommonDimension statsCommonDimension = sud.getStatsCommonDimension();

            KpiDimension kpi = statsCommonDimension.getKpi();
            if(null != kpi && StringUtils.isNotBlank(kpi.getKpiName())){
                boolean result = set.contains(kpi.getKpiName());
                if(!result){
                    set.add(kpi.getKpiName());
                    String sql1 = "insert into `dimension_kpi`(kpi_name) values('"+kpi.getKpiName()+"')";
                    kpiList.add(sql1);
                }
            }
            PlatformDimension pl = statsCommonDimension.getPl();
            if(null != pl && StringUtils.isNotBlank(pl.getPlatform())){
                boolean result = set.contains(pl.getPlatform());
                if(!result){
                    set.add(pl.getPlatform());
                    String sql2 = "insert into `dimension_platform`(platform_name) values('"+pl.getPlatform()+"')";
                    kpiList.add(sql2);
                }
            }
            DateDimension dt = statsCommonDimension.getDt();
            if(null != dt && dt.getYear() != 0){
                String params = dt.getYear()+"_"+
                        dt.getSeason()+"_"+
                        dt.getMonth()+"_"+
                        dt.getWeek()+"_"+
                        dt.getDay()+"_'"+
                        dt.getType()+ "'_'"+
                        new Date(dt.getCalendar().getTime());
                boolean result = set.contains(params);
                if(!result){
                    set.add(params);
                    String  sql4 = " INSERT INTO `dimension_date`(`year`, `season`, `month`, `week`, `day`, `type`, `calendar`) VALUES("+
                            dt.getYear()+","+
                            dt.getSeason()+","+
                            dt.getMonth()+","+
                            dt.getWeek()+","+
                            dt.getDay()+",'"+
                            dt.getType()+ "','"+
                            new Date(dt.getCalendar().getTime())+"')";
                    dateList.add(sql4);
                }

            }
        }


    }

    /**
     * 浏览器维度查询
     */
    public boolean selectBrowserDimension(BrowserDimension browserDimension){
        String sql = " SELECT `id` FROM `dimension_browser` WHERE `browser_name` = '"+browserDimension.getBrowserName()+
                "' AND `browser_version` = '"+browserDimension.getBrowserVersion()+"'";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        if(maps == null || maps.size() <= 0){
            return true;
        }
        return false;
    }
    /**
     * KPI维度查询
     */
    public boolean selectKpiDimension(KpiDimension kpiDimension){
        String sql = " select id from `dimension_kpi` where kpi_name = '"+kpiDimension.getKpiName()+"'";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        if(maps == null || maps.size() <= 0){
            return true;
        }
        return false;
    }
    /**
     * 浏览器维度查询
     */
    public boolean selectPlatformDimension(PlatformDimension platformDimension){
        String sql = " select id from `dimension_platform` where platform_name = '"+platformDimension.getPlatform()+"'";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        if(maps == null || maps.size() <= 0){
            return true;
        }
        return false;
    }
    /**
     * 浏览器维度查询
     */
    public boolean selectDateDimension(DateDimension dateDimension){
        String sql = " SELECT `id` FROM `dimension_date` WHERE `year` = "+dateDimension.getYear()+
                " AND `season` = "+dateDimension.getSeason()+
                " AND `month` = "+dateDimension.getMonth()+
                " AND `week` = "+dateDimension.getWeek()+
                " AND `day` = "+dateDimension.getDay()+
                " AND `type` = "+dateDimension.getType()+
                " AND `calendar` = "+dateDimension.getCalendar();
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        if(maps == null || maps.size() <= 0){
            return true;
        }
        return false;
    }

}
