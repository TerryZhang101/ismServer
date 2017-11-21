package com.ismServer.common.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Redis配置
 * @author XUCZ
 *
 */
@Configuration  
public class RedisConfiguration {  
      
	/**
	 * 创建redis连接池
	 * @param config
	 * @param host
	 * @param port
	 * @return JedisPool
	 */
    @Bean(name= "jedis.pool")  
    @Autowired  
    public JedisPool jedisPool(@Qualifier("jedis.pool.config") JedisPoolConfig config,   
                @Value("${jedis.pool.host}")String host,   
                @Value("${jedis.pool.port}")int port) {  
        return new JedisPool(config, host, port);  
    }  
      
    /**
     * redis配置
     * @param maxTotal
     * @param maxIdle
     * @param maxWaitMillis
     * @return JedisPoolConfig
     */
    @Bean(name= "jedis.pool.config")  
    public JedisPoolConfig jedisPoolConfig (@Value("${jedis.pool.config.maxTotal}")int maxTotal,  
                                @Value("${jedis.pool.config.maxIdle}")int maxIdle,  
                                @Value("${jedis.pool.config.maxWaitMillis}")int maxWaitMillis) {  
        JedisPoolConfig config = new JedisPoolConfig();  
        config.setMaxTotal(maxTotal);  
        config.setMaxIdle(maxIdle);  
        config.setMaxWaitMillis(maxWaitMillis);  
        return config;  
    }  
      
}  
