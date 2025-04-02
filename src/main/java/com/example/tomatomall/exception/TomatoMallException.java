package com.example.tomatomall.exception;

public class TomatoMallException extends RuntimeException {
    public TomatoMallException(String message){super(message);}

    public static TomatoMallException WrongUsername(){
        return new TomatoMallException("用户名不存在，请检查后重试");
    }

    public static TomatoMallException WrongPassword(){
        return new TomatoMallException("密码错误，请重新输入");
    }

    public static TomatoMallException WrongFormat(){
        return new TomatoMallException("输入格式不符合要求，请检查后重试");
    }

    public static TomatoMallException DuplicateName(){
        return new TomatoMallException("用户名已被使用，请尝试其他用户名");
    }

    public static TomatoMallException ProductNotFound(){
        return new TomatoMallException("未找到对应商品，请检查商品ID");
    }

    public static TomatoMallException DuplicateProduct(){
        return new TomatoMallException("已存在同名商品，请修改商品名称");
    }

    public static TomatoMallException NoEnoughArguments(){
        return new TomatoMallException("请求参数不足，请检查请求内容");
    }

    public static TomatoMallException InvalidPhoneNumber() {
        return new TomatoMallException("手机号格式不正确，请输入11位有效手机号");
    }

    public static TomatoMallException notLogin() { return new TomatoMallException("未登录，请先登录");}

    public static TomatoMallException phoneAlreadyExists(){return new TomatoMallException("手机号已存在，请使用其他手机号注册");}

    public static TomatoMallException ossError(){return new TomatoMallException("oss服务出现异常");};
}
