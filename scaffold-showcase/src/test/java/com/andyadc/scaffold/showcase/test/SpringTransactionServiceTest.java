package com.andyadc.scaffold.showcase.test;

import com.andyadc.scaffold.showcase.sys.entity.SpringTransaction;
import com.andyadc.scaffold.showcase.sys.service.SpringTransactionService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author andaicheng
 * @version 2017/6/13
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext.xml"})
public class SpringTransactionServiceTest {

    @Autowired
    private SpringTransactionService springTransactionService;

    @Test
    public void testSave() {
        springTransactionService.save(new SpringTransaction("test"));
    }

    @Before
    public void before() {
        System.out.println("------------------------------------------------");
    }

    @After
    public void after() {
        System.out.println("------------------------------------------------");
    }
}
