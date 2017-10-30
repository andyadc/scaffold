package com.andyadc.sg.cloudlesson1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author andy.an
 * @since 2017/10/30
 */
@SpringBootApplication
public class SpringCloudLesson1Application {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(SpringCloudLesson1Application.class);
        application.setWebEnvironment(true);
        application.run(args);
    }
}
