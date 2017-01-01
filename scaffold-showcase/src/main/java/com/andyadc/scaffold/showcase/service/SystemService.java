package com.andyadc.scaffold.showcase.service;


import com.andyadc.scaffold.showcase.entity.AuthUser;

/**
 * @author andaicheng
 */
public interface SystemService {

    AuthUser findByAccount(String account);

    boolean lockAuthUser(String account);
}
