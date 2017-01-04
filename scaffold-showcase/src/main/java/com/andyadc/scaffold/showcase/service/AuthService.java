package com.andyadc.scaffold.showcase.service;

import com.andyadc.scaffold.showcase.entity.AuthUser;

/**
 * @author andaicheng
 * @version 2017/1/4
 */
public interface AuthService {

    AuthUser findAuthUserByAccount(String account);

    boolean lockAuthUser(String account);
}
