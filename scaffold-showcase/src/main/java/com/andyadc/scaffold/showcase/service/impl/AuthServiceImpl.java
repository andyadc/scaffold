package com.andyadc.scaffold.showcase.service.impl;

import com.andyadc.scaffold.showcase.entity.AuthUser;
import com.andyadc.scaffold.showcase.mapper.AuthUserMapper;
import com.andyadc.scaffold.showcase.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author andaicheng
 * @version 2017/1/4
 */
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
