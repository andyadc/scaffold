package com.andyadc.scaffold.showcase.test;

import com.andyadc.scaffold.showcase.auth.entity.AuthUser;
import com.andyadc.scaffold.showcase.auth.security.PasswordHelper;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author andy.an
 * @since 2017/8/21
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext.xml"})
public class PasswordHelperTest {

    @Autowired
    private PasswordHelper passwordHelper;

    @Ignore
    @Test
    public void testGeneratePassword() {
        AuthUser authUser = new AuthUser();
        authUser.setAccount("admin");
        authUser.setPassword("123");
        passwordHelper.encryptPassword(authUser);
        System.out.println(authUser.getAccount());
        System.out.println(authUser.getSalt());
        System.out.println(authUser.getPassword());
    }
}
