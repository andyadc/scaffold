package com.andyadc.scaffold.showcase.test;

import com.andyadc.scaffold.showcase.mail.MailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author andaicheng
 * @version 2016/12/31
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext.xml"})
public class TestMailSend {

    @Autowired
    private MailService mailService;

    @Test
    public void testSend() {
        mailService.sendMail("andaicheng@qq.com", "from spring", "hello world");
    }
}
