package com.luo.nettyDemo.util;


import com.luo.nettyDemo.attribute.Attributes;
import io.netty.channel.Channel;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

/**
 *  登录工具类
 */
public class LoginUtil {
    /**
     * 设置登录标记位
     */
    public static void markAsLogin(Channel channel){
       channel.attr(Attributes.LOGIN).set(true);
    }

    /**
     * 判断是否登录
     */
    public static boolean hasLogin(Channel channel){
        Attribute<Boolean> loginAttr = channel.attr(Attributes.LOGIN);
        return loginAttr.get() != null;
    }
}
