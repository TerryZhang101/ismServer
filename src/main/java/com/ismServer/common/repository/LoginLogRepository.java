package com.ismServer.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ismServer.common.domain.LoginLog;

/**
 * 用户日志记录Repository
 *
 * @author Terry Zhang
 * @date 2017-09-19 九月 22:35
 * @modify
 **/
public interface LoginLogRepository extends JpaRepository<LoginLog, String>{
}
