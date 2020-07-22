package com.talentcard.common.bo;

import lombok.Data;

import java.util.Date;

/**
 * @Description:
 * @author: liyuan
 * @data 2020-07-21 17:02
 */
@Data
public class TripRealTimeBO {
    private Long ID;
    private Date time;
    private String scenicName;//景区名
    private String youkename;//游客名(在人才t_talent表)
    private Long cardId;//可以找卡名
    private Byte welfare;//福利类型
    private Long staffID;//为了查员工姓名
}
