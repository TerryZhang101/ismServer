package com.ismServer.bussiness.form.response;

import com.alibaba.fastjson.annotation.JSONField;
import com.ismServer.common.form.BaseForm;

/**
 * 响应基础form
 *
 * @author Terry Zhang
 * @date 2017-09-27 九月 22:50
 * @modify
 **/
public class BaseResponseForm extends BaseForm {

    private static final long serialVersionUID = -2261940443417543209L;

    //0=交易正常 其他交易非正常
    @JSONField(name="ec")
    private String resultCode;

    //响应信息
    @JSONField(name="em")
    private String resultMsg;

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    @Override
    public String toString() {
        return "BaseResponseForm{" +
        		super.toString() +
                ", resultCode='" + resultCode + '\'' +
                ", resultMsg='" + resultMsg + '\'' +
                '}';
    }
}
