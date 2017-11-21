package com.ismServer.common.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ismServer.common.constant.ApplicationErrorCode;
import com.ismServer.common.exception.TranFailException;
import com.ismServer.common.util.JSONUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Redis客户端连接
 * @author XUCZ
 *
 */
@Component
public class RedisClient {

	@Autowired  
    private JedisPool jedisPool;  
      
	/**
	 * redis设值
	 * @param key
	 * @param value
	 * @throws TranFailException
	 */
    public void set(String key, Object value) throws TranFailException {  
        Jedis jedis = null;  
        try {  
            jedis = jedisPool.getResource();
            String redisValue = "";
            if(value instanceof String) {
            	redisValue = (String)value;
            } else {
            	redisValue = JSONUtil.objToJSON(value);
            }
            set(key, redisValue, 3600);  
        } catch (Exception e) {
			throw new TranFailException(ApplicationErrorCode.CACHE_ERROR, "redis存入数据异常");
		} finally {  
            //返还到连接池  
            jedis.close();  
        }  
    }  
    
    /**
     * redis设值
     * @param key
     * @param value
     * @param time
     * @throws TranFailException
     */
    public void set(String key, String value, int time) throws TranFailException {  
        Jedis jedis = null;  
        try {  
        	jedis = jedisPool.getResource();
            jedis.setex(key, time, value);  
        } catch (Exception e) {
			throw new TranFailException(ApplicationErrorCode.CACHE_ERROR, "redis存入数据异常");
		} finally {  
            //返还到连接池  
            jedis.close();  
        }  
    }  
      
    /**
     * redis获取数据
     * @param key
     * @return String
     * @throws TranFailException
     */
    public String get(String key) throws TranFailException  {  
        Jedis jedis = null;  
        try {  
            jedis = jedisPool.getResource();  
            return jedis.get(key);  
        } catch (Exception e) {
			throw new TranFailException(ApplicationErrorCode.CACHE_ERROR, "redis获取数据异常");
		} finally {  
            //返还到连接池  
            jedis.close();  
        }  
    } 
    
    /**
     * redis是否存在key
     * @param key
     * @throws TranFailException
     */
    public boolean hasKey(String key) throws TranFailException  {  
        Jedis jedis = null;  
        try {  
            jedis = jedisPool.getResource();  
            return jedis.exists(key);  
        } catch (Exception e) {
			throw new TranFailException(ApplicationErrorCode.CACHE_ERROR, "redis获取数据异常");
		} finally {  
            //返还到连接池  
            jedis.close();  
        }  
    } 
    
    /**
     * redis是否存在key
     * @param key
     * @throws TranFailException
     */
    public void delByKey(String key) throws TranFailException  {  
        Jedis jedis = null;  
        try {  
            jedis = jedisPool.getResource();  
            jedis.del(key);  
        } catch (Exception e) {
			throw new TranFailException(ApplicationErrorCode.CACHE_ERROR, "redis删除数据异常");
		} finally {  
            //返还到连接池  
            jedis.close();  
        }  
    } 
    
    /**
     * redis是否存在key
     * @param key
     * @throws TranFailException
     */
    public long expireTime(String key) throws TranFailException  {  
        Jedis jedis = null;  
        try {  
            jedis = jedisPool.getResource();  
            return expireTime(key, 3600);  
        } catch (Exception e) {
			throw new TranFailException(ApplicationErrorCode.CACHE_ERROR, "redis删除数据异常");
		} finally {  
            //返还到连接池  
            jedis.close();  
        }  
    } 
    
    /**
     * redis是否存在key
     * @param key
     * @throws TranFailException
     */
    public long expireTime(String key, int time) throws TranFailException  {  
        Jedis jedis = null;  
        try {  
            jedis = jedisPool.getResource();  
            return jedis.expire(key, time);  
        } catch (Exception e) {
			throw new TranFailException(ApplicationErrorCode.CACHE_ERROR, "redis删除数据异常");
		} finally {  
            //返还到连接池  
            jedis.close();  
        }  
    } 
}
