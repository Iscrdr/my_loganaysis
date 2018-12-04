package com.qianfeng.bigdata.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class UserSerice {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void getConn(){
        List<Map<String, Object>> lists = jdbcTemplate.queryForList(" select  count(*) from stats_user ");
        System.out.println(lists.size());

    }
}
