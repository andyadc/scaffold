package com.andyadc.scaffold.showcase.test;

import com.andyadc.scaffold.showcase.mail.MimeMailService;
import com.andyadc.scaffold.showcase.mail.SimpleMailService;
import org.junit.Ignore;
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
public class MailSendTest {

    @Autowired
    private SimpleMailService simpleMailService;
    @Autowired
    private MimeMailService mimeMailService;

    @Ignore
    @Test
    public void testSendMine() {
        mimeMailService.sendNotificationMail("andyadc");
    }

    @Ignore
    @Test
    public void testSend() {
        simpleMailService.sendMail("xx@qq.com", "from spring", "hello world");
    }
}
