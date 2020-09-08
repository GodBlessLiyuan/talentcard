package com.bsnsapi.fabric.chaincodeEntities;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

/***
 * 4-1 人才数据智能合约方法名称及参数说明
 *  人才数据实体类：
 * */
@Data
public class Profile implements Serializable {
    //Type string `json:"type"`
    //	Id string `json:"id"`
    //	Name string `json:"name"`
    //	Sex string `json:"sex"`
    //	PoliticalStatus string `json:"politicalStatus"`

    public Profile(){}
    public Profile(String type, String id, String name) {
        this.type = type;
        this.id = id;
        this.name = name;
    }

    @JSONField(name = "type")
    String type;

    @JSONField(name = "id")
    String id;

    @JSONField(name = "name")
    String name;

    @JSONField(name = "sex")
    String sex;

    @JSONField(name = "politicalStatus")
    String politicalStatus;
}
