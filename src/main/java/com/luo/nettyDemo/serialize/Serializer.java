package com.luo.nettyDemo.serialize;


import com.luo.nettyDemo.serialize.impl.JSONSerializer;


/**
 * 序列化
 */
public interface Serializer {

    /**
     * 默认是 JSONSerializer
     */
    Serializer DEFAULT = new JSONSerializer();

    /**
     * 序列化算法
     */
    byte getSerializerAlogrithm();

    /**
     * java 对象序列化成二进制
     */
    byte[] serializer(Object obj);

    /**
     * 二进制转化成 java 对象
     */
    <T> T deserializer(Class<T> clazz,byte[] bytes);
}
