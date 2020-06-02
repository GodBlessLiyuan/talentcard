package com.talentcard.common.bo;

import com.talentcard.common.pojo.FeedbackPO;
import lombok.Data;

/**
 * @author: xiahui
 * @date: Created in 2020/6/2 19:28
 * @description: 意见反馈
 * @version: 1.0
 */
@Data
public class FeedbackBO extends FeedbackPO {

    /**
     * 用户姓名
     */
    private String name;
    /**
     * 人才卡号
     */
    private String card;
}
