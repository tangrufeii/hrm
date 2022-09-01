package cn.itsource.exception;

import cn.itsource.result.JSONResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @description: 全局异常处理类
 * @auth: tangrufei
 * @date: 2022-08-20 09:16
 */
@RestControllerAdvice //声明当前类为全局异常捕获类
@Slf4j
public class GlobalExceptionHandler {

    //当前方法处理空指针异常
    @ExceptionHandler(value = NullPointerException.class)
    public JSONResult nullPointerExceptionHandler(NullPointerException e, String userId){
        log.error("发生异常：{}", e);
        return JSONResult.error("发生空指针异常");
    }

    //当前方法处理算术异常
    @ExceptionHandler(value = ArithmeticException.class)
    public JSONResult arithmeticExceptionHandler(ArithmeticException e){
        log.error("发生异常：{}", e);
        return JSONResult.error("发生算术异常");
    }

    //当前方法处理算术异常
    @ExceptionHandler(value = AccessDeniedException.class)
    public JSONResult arithmeticAccessDeniedException(AccessDeniedException e){
        return JSONResult.error("滚犊子，你无权访问！");
    }

    //当前方法处理自定义异常
    @ExceptionHandler(value = MyException.class)
    public JSONResult myExceptionHandler(MyException e){
        log.error("发生异常：{}", e);
        return JSONResult.error(e.getMessage());
    }

    //兜底异常
    @ExceptionHandler(value = Exception.class)
    public JSONResult exceptionHandler(Exception e){
        log.error("发生异常：{}", e);
        return JSONResult.error("系统异常，请稍候再试");
    }
}
