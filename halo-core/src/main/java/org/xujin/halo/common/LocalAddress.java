package org.xujin.halo.common;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 获取本机地址
 * @author xujin
 * @date 2018/02/07
 */
public class LocalAddress {
    public static String IP = "";

    static {
        try {
            IP = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
