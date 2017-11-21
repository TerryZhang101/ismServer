package com.ismServer.common.form;

import java.io.Serializable;

/**
 * 创建发送短信Form
 * @author Terry Zhang
 * @date 2017-09-30 九月 10:29
 * @modify
 **/
public class SmsMessageForm implements Serializable{

    private static final long serialVersionUID = -3770339470897021092L;

    private String name = "13603018707";

    private String pwd = "51B5656E506E86245D86D3274349";

    private String content = "您好，验证码为：$1，如果有疑问请联系：400-777-9456";

    private String mobile;

    private String stime;

    private String sign = "诺漫斯";

    private String type = "pt";

    private String extno;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getStime() {
        return stime;
    }

    public void setStime(String stime) {
        this.stime = stime;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExtno() {
        return extno;
    }

    public void setExtno(String extno) {
        this.extno = extno;
    }

    @Override
    public String toString() {
        return "SmsMessageForm{" +
                "name='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                ", content='" + content + '\'' +
                ", mobile='" + mobile + '\'' +
                ", stime='" + stime + '\'' +
                ", sign='" + sign + '\'' +
                ", type='" + type + '\'' +
                ", extno='" + extno + '\'' +
                '}';
    }
}
