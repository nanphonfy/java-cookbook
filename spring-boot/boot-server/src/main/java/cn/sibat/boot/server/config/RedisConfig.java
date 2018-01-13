package cn.sibat.boot.server.config;

import cn.sibat.boot.server.dao.redis.JedisClient;
import cn.sibat.boot.server.dao.redis.JedisClientSingle;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 定义Redis连接池
 */
@Configuration
public class RedisConfig {
    @Bean(name = "jedisPool")
    public JedisPool getPool(@Qualifier("redisProperties") RedisProperties redisProperties) {
        //System.out.println("> jedis pool start");
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(10);
        jedisPoolConfig.setMaxTotal(10);
        jedisPoolConfig.setMaxWaitMillis(5000);
        jedisPoolConfig.setTestOnBorrow(true);
        return new JedisPool(jedisPoolConfig,
                            redisProperties.getIp(),
                            redisProperties.getPort(),
                            redisProperties.getTimeout());
    }

    @Bean(name = "jedisClient")
    public JedisClient getJedisClient() {
        return new JedisClientSingle();
    }
}
