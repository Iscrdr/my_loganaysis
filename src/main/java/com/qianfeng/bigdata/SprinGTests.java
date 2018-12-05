package com.qianfeng.bigdata;

import com.qianfeng.bigdata.services.UserSerice;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SprinGTests {
    Logger logger= LoggerFactory.getLogger(SprinGTests.class);
    @Autowired
    private UserSerice userSerice;

    @Test
    public void testAll(){
    }
}
