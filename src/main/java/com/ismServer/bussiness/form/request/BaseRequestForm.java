package com.ismServer.bussiness.form.request;

import com.alibaba.fastjson.annotation.JSONField;
import com.ismServer.common.form.BaseForm;
import com.ismServer.common.util.SignUtil;

import java.util.Map;

/**
 * 创建基础form
 *
 * @author Terry Zhang
 * @date 2017-09-27 九月 22:47
 * @modify
 **/
public class BaseRequestForm extends BaseForm {

    private static final long serialVersionUID = -658380873387470830L;

    //法人代码（机构代码） 32 M
    @JSONField(name="organ_id")
    private String organId;

    //渠道(00=APP 01= PC) 2 M
    @JSONField(name="channel")
    private String channel = "00";

    Map requestMap= SignUtil.sign();

    //随机数  32 M
    @JSONField(name="nonce_str")
    private String nonceStr = (String) requestMap.get("nonce_str");

    //签名  32 M
    @JSONField(name="sign")
    private String sign = (String) requestMap.get("sign");


    public BaseRequestForm() {
        super();
    }

    public BaseRequestForm(String organId, String channel) {
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
        return "BaseRequestForm{" +
        		super.toString() +
                ",organId='" + organId + '\'' +
                ", channel='" + channel + '\'' +
                ", requestMap=" + requestMap +
                ", nonceStr='" + nonceStr + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }
}
