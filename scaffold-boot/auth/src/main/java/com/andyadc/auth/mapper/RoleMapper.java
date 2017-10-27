package com.andyadc.auth.mapper;

import com.andyadc.auth.entity.Role;
import com.andyadc.auth.repository.MyBatisRepository;

@MyBatisRepository
public interface RoleMapper {
    int deleteByPrimaryKey(String id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
}