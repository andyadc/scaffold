package com.andyadc.scaffold.showcase.auth.service.impl;

import com.andyadc.scaffold.showcase.auth.entity.AuthUser;
import com.andyadc.scaffold.showcase.auth.mapper.AuthUserMapper;
import com.andyadc.scaffold.showcase.auth.service.AuthService;
import org.javasimon.aop.Monitored;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author andaicheng
 * @version 2017/1/4
 */
@Monitored
@Service("authService")
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthUserMapper authUserMapper;

    @Override
    public AuthUser findAuthUserByAccount(String account) {
        return authUserMapper.selectByAccount(account);
    }

    @Override
    public boolean lockAuthUser(String account) {
        return authUserMapper.lockAuthUserByAccount(account) > 0;
    }
}
