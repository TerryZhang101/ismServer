package com.ismServer.bussiness.form.request;



import com.alibaba.fastjson.annotation.JSONField;

/**
 * 创建登录form
 *
 * @author Terry Zhang
 * @date 2017-09-27 九月 22:44
 * @modify
 **/
public class LoginRequestForm extends BaseRequestForm {

    private static final long serialVersionUID = -4556836983968262451L;

    //手机号码 11 M
    @JSONField(name="mobile_no")
    private String mobileNo;

    //登录密码 256 M
    @JSONField(name="logon_pwd")
    private String logonPwd;
    
   
    @JSONField(name="img_code")
    private String   img_code;
  
    @JSONField(name="imageKey")
    private String imageKey;

    
    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getLogonPwd() {
        return logonPwd;
    }

    public void setLogonPwd(String logonPwd) {
        this.logonPwd = logonPwd;
    }

	public String getImg_code() {
		return img_code;
	}

	public void setImg_code(String img_code) {
		this.img_code = img_code;
	}

	public String getImageKey() {
		return imageKey;
	}

	public void setImageKey(String imageKey) {
		this.imageKey = imageKey;
	}

	@Override
	public String toString() {
		return "LoginRequestForm [mobileNo=" + mobileNo + ", logonPwd=" + logonPwd + ", img_code=" + img_code
				+ ", imageKey=" + imageKey + "]";
	}


	

}
