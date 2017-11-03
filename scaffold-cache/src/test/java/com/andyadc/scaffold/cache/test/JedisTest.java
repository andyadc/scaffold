package com.andyadc.scaffold.cache.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * @author andy.an
 * @since 2017/11/3
 */
public class JedisTest {

    private Jedis jedis;

    @Before
    public void init() {
        jedis = new Jedis("0.0.0.0", 6379);
        jedis.auth("password");
    }

    @Test
    public void testPing() {
        Assert.assertEquals("PONG", jedis.ping());
    }
}
