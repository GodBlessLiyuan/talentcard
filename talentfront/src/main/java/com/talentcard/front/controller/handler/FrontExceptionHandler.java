package com.talentcard.front.controller.handler;

import com.talentcard.common.vo.ResultVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author: xiahui
 * @date: Created in 2020/4/21 9:26
 * @description: 异常处理
 * @version: 1.0
 */
@RestControllerAdvice
public class FrontExceptionHandler {
    private final static Logger logger = LoggerFactory.getLogger(FrontExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    public ResultVO handlerPromptException(Exception e) {
        logger.error("FrontExceptionHandler: ", e);
        return new ResultVO(2000);
    }
}
