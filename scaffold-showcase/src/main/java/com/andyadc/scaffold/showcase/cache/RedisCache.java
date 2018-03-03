package com.andyadc.scaffold.showcase.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author andaicheng
 * @version 2016/12/28
 */
@Component
public class RedisCache {

    private static final Logger LOG = LoggerFactory.getLogger(RedisCache.class);

    private RedisTemplate<String, Object> redisTemplate;

    public RedisCache(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Object get(Object key) {
        final byte[] keyb = key.toString().getBytes();
        Object object;

        object = redisTemplate.execute((RedisConnection connection) -> {
            byte[] value = connection.get(keyb);
            if (value == null) {
                return null;
            }
            return toObject(value);
        });
        return object;
    }

    public void put(Object key, Object value, final long liveTime) {
        final String keyf = key.toString();
        final Object valuef = value;

        redisTemplate.execute((RedisConnection connection) -> {
            byte[] keyb = keyf.getBytes();
            byte[] valueb = toByteArray(valuef);
            connection.set(keyb, valueb);
            if (liveTime > 0) {
                connection.expire(keyb, liveTime);
            }
            return 1L;
        });
    }

    public void del(Object key) {
        final String keyf = key.toString();

        redisTemplate.execute((RedisConnection connection) ->
                connection.del(keyf.getBytes())
        );
    }

    public void expire(Object key, final long liveTime) {
        final String keyf = key.toString();

        redisTemplate.execute((RedisConnection connection) ->
                connection.expire(keyf.getBytes(), liveTime)
        );
    }

    public Boolean exist(Object key) {
        final String keyf = key.toString();

        return redisTemplate.execute((RedisConnection connection) ->
                connection.exists(keyf.getBytes())
        );
    }

    public void clear() {
        redisTemplate.execute((RedisConnection connection) -> {
            connection.flushDb();
            return "ok";
        });
    }

    /**
     * 对key的值做加加操作,并返回新的值。 incr一个不存在的key，则设置key为1
     *
     * @param key key
     * @return 返回值：Long
     */
    public Long incr(Object key) {
        final String keyf = key.toString();

        return redisTemplate.execute((RedisConnection connection) ->
                connection.incr(keyf.getBytes())
        );
    }

    /**
     * 对key的值做的是减减操作，decr一个不存在key，则设置key为-1
     *
     * @param key key
     * @return 返回值：Long
     */
    public Long decr(Object key) {
        final String keyf = key.toString();

        return redisTemplate.execute((RedisConnection connection) ->
                connection.decr(keyf.getBytes())
        );
    }

    //------------------------------------------------------------------
    //----------------------JDK serialization---------------------------
    //------------------------------------------------------------------

    /**
     * < Object转byte[] >
     */
    private byte[] toByteArray(Object obj) {
        byte[] bytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            bytes = bos.toByteArray();
            oos.close();
            bos.close();
        } catch (Exception e) {
            LOG.error("toByteArray error!", e);
        }
        return bytes;
    }

    /**
     * < byte[]转Object >
     */
    private Object toObject(byte[] bytes) {
        Object obj = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bis);
            obj = ois.readObject();
            ois.close();
            bis.close();
        } catch (Exception e) {
            LOG.error("toObject error!", e);
        }
        return obj;
    }
}
