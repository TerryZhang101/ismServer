package com.ismServer.bussiness.integration.request;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ismServer.common.util.SignUtil;

import java.io.Serializable;
import java.util.Map;

/**
 * 创建基础发送类
 *
 * @author Terry Zhang
 * @date 2017-09-07 九月 22:36
 * @modify
 **/
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseRequest implements Serializable {

    private static final long serialVersionUID = -3861336179009804628L;

    //法人代码（机构代码） 32 M
    @JSONField(name="organ_id")
    private String organId;

    //渠道(00=APP 01= PC) 2 M
    @JSONField(name="channel")
    private String channel = "00";
    
    Map requestMap=SignUtil.sign();
    
    //随机数  32 M
    @JSONField(name="nonce_str")
    private String nonceStr = (String) requestMap.get("nonce_str");
    
    //签名  32 M
    @JSONField(name="sign")
    private String sign = (String) requestMap.get("sign");
    
    
    public BaseRequest() {
        super();
    }

    public BaseRequest(String organId, String channel) {
        this.organId = organId;
        this.channel = channel;
    }

    @JSONField(name="organ_id")
    public String getOrganId() {
        return organId;
    }

    public void setOrganId(String organId) {
        this.organId = organId;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    
    public String getNonceStr() {
		return nonceStr;
	}

	public String getSign() {
		return sign;
	}

	@Override
    public String toString() {
        return "BaseRequest{" +
                "organId='" + organId + '\'' +
                ", channel='" + channel + '\'' +
                '}';
    }
}
