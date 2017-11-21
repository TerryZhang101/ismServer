package com.ismServer.bussiness.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ismServer.bussiness.domain.User;

/**
 * @author Terry Zhang
 * @date 2017-09-16 九月 19:56
 * @modify
 **/
public interface UserRepository extends JpaRepository<User, String> {

    User findUserByCustNo(String custNo);

    List<User> findUsersByMobileNo(String mobileNo);
}
