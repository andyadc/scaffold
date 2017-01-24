package com.andyadc.scaffold.showcase.web;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * @author andaicheng
 * @version 2017/1/8
 */
public class IPAddrFetcher {

    /**
     * 获取客户端IP地址，支持代理服务器
     */
    public static String getRemoteIpAddress(HttpServletRequest request) {
        String ip = "";
        //匹配大小写，保证无论Nginx如何配置代理参数，系统都能正常获取代理IP
        Enumeration<?> enumeration = request.getHeaderNames();
        while (enumeration.hasMoreElements()) {
            String paraName = (String) enumeration.nextElement();
            if ("x-forward-for".equalsIgnoreCase(paraName) || "x-forwarded-for".equalsIgnoreCase(paraName)) {
                ip = request.getHeader(paraName);
                break;
            }
        }
        final String localIP = "127.0.0.1";
        if ((ip == null) || (ip.length() == 0) || (ip.equalsIgnoreCase(localIP)) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if ((ip == null) || (ip.length() == 0) || (ip.equalsIgnoreCase(localIP)) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if ((ip == null) || (ip.length() == 0) || (ip.equalsIgnoreCase(localIP)) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
