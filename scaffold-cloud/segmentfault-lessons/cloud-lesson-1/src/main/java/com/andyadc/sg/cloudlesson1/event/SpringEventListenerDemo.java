package com.andyadc.sg.cloudlesson1.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author andy.an
 * @since 2017/10/30
 */
public class SpringEventListenerDemo {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.addApplicationListener(new MyApplicationListener());

        context.refresh();

        context.publishEvent(new MyApplicationEvent("Hello Event!"));
        context.publishEvent(new MyApplicationEvent(1));
    }

    private static class MyApplicationListener implements ApplicationListener<MyApplicationEvent> {

        @Override
        public void onApplicationEvent(MyApplicationEvent myApplicationEvent) {

            System.out.printf("MyApplicationListener receives event source: %s \n", myApplicationEvent.getSource());
        }
    }

    private static class MyApplicationEvent extends ApplicationEvent {

        public MyApplicationEvent(Object source) {
            super(source);
        }
    }
}
