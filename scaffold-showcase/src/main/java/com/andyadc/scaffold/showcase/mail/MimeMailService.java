package com.andyadc.scaffold.showcase.mail;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

/**
 * MIME邮件服务类.
 * <p>
 * 演示由Freemarker引擎生成的的html格式邮件, 并带有附件.
 *
 * @author andaicheng
 * @version 2017/1/5
 */
public class MimeMailService {

    private static final Logger LOG = LoggerFactory.getLogger(MimeMailService.class);

    private static final String DEFAULT_ENCODING = "utf-8";

    private JavaMailSender mailSender;
    private Template template;

    /**
     * 发送MIME格式的用户修改通知邮件.
     */
    public void sendNotificationMail(String userName) {
        try {
            MimeMessage msg = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(msg, true, DEFAULT_ENCODING);

            helper.setTo("xx@qq.com");
            helper.setFrom("xx@163.com");
            helper.setSubject("用户修改通知");

            String content = generateContent(userName);
            helper.setText(content, true);

            File attachment = generateAttachment();
            helper.addAttachment("mailAttachment.txt", attachment);

            mailSender.send(msg);
            LOG.info("HTML版邮件已发送至xx@qq.com.com");
        } catch (MessagingException e) {
            LOG.error("构造邮件失败", e);
        } catch (Exception e) {
            LOG.error("发送邮件失败", e);
        }
    }

    /**
     * 使用Freemarker生成html格式内容.
     */
    private String generateContent(String userName) throws MessagingException {

        try {
            Map context = Collections.singletonMap("userName", userName);
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, context);
        } catch (IOException e) {
            LOG.error("生成邮件内容失败, FreeMarker模板不存在", e);
            throw new MessagingException("FreeMarker模板不存在", e);
        } catch (TemplateException e) {
            LOG.error("生成邮件内容失败, FreeMarker处理失败", e);
            throw new MessagingException("FreeMarker处理失败", e);
        }
    }

    /**
     * 获取classpath中的附件.
     */
    private File generateAttachment() throws MessagingException {
        try {
            Resource resource = new ClassPathResource("/mail/mailAttachment.txt");
            return resource.getFile();
        } catch (IOException e) {
            LOG.error("构造邮件失败,附件文件不存在", e);
            throw new MessagingException("附件文件不存在", e);
        }
    }

    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void setFreemarkerConfiguration(Configuration freemarkerConfiguration) throws IOException {
        // 根据freemarkerConfiguration的templateLoaderPath载入文件.
        template = freemarkerConfiguration.getTemplate("mailTemplate.ftl", DEFAULT_ENCODING);
    }
}
