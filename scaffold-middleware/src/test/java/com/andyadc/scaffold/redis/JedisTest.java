package com.andyadc.scaffold.redis;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author andy.an
 * @since 2017/11/9
 */
public class JedisTest {

    private Jedis jedis;

    @Before
    public void init() {
        jedis = new Jedis(RedisConst.HOST, RedisConst.PORT);
        jedis.auth(RedisConst.AUTH);
    }

    @After
    public void close() {
        if (jedis != null) {
            jedis.close();
        }
    }

    @Test
    public void testKeys() {
        Set<String> keys = jedis.keys("*");
        for (String key : keys) {
            System.out.println("key: " + key);
        }
    }

    /**
     * list
     */
    @Test
    public void testList() {
        jedis.lpush("collections", "arraylist", "set", "stack");
        jedis.lpush("collections", "hashmap");

    }

    /**
     * set
     */
    @Test
    public void testSet() {
        jedis.sadd("sets", "a", "a", "b");
    }

    @Test
    public void testHash() {
        Map<String, String> map = new HashMap<>();
        map.put("a", "123");
        map.put("b", "12356");
        map.put("c", "12378");

        jedis.hmset("hash", map);
        jedis.hset("hash", "c", "000");
    }

    @Test
    public void testSortedSet() {

    }

    @Test
    public void testPing() {
        Assert.assertEquals("PONG", jedis.ping());
    }
}
