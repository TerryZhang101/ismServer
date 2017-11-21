package com.ismServer.bussiness.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author Terry Zhang
 * @date 2017-09-16 九月 19:53
 * @modify
 **/
@Entity
@Table(name = "T_USER")
public class User implements Serializable {

    private static final long serialVersionUID = 7466301491691835432L;

    @Id
    @Column(name = "CB_CUSTNO", length = 32)
    private String custNo;

    @Column(name = "CB_MOBILE_NO", length = 11)
    private String mobileNo;

    public String getCustNo() {
        return custNo;
    }

    public void setCustNo(String custNo) {
        this.custNo = custNo;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    @Override
    public String toString() {
        return "User{" +
                "custNo='" + custNo + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                '}';
    }
}
