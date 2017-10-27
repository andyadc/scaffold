package com.andyadc.auth.test;

import com.andyadc.auth.mapper.RoleMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author andy.an
 * @since 2017/10/27
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthTests {

    @Autowired
    private RoleMapper roleMapper;

    @Test
    public void testSelect() {
        System.out.println("**************************************");
        String value = roleMapper.selectByPrimaryKey("1").getName();
        System.out.println(value);
        System.out.println("**************************************");
    }
}
