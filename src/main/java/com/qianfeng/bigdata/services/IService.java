package com.qianfeng.bigdata.services;

import com.qianfeng.bigdata.analysis.dimension.BaseDimension;
import com.qianfeng.bigdata.analysis.kv.value.StatsOutputValue;
import org.springframework.stereotype.Service;

@Service
public interface IService {
     void addDemission(BaseDimension baseDimension, StatsOutputValue statsOutputValue);
     void insertDemission();
}
