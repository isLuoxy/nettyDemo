package com.luo.nettyDemo.serialize.impl;

import com.alibaba.fastjson.JSON;
import com.luo.nettyDemo.serialize.Serializer;
import com.luo.nettyDemo.serialize.SerializerAlgorithm;

/**
 *
 * @author L99
 * @createDate 2018/11/21
 *
 */
public class JSONSerializer implements Serializer {

    @Override
    public byte getSerializerAlogrithm() {
       return SerializerAlgorithm.JSON;
    }

    @Override
    public byte[] serializer(Object obj) {
        return JSON.toJSONBytes(obj);
    }

    @Override
    public <T> T deserializer(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes,clazz);
    }
}
