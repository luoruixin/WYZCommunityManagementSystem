package com.wyz.common;

/**
 * 自定义业务异常
 */
public class CustomExcepion extends RuntimeException{
    public CustomExcepion(String msg){
        super(msg);
    }
}
