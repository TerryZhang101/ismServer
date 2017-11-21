package com.ismServer.common.util;

import java.util.Map;
import java.util.TreeMap;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ismServer.bussiness.form.request.BaseRequestForm;
import com.ismServer.bussiness.integration.request.BaseRequest;
import com.ismServer.common.exception.CacheException;
import com.ismServer.common.util.kit.SignKit;
import com.ismServer.common.util.kit.StrKit;


/**
 * 验签、签名工具类
 * @author cja
 * @date 2017年9月17日
 */
public class SignUtil {

	private static final String key = "123";

	/**
	 * 验签
	 * @param request
	 * @return Boolean
	 * @throws CacheException
	 */
	@SuppressWarnings("unchecked")
	public static Boolean checkSign(BaseRequestForm request) throws CacheException{
		
		boolean checkResult=false;
		
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("nonce_str", request.getNonceStr());
		jsonObj.put("sign",request.getSign());
		
		checkResult = SignKit.verify(JSON.toJavaObject(jsonObj, Map.class), key);

		return checkResult;


	}

	/**
	 * 签名
	 * @return Map
	 * @throws CacheException
	 */
	@SuppressWarnings("unchecked")
	public static Map sign() throws CacheException{

		Map requestMap=new TreeMap<String,Object>();
		String nonce_str = StrKit.getRandomUUID();
		requestMap.put("nonce_str", nonce_str);
		String sign = SignKit.createSign(JSON.parseObject(JSON.toJSONString(requestMap), Map.class), key);
		requestMap.put("sign", sign);
		return requestMap;
		
	}
	
}
