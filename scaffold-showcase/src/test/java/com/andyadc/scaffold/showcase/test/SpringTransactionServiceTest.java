package com.andyadc.scaffold.showcase.test;

import com.andyadc.scaffold.showcase.sys.service.SpringTransactionService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.Duration;
import java.time.Instant;

/**
 * @author andaicheng
 * @version 2017/6/13
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext.xml"})
public class SpringTransactionServiceTest {

    private static final Logger LOG = LoggerFactory.getLogger(SpringTransactionServiceTest.class);

    @Autowired
    private SpringTransactionService springTransactionService;
    @Autowired
    private TaskExecutor taskExecutor;

    @Test
    public void testUpdateIfNull() {
        Instant begin = Instant.now();
        long id = 3;
        int number = 1;

        for (int i = 0; i < 10; i++) {
            taskExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    boolean flag = springTransactionService.updateIfNumberIsZero(id, number);
                    LOG.info("{} update status: {}", Thread.currentThread().getName(), flag);
                }
            });
        }

        Instant end = Instant.now();
        LOG.info("elapsed time: {}", Duration.between(begin, end).toMillis());
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
