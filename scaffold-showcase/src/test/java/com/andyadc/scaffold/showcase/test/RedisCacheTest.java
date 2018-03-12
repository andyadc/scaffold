package com.andyadc.scaffold.showcase.test;

import com.andyadc.scaffold.showcase.cache.RedisCache;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author andaicheng
 * @version 2017/3/1
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext.xml"})
public class RedisCacheTest {

    @Autowired
    private RedisCache redisCache;

    @Ignore
    @Test
    public void testAdd() {
        redisCache.put("adc", "qwerty", 10000);
    }

    @Ignore
    @Test
    public void testGet() {
        System.out.println(redisCache.get("adc"));
    }

    @Before
    public void before() {
        System.out.println("++++++++++++++++++++++++++++++++++++++++++");
    }

    @After
    public void after() {
        System.out.println("++++++++++++++++++++++++++++++++++++++++++");
    }
}
