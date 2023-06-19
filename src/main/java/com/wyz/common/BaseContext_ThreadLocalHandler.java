package com.wyz.common;


//每次请求都是一个单独的线程，这样可以让线程之间共享数据
/**
 * 基于ThreadLocal封装的工具类，用于保存和获取当前登录的id
 */
public class BaseContext_ThreadLocalHandler {
    private static ThreadLocal<Long> threadLocal=new ThreadLocal<>();

    //工具方法设置静态
    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }

    public static Long getCurrentId(){
        return threadLocal.get();
    }
}
