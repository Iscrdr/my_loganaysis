package com.qianfeng.bigdata.services;

import com.qianfeng.bigdata.analysis.dimension.BaseDimension;
import com.qianfeng.bigdata.analysis.kv.value.StatsOutputValue;
import com.qianfeng.bigdata.dao.NewUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewUserService implements IService {

    @Autowired
    private NewUserDao newUserDao;

    public void addDemission(BaseDimension baseDimension, StatsOutputValue statsOutputValue) {
        newUserDao.addDemission(baseDimension,statsOutputValue);
    }

    @Override
    public void insertDemission() {
        newUserDao.insertDemission();
    }
}
