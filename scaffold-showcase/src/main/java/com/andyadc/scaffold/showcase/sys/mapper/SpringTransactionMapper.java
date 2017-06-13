package com.andyadc.scaffold.showcase.sys.mapper;

import com.andyadc.scaffold.showcase.common.repository.MyBatisRepository;
import com.andyadc.scaffold.showcase.sys.entity.SpringTransaction;

@MyBatisRepository
public interface SpringTransactionMapper {

    int deleteByPrimaryKey(Long id);

    int insertSelective(SpringTransaction record);

    SpringTransaction selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SpringTransaction record);
}