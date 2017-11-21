package com.ismServer.common.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.ismServer.bussiness.form.request.LoginRequestForm;
import com.ismServer.common.constant.ApplicationErrorCode;
import com.ismServer.common.exception.BaseException;
import com.ismServer.common.exception.CacheException;
import com.ismServer.common.exception.SessionTimeOutException;
import com.ismServer.common.exception.TranFailException;
import com.ismServer.common.form.BaseForm;
import com.ismServer.common.redis.RedisClient;
import com.ismServer.common.util.JSONUtil;
import com.ismServer.common.util.MD5Util;

/**
 * Session逻辑类
 *
 * @author Terry Zhang
 * @date 2017-09-19 九月 21:23
 * @modify
 **/
@Component
public class SessionService {

    private static Logger logger = LoggerFactory.getLogger(SessionService.class);
    
    @Autowired
    private RedisClient redisClient;
    
    /**
     * 创建session
     * @param form
     * @return BaseForm
     */
    public BaseForm createSession(LoginRequestForm form) {
        logger.info("SessionService.createSession --> form:{}", form);
        try {
        	String mobileNo = form.getMobileNo();
        	//当检测到有相同的手机号码登录时，将先前那个删除
        	if(redisClient.hasKey(mobileNo)){
        		String checkToken = redisClient.get(mobileNo);
        		String checkSessionToken = getEncryptedSessionToken(checkToken);
        		if(redisClient.hasKey(checkSessionToken)){
        			redisClient.delByKey(checkSessionToken);
        		}
        	}
        	
            String serialNo = UUID.randomUUID().toString();
            String encryptedSessionToken = getEncryptedSessionToken(serialNo);

            Map<String, String> redisSessionMap = new HashMap<String, String>();
            redisSessionMap.put("sessionToken", serialNo);
            redisSessionMap.put("httpSessionId", form.getHttpSessionId());
            redisSessionMap.put("clientIp", form.getClientIp());
            redisSessionMap.put("mobileNo", mobileNo);

            redisClient.set(encryptedSessionToken, redisSessionMap);
            
            form.setRequestToken(serialNo);

            return form;
        } catch (Exception e) {
            logger.error("SessionService.createSession --> Exception:{}", e);
            throw new TranFailException(ApplicationErrorCode.SYSTEM_ERROR, "登录失败");
        }
    }

    /**
     * 退出系统，清除token
     * @param sessionToken
     */
    public void destorySessionWhenLogout(String sessionToken) {
        logger.info("SessionService.destorySessionWhenLogout --> sessionToken:{}", sessionToken);
        if(StringUtils.isEmpty(sessionToken)) {
            throw new TranFailException(ApplicationErrorCode.NOT_NULL, "token为空");
        }

        try {
            String encryptedSessionToken = getEncryptedSessionToken(sessionToken);
            //无论值是否存在，都清理这条缓存
            if(redisClient.hasKey(encryptedSessionToken)){
            	String userJson = redisClient.get(encryptedSessionToken);
                Map<String, String> redisSessionMap = JSONUtil.JSONToObj(userJson, Map.class);
                redisClient.delByKey(encryptedSessionToken);
            	
            	String mobileNo = redisSessionMap.get("mobileNo");
            	redisClient.delByKey(mobileNo);
            }
        } catch (Exception e) {
            logger.error("SessionService.destorySessionWhenLogout --> Exception:{}", e);
        }
    }

    /**
     * 对sessionToken做二次加密
     * @param sessionToken
     * @return
     */
    public String getEncryptedSessionToken(String sessionToken){
        /*
         * 对sessionToken和sessionContextKey做二次加密
         */
        String encryptedSessionToken = null;
        try{
            encryptedSessionToken = MD5Util.encodeByMD5(sessionToken);
        }
        catch (Exception e){

            throw new TranFailException(ApplicationErrorCode.SYSTEM_ERROR, "系统异常");
        }
        return encryptedSessionToken;
    }

    /**
     * 检查session是否有效
     * @param sessionToken
     * @param httpSessionId
     * @param clientIp
     * @throws IOException 
     */
    public void checkSessionAndAccessAuth(String sessionToken, String httpSessionId, String clientIp) throws IOException {
        if(StringUtils.isEmpty(sessionToken)){
            throw new SessionTimeOutException(ApplicationErrorCode.LOGIN_TIMEOUT, "Session已超时");
        }

        if(StringUtils.isEmpty(httpSessionId)){
            throw new SessionTimeOutException(ApplicationErrorCode.LOGIN_TIMEOUT, "Session已超时");
        }

        if(StringUtils.isEmpty(clientIp)){
            throw new SessionTimeOutException(ApplicationErrorCode.LOGIN_TIMEOUT, "Session已超时");
        }
        /*
         * 对sessionToken和sessionContextKey做二次加密
         */
        String encryptedSessionToken = getEncryptedSessionToken(sessionToken);
        try{
            /*
             * 中获取sessionToken反向验证
             */
        	if(redisClient.hasKey(encryptedSessionToken)){
        		throw new SessionTimeOutException(ApplicationErrorCode.LOGIN_TIMEOUT, "Session已超时");
        	}
        	
        	String userJson = redisClient.get(encryptedSessionToken);
            Map<String, String> redisSessionMap = JSONUtil.JSONToObj(userJson, Map.class);
            if(null == redisSessionMap || redisSessionMap.isEmpty() || redisSessionMap.size() == 0) {
            	throw new SessionTimeOutException(ApplicationErrorCode.LOGIN_TIMEOUT, "Session已超时");
            }
            String redisSessionToken = redisSessionMap.get("sessionToken");
            String redisHttpSessionId = redisSessionMap.get("httpSessionId");
            String redisClientIP = redisSessionMap.get("clientIp");
            if(!sessionToken.equals(redisSessionToken)){
                try{
                    //清除这条无效的sessionToken的缓存
                	redisClient.delByKey(encryptedSessionToken);
                	
                	String mobileNo = redisSessionMap.get("mobileNo");
                	redisClient.delByKey(mobileNo);
                }
                catch (Exception e){
                    throw new CacheException(ApplicationErrorCode.CACHE_ERROR, "缓存服务器异常", e);
                }
                throw new SessionTimeOutException(ApplicationErrorCode.LOGIN_TIMEOUT, "Session已超时");
            }

            /*
             * 检查httpSessionId是否一致
             */
//            if(!httpSessionId.equals(redisHttpSessionId)){
//                try{
		          		//清除这条无效的sessionToken的缓存
//		        		redisClient.delByKey(encryptedSessionToken);
//		        	
//		        		String mobileNo = redisSessionMap.get("mobileNo");
//		        		redisClient.delByKey(mobileNo);
//                }
//                catch (Exception e){
//                    throw new CacheException(ApplicationErrorCode.CACHE_ERROR, "缓存服务器异常", e);
//                }
//                throw new SessionTimeOutException(ApplicationErrorCode.LOGIN_TIMEOUT, "Session已超时");
//            }

            /*
             * 检查当前IP和缓存的上次操作IP是否一致，不一致时按超时处理
             */
//			if(!clientIp.equals(redisClientIP)){
//				try{
      				//清除这条无效的sessionToken的缓存
//    				redisClient.delByKey(encryptedSessionToken);
//    	
//    				String mobileNo = redisSessionMap.get("mobileNo");
//    				redisClient.delByKey(mobileNo);
//				}
//				catch (Exception e){
//					throw new CacheException(ApplicationErrorCode.CACHE_ERROR, "缓存服务器异常", e);
//				}
//				throw new SessionTimeOutException(ApplicationErrorCode.LOGIN_TIMEOUT, "Session已超时");
//			}
        } catch(BaseException e){
            throw e;
        }
    }

    /**
     * 更新session数据
     * @param sessionToken
     * @param httpSessionId
     * @param clientIp
     * @throws IOException 
     */
    public void updateSession(String sessionToken, String httpSessionId, String clientIp) throws IOException {
        if(StringUtils.isEmpty(sessionToken)){
            throw new SessionTimeOutException(ApplicationErrorCode.LOGIN_TIMEOUT, "Session已超时");
        }

        if(StringUtils.isEmpty(httpSessionId)){
            throw new SessionTimeOutException(ApplicationErrorCode.LOGIN_TIMEOUT, "Session已超时");
        }

        if(StringUtils.isEmpty(clientIp)){
            throw new SessionTimeOutException(ApplicationErrorCode.LOGIN_TIMEOUT, "Session已超时");
        }
        /*
         * 对sessionToken和sessionContextKey做二次加密
         */
        String encryptedSessionToken = getEncryptedSessionToken(sessionToken);
        try{
        	String userJson = redisClient.get(encryptedSessionToken);
            Map<String, String> redisSessionMap = JSONUtil.JSONToObj(userJson, Map.class);
            redisSessionMap.put("sessionToken", sessionToken);
            redisSessionMap.put("httpSessionId", httpSessionId);
            redisSessionMap.put("clientIp", clientIp);
            
            redisClient.set(encryptedSessionToken, JSONUtil.objToJSON(redisSessionMap));
            String mobileNo = redisSessionMap.get("mobileNo");
            redisClient.set(mobileNo, sessionToken);
        } catch(BaseException e){
            throw e;
        }
    }
}
