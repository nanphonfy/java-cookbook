package cn.sibat.boot.server.dao.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 操作redis的接口
 */
public interface JedisClient {
    String get(String key);

    String set(String key, String value);

    List<String> lrange(String key, long start, long end);

    String hget(String hkey, String key);

    boolean hexists(String hkey, String key);

    Set smembers(String key);

    Map<String, String> hgetAll(String hkey);

    long hset(String hkey, String key, String value);

    long incr(String key);

    long expire(String key, int second);

    long ttl(String key);

    long del(String key);

    long hdel(String hkey, String key);

    boolean exists(String key);

    Set<String> keys(String s);
}
