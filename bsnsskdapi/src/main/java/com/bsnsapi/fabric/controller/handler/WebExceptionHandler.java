package com.bsnsapi.fabric.controller.handler;

import com.bsnsapi.fabric.vo.ResultVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author: xiahui
 * @date: Created in 2020/4/21 9:24
 * @description: 异常处理
 * @version: 1.0
 */
@RestControllerAdvice
public class WebExceptionHandler {
    private final static Logger logger = LoggerFactory.getLogger(WebExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    public ResultVO handlerPromptException(Exception e) {
        logger.error("WebExceptionHandler: ", e);
        return new ResultVO(2000);
    }
}
