package cn.sibat.ebase.server.dao.redis;

import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class JedisClientSingle implements JedisClient {
    @Autowired
    private JedisPool jedisPool;

    @Override
    public String get(String key) {
        Jedis jedis = jedisPool.getResource();
        String string = jedis.get(key);
        jedis.close();
        return string;
    }

    @Override
    public String set(String key, String value) {
        Jedis jedis = jedisPool.getResource();
        String string = jedis.set(key, value);
        jedis.close();
        return string;
    }

    @Override
    public List<String> lrange(String key, long start, long end) {
        Jedis jedis = jedisPool.getResource();
        List<String> string = jedis.lrange(key, start, end);
        jedis.close();
        return string;
    }

    @Override
    public String hget(String hkey, String key) {
        Jedis jedis = jedisPool.getResource();
        String string = jedis.hget(hkey, key);
        jedis.close();
        return string;
    }

    @Override
    public boolean hexists(String hkey, String key) {
        Jedis jedis = jedisPool.getResource();
        boolean string = jedis.hexists(hkey, key);
        jedis.close();
        return string;
    }

    @Override
    public Set smembers(String key) {
        Jedis jedis = jedisPool.getResource();
        Set<String> string = jedis.smembers(key);
        jedis.close();
        return string;
    }

    @Override
    public Map<String, String> hgetAll(String hkey) {
        Jedis jedis = jedisPool.getResource();
        Map<String, String> string = jedis.hgetAll(hkey);
        jedis.close();
        return string;
    }

    @Override
    public long hset(String hkey, String key, String value) {
        Jedis jedis = jedisPool.getResource();
        Long result = jedis.hset(hkey, key, value);
        jedis.close();
        return result;
    }

    @Override
    public long incr(String key) {
        Jedis jedis = jedisPool.getResource();
        Long result = jedis.incr(key);
        jedis.close();
        return result;
    }

    @Override
    public long expire(String key, int second) {
        Jedis jedis = jedisPool.getResource();
        Long result = jedis.expire(key, second);
        jedis.close();
        return result;
    }

    @Override
    public long ttl(String key) {
        Jedis jedis = jedisPool.getResource();
        Long result = jedis.ttl(key);
        jedis.close();
        return result;
    }

    @Override
    public long del(String key) {
        Jedis jedis = jedisPool.getResource();
        Long result = jedis.del(key);
        jedis.close();
        return result;
    }

    @Override
    public long hdel(String hkey, String key) {
        Jedis jedis = jedisPool.getResource();
        Long result = jedis.hdel(hkey, key);
        jedis.close();
        return result;
    }

    @Override
    public boolean exists(String key) {
        Jedis jedis = jedisPool.getResource();
        boolean result = jedis.exists(key);
        jedis.close();
        return result;
    }

    @Override
    public Set<String> keys(String s) {
        Jedis jedis = jedisPool.getResource();
        Set<String> list = jedis.keys(s);
        jedis.close();
        return list;
    }
}
