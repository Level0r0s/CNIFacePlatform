package cn.abellee.cniface.platform.exception;

import cn.abellee.cniface.platform.domain.common.CNIFaceResponse;
import cn.abellee.cniface.platform.domain.common.CNIFaceResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Objects;

/**
 * @author abel
 * @date 2022/6/5 5:59 PM
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CNIFaceResponse<?> throwCustomException(MethodArgumentNotValidException methodArgumentNotValidException){
        log.error("[ @请求参数异常捕获 ] " + methodArgumentNotValidException.getMessage());
        return CNIFaceResponse.error(CNIFaceResponseCode.VALID_ERROR, Objects.requireNonNull(methodArgumentNotValidException.getBindingResult().getFieldError()).getDefaultMessage());
    }

    @ResponseBody
    @ExceptionHandler(CNIFaceException.class)
    public CNIFaceResponse<?> throwCustomException(CNIFaceException cnifaceException){
        log.error("[ @系统错误 ] " + cnifaceException.getMessage());
        return CNIFaceResponse.error(cnifaceException.getCNIFaceResponseCode(), cnifaceException.getMessage());
    }
}
