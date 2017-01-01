package com.andyadc.scaffold.showcase.service.impl;

import com.andyadc.scaffold.showcase.entity.AuthUser;
import com.andyadc.scaffold.showcase.mapper.AuthUserMapper;
import com.andyadc.scaffold.showcase.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author andaicheng
 */
@Service("systemService")
public class SystemServiceImpl implements SystemService {

    @Autowired
    private AuthUserMapper authUserMapper;

    @Override
    public AuthUser findByAccount(String account) {
        return authUserMapper.selectByAccount(account);
    }

    @Override
    public boolean lockAuthUser(String account) {
        return authUserMapper.lockAuthUserByAccount(account) > 0;
    }
}
