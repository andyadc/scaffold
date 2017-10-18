package com.andyadc.boot;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author andaicheng
 * @since 2017/10/18
 */
@Order(value = 3)
@Component
public class MyCommandLineRunner implements CommandLineRunner {

    @Override
    public void run(String... strings) throws Exception {
        System.out.println(">>>>    This is MyCommandLineRunner Order = 3   ...<<<<");
    }
}
