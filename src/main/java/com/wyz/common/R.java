package com.wyz.common;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

//此类是一个通用结果类,服务端响应的所有结果最终都会包装成此种类型返回给前端页面
//注意这里要实现序列化接口Serializable，这样spring-cache才能序列化
@Data
public class R<T> implements Serializable {

    private Integer code; //编码：1成功，0和其它数字为失败

    private String msg; //错误信息

    private T data; //数据

    private Map map = new HashMap(); //动态数据

    //响应成功返回R对象
    public static <T> R<T> success(T object) {
        R<T> r = new R<T>();
        r.data = object;
        r.code = 1;
        return r;
    }

    //响应失败也返回R对象
    public static <T> R<T> error(String msg) {
        R r = new R();
        r.msg = msg;
        r.code = 0;
        return r;
    }

    public R<T> add(String key, Object value) {
        this.map.put(key, value);
        return this;
    }
}
