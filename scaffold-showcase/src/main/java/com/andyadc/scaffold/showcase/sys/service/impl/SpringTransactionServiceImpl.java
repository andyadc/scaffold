package com.andyadc.scaffold.showcase.sys.service.impl;

import com.andyadc.scaffold.showcase.sys.entity.SpringTransaction;
import com.andyadc.scaffold.showcase.sys.handle.SpringTransactionHandle;
import com.andyadc.scaffold.showcase.sys.mapper.SpringTransactionMapper;
import com.andyadc.scaffold.showcase.sys.service.SpringTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author andaicheng
 * @version 2017/6/13
 */
@Service
public class SpringTransactionServiceImpl implements SpringTransactionService {

    @Autowired
    private SpringTransactionMapper mapper;
    @Autowired
    private SpringTransactionHandle handle;

    @Override
    public void service1(SpringTransaction transaction) {

    }

    @Override
    public void save(SpringTransaction transaction) {
        if (transaction.getId() != null)
            mapper.updateByPrimaryKeySelective(transaction);
        else
            mapper.insertSelective(transaction);
    }
}
