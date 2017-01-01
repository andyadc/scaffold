package com.andyadc.scaffold.showcase.mapper;

import com.andyadc.scaffold.showcase.common.repository.MyBatisRepository;
import com.andyadc.scaffold.showcase.entity.AuthUser;

@MyBatisRepository
public interface AuthUserMapper {

    int deleteByPrimaryKey(Long id);

    int insertSelective(AuthUser record);

    AuthUser selectByPrimaryKey(Long id);

    AuthUser selectByAccount(String account);

    int updateByPrimaryKeySelective(AuthUser record);

    int deleteAuthUserLogic(Long id);

    int lockAuthUserByAccount(String account);
}