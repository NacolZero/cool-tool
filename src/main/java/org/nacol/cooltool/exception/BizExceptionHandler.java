package org.nacol.cooltool.exception;

import org.nacol.cooltool.common.result.Result;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * @Author Nacol(姚秋实)
 * @Date 2023/1/26
 * @Description 异常处理器
 */
@RestControllerAdvice
public class BizExceptionHandler {

    @ExceptionHandler(BizException.class)
    public Result userNotFoundExceptionHandler(BizException bizException) {
        return Result.bizException(bizException.getMessage(), bizException.getData());
    }

}
