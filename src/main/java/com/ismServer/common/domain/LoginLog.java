package com.ismServer.common.domain;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 创建用户日志记录实体
 *
 * @author Terry Zhang
 * @date 2017-09-19 九月 22:06
 * @modify
 **/
@Entity
@Table(name = "T_LOGIN_LOG")
public class LoginLog implements Serializable {

    private static final long serialVersionUID = -8767474884137581857L;

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "TLL_LOGON_ID", length = 32, nullable = false)
    private String logId;

    @Column(name = "TLL_CLIENT_IP", length = 64, nullable = false)
    private String clientIp;

    @Column(name = "TLL_SESSION_TOKEN", length = 64)
    private String sessionToken;

    @Column(name = "TLL_CREATE_DATE", length = 64, nullable = false)
    @CreatedDate
    private Date createDate;

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "LoginLog{" +
                "logId='" + logId + '\'' +
                ", clientIp='" + clientIp + '\'' +
                ", sessionToken='" + sessionToken + '\'' +
                ", createDate=" + createDate +
                '}';
    }
}
