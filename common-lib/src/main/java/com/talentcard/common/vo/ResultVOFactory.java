package com.talentcard.common.vo;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: velve
 * @date: Created in 2020/5/21 19:50
 * @description: ResultVO定义常量工厂
 * @version: 1.0
 */
public class ResultVOFactory {
    /**
     * 数据返回正常，无异常
     */
    public static final int SUCCESS = 1000;

    public static ResultVO builder(int status){
        return new ResultVO<>(SUCCESS);
    }

    private static Map<Integer,String> errors = new HashMap<>();

    static {
        errors.put(2305, "该openId已被注册");

    }

    public static ResultVO<String> builderError(int status){

        return null;
    }
}
