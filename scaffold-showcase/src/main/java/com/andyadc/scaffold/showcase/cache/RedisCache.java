package com.andyadc.scaffold.showcase.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
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

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Object get(Object key) {
        final String keyf = key.toString();
        Object object;

        object = redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) {
                byte[] key = keyf.getBytes();
                byte[] value = connection.get(key);
                if (value == null) {
                    return null;
                }
                return toObject(value);
            }
        });
        return object;
    }

    public void put(Object key, Object value, final long liveTime) {
        final String keyf = key.toString();
        final Object valuef = value;

        redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) {
                byte[] keyb = keyf.getBytes();
                byte[] valueb = toByteArray(valuef);
                connection.set(keyb, valueb);
                if (liveTime > 0) {
                    connection.expire(keyb, liveTime);
                }
                return 1L;
            }
        });
    }

    public void del(Object key) {
        final String keyf = key.toString();
        redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) {
                return connection.del(keyf.getBytes());
            }
        });
    }

    public void exprie(Object key, final long liveTime) {
        final String keyf = key.toString();
        redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) {
                return connection.expire(keyf.getBytes(), liveTime);
            }
        });
    }

    public Boolean exist(Object key) {
        final String keyf = key.toString();
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) {
                return connection.exists(keyf.getBytes());
            }
        });
    }

    public void clear() {
        redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) {
                connection.flushDb();
                return "ok";
            }
        });
    }

    /**
     * 对key的值做加加操作,并返回新的值。 incr一个不存在的key，则设置key为1 方法描述: <br>
     *
     * @param key key
     * @return 返回值：Long <br>
     */
    public Long incr(Object key) {
        final String keyf = key.toString();
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) {
                return connection.incr(keyf.getBytes());
            }
        });
    }

    /**
     * 对key的值做的是减减操作，decr一个不存在key，则设置key为-1 <br>
     *
     * @param key key
     * @return 返回值：Long <br>
     */
    public Long decr(Object key) {
        final String keyf = key.toString();
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) {
                return connection.decr(keyf.getBytes());
            }
        });
    }

    /**
     * < Object转byte[] >. <br>
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
     * < byte[]转Object >. <br>
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
