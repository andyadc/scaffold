package com.andyadc.scaffold.showcase.auth.service.impl;

import com.andyadc.scaffold.showcase.auth.entity.AuthUser;
import com.andyadc.scaffold.showcase.auth.mapper.AuthUserMapper;
import com.andyadc.scaffold.showcase.auth.service.AuthService;
import org.javasimon.aop.Monitored;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author andaicheng
 * @version 2017/1/4
 */
@Monitored
@Service
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

    @Transactional
    @Override
    public AuthUser saveAuthUser(AuthUser authUser) {
        if (authUser.getId() != null && authUser.getId() > 0)
            authUserMapper.updateByPrimaryKeySelective(authUser);
        else
            authUserMapper.insertSelective(authUser);
        return authUser;
    }

}
