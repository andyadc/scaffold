package com.andyadc.scaffold.showcase.sys.service.impl;

import com.andyadc.scaffold.showcase.sys.handle.SpringTransactionHandle;
import com.andyadc.scaffold.showcase.sys.mapper.SpringTransactionMapper;
import com.andyadc.scaffold.showcase.sys.service.SpringTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void service1() {

    }

    @Transactional
    @Override
    public boolean updateIfNumberIsZero(long id, int num) {
        int c = mapper.updateIfNumberIsZero(id, num);
        return c > 0;
    }
}
