package com.example.tomatomall.exception;

public class TomatoMallException extends RuntimeException {
    public TomatoMallException(String message){super(message);}
    public static TomatoMallException WrongUsername(){
        return new TomatoMallException("用户名不存在");
    }

    public static TomatoMallException WrongPassword(){
        return new TomatoMallException("密码错误");
    }

    public static TomatoMallException WrongFormat(){
        return new TomatoMallException("格式错误");
    }

    public static  TomatoMallException DuplicateName(){
        return new TomatoMallException("用户名重复");
    }

    public static  TomatoMallException ProductNotFound(){
        return new TomatoMallException("未找到对应商品");
    }
}
