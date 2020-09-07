package com.bsnsapi.fabric.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description:
 * @author: xiahui
 * @data 2020-09-03 15:01
 */
@Data
public class ResultVO<T> implements Serializable {
    private Integer status;

    private T data;

    public ResultVO(){
        this(1000);
    }

    public ResultVO(Integer status) {
        this(status, null);
    }

    public ResultVO(Integer status, T data) {
        this.status = status;
        this.data = data;
    }
}
