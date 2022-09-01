package cn.itsource.exception;

/**
 * @description: 自定义的运行时异常类
 * @auth: tangrufei
 * @date: 2022-08-20 09:35
 */
public class MyException extends RuntimeException{

    public MyException(String message){
        super(message);
    }
}
