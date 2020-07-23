package com.talentcard.web.vo;

import lombok.Data;

import java.util.Date;

/**
 * @Description:
 * @author: liyuan
 * @data 2020-07-21 18:37
 */
@Data
public class TripRealTimeVO {
    private Long ID;
    private Date time;
    private String scenicName;//景区名
    private String youkename;//游客名(在人才t_talent表)
    private String welfare;//福利类型
    private String cardname;//卡名
    private String staffname;//员工姓名
}
