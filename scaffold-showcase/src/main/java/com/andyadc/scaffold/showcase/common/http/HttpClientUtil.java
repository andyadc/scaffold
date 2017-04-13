package com.andyadc.scaffold.showcase.common.http;

import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.DnsResolver;
import org.apache.http.conn.HttpConnectionFactory;
import org.apache.http.conn.ManagedHttpClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.conn.ManagedHttpClientConnectionFactory;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.conn.SystemDefaultDnsResolver;
import org.apache.http.impl.io.DefaultHttpRequestWriterFactory;
import org.apache.http.impl.io.DefaultHttpResponseParserFactory;

/**
 * HttpClient 4.5.3
 * zhangkaitao
 *
 * @author andaicheng
 * @version 2017/4/12
 */
public class HttpClientUtil {

    static PoolingHttpClientConnectionManager manager = null;
    static CloseableHttpClient httpClient = null;

    public static synchronized CloseableHttpClient getHttpClient() {
        if (httpClient == null) {

            //注册访问协议相关的Socket工厂
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.INSTANCE)
                    .register("https", SSLConnectionSocketFactory.getSystemSocketFactory())
                    .build();

            //HttpConnection 工厂; 配置写请求/解析响应处理器
            HttpConnectionFactory<HttpRoute, ManagedHttpClientConnection> connectionFactory = new ManagedHttpClientConnectionFactory(
                    DefaultHttpRequestWriterFactory.INSTANCE,
                    DefaultHttpResponseParserFactory.INSTANCE
            );

            //DNS 解析器
            DnsResolver dnsResolver = SystemDefaultDnsResolver.INSTANCE;

            manager = new PoolingHttpClientConnectionManager(socketFactoryRegistry, connectionFactory, dnsResolver);

        }

        return null;
    }
}
