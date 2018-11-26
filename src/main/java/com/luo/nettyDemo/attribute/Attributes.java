package com.luo.nettyDemo.attribute;

import io.netty.util.AttributeKey;

/**
 *  一些属性定义
 */
public interface Attributes {

    /**
    *  登录标志
    */
    AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");
}
