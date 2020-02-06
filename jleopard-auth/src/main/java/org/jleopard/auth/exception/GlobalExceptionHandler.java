/**
 * @author (c) 2019. Chen_9g jleopard@126.com.
 * @date 2020/2/2  20:52
 * @version 1.0
 *
 * <p>
 * Find a way for success and not make excuses for failure.
 * </p>
 */
package org.jleopard.auth.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Objects;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理所有不可知的异常
     */
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Error> handleException(Throwable e){
        // 打印堆栈信息
        log.error(getStackTrace(e));
        return buildResponseEntity(Error.error(500, e.getMessage()));
    }

    /**
     * BadCredentialsException
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Error> badCredentialsException(BadCredentialsException e){
        // 打印堆栈信息
        String message = "坏的凭证".equals(e.getMessage()) ? "用户名或密码不正确" : e.getMessage();
        log.error(message);
        return buildResponseEntity(Error.error(message));
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<Error> disabledException(DisabledException e){
        log.error(e.getMessage());
        return buildResponseEntity(Error.error("账户被禁用"));
    }
    @ExceptionHandler(AccountExpiredException.class)
    public ResponseEntity<Error> accountExpiredException(AccountExpiredException e){
        log.error(e.getMessage());
        return buildResponseEntity(Error.error("账户过期无法验证"));
    }

    /**
     * 处理所有接口数据验证异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Error> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        // 打印堆栈信息
        log.error(getStackTrace(e));
        String[] str = Objects.requireNonNull(e.getBindingResult().getAllErrors().get(0).getCodes())[1].split("\\.");
        String message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        String msg = "不能为空";
        if(msg.equals(message)){
            message = str[1] + ":" + message;
        }
        return buildResponseEntity(Error.error(400, message));
    }

    /**
     * 统一返回
     */
    private ResponseEntity<Error> buildResponseEntity(Error Error) {
        return new ResponseEntity<>(Error, HttpStatus.valueOf(Error.getStatus()));
    }

    private String getStackTrace(Throwable throwable) {
        StringWriter sw = new StringWriter();
        try (PrintWriter pw = new PrintWriter(sw)) {
            throwable.printStackTrace(pw);
            return sw.toString();
        }
    }
}
