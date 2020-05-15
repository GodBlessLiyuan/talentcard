package com.talentcard.front.utils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author: xiahui
 * @date: Created in 2019/10/16 11:44
 * @description: Request 工具
 * @version: 1.0
 */
public class HttpServletRequestUtil {
    private static final String UNKNOWN = "unknown";
    private static final String LOCAL_IP = "127.0.0.1";

    /**
     * 根据req获取Ip
     *
     * @param req
     * @return
     */
    public static String getIpAddr(HttpServletRequest req) {
        String ipAddr = null;
        try {
            ipAddr = req.getHeader("x-forwarded-for");
            if (ipAddr == null || ipAddr.length() == 0 || HttpServletRequestUtil.UNKNOWN.equals(ipAddr)) {
                ipAddr = req.getHeader("Proxy-Client-IP");
            }
            if (ipAddr == null || ipAddr.length() == 0 || HttpServletRequestUtil.UNKNOWN.equals(ipAddr)) {
                ipAddr = req.getHeader("X-Forwarded-For");
            }
            if (ipAddr == null || ipAddr.length() == 0 || HttpServletRequestUtil.UNKNOWN.equals(ipAddr)) {
                ipAddr = req.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddr == null || ipAddr.length() == 0 || HttpServletRequestUtil.UNKNOWN.equals(ipAddr)) {
                ipAddr = req.getHeader("X-Real-IP");
            }
            if (ipAddr == null || ipAddr.length() == 0 || HttpServletRequestUtil.UNKNOWN.equals(ipAddr)) {
                ipAddr = req.getRemoteAddr();
                if (HttpServletRequestUtil.LOCAL_IP.equals(ipAddr)) {
                    // 根据网卡取本机配置的IP
                    InetAddress inetAddr = null;
                    try {
                        inetAddr = InetAddress.getLocalHost();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    ipAddr = inetAddr.getHostAddress();
                }
            }

            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            // "***.***.***.***".length() = 15
            if (ipAddr != null && ipAddr.length() > 15) {
                if (ipAddr.indexOf(",") > 0) {
                    ipAddr = ipAddr.substring(0, ipAddr.indexOf(","));
                }
            }
        } catch (Exception e) {
            ipAddr = "";
        }

        return "0:0:0:0:0:0:0:1".equals(ipAddr) ? LOCAL_IP : ipAddr;
    }
}
