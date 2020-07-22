package com.talentcard.common.bo;

import lombok.Data;

import java.util.Date;

/**
 * @Description:农家乐的实时查询
 * @author: liyuan
 * @data 2020-07-15 18:12
 */
@Data
public class FarmHouseUserRealTimeBO {
    private Long ttId;//主键
    private Date time;//使用时间
    private String name;//农家乐名称
    private String youkename;//游客名
    private Long cardID;//人才卡
    private String staffname;//验证员工

}
