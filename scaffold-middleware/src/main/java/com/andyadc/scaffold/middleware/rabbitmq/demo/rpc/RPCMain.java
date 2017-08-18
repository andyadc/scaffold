package com.andyadc.scaffold.middleware.rabbitmq.demo.rpc;

/**
 * @author andy.an
 * @since 2017/8/18
 */
public class RPCMain {

    public static void main(String[] args) throws Exception {
        RPCClient fibonacciRpc = new RPCClient();
        System.out.println(" [x] Requesting fib(10)");
        String response = fibonacciRpc.call("10");
        System.out.println(" [.] Got '" + response + "'");

        fibonacciRpc.close();
    }
}
