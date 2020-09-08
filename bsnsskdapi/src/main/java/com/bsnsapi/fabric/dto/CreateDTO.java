package com.bsnsapi.fabric.dto;

import lombok.Data;


import java.io.Serializable;

/**
 * @Description: 该dto实际上是Config类、ReqKeyEscrow类和Ticket类
 * @author: liyuan
 * @data 2020-09-03 15:31
 */
@Data
public class CreateDTO implements Serializable {
    /***
     {"ticket":{},
     "reqKeyEscrow":{}}
     * */
    //票；入场券，标签；
    private String ticket;
    /***
     有三中这种类，reqkey.setArgs(args);
     */
    private String reqKeyEscrow;
}
