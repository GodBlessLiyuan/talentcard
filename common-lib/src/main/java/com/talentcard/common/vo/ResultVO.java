package com.talentcard.common.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ChenXU
 * @version 1.0
 * @date Created in 2020/03/24 15:13
 * @description
 */
@Data
public class ResultVO<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer status;

    private T data;

    public ResultVO(Integer status) {
        this(status, null);
    }

    public ResultVO(Integer status, T data) {
        this.status = status;
        this.data = data;
    }

    public static ResultVO notLogin(){
        return new ResultVO(2104);
    }
}
