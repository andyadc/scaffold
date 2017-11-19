package com.andyadc.scaffold.rabbitconsumer;

/**
 * @author andaicheng
 * @since 2017/11/16
 */
public class MessageHandler {

    /**
     * default method name
     */
    public void handleMessage(byte[] body) {
        System.out.println(new String(body));
    }

    public void handleDebug(String message) {
        System.out.println("--------------debug-------------");
        System.out.println(message);
    }

    public void handleInfo(String message) {
        System.out.println("--------------info-------------");
        System.out.println(message);
    }

    public void handleInfo(Order order) {
        System.out.println("--------------info-------------");
        System.out.println(order);
    }

    public void handleError(String message) {
        System.out.println("--------------error-------------");
        System.out.println(message);
    }
}
