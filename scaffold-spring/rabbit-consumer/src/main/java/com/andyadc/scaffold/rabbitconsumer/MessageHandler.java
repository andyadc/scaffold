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

    public void handleDebug(byte[] body) {
        System.out.println("--------------debug-------------");
        System.out.println(new String(body));
    }

    public void handleInfo(byte[] body) {
        System.out.println("--------------info-------------");
        System.out.println(new String(body));
    }

    public void handleError(byte[] body) {
        System.out.println("--------------error-------------");
        System.out.println(new String(body));
    }
}
