package com.talentcard.common.bo;

import com.talentcard.common.pojo.EvFrontendEventPO;
import lombok.Data;

/**
 * @ Author     ：AnHongxu.
 * @ Date       ：Created in 9:28 2020/8/29
 * @ Description：我发起的活动查询业务类
 * @ Modified By：
 * @ Version:     1.0
 */
@Data
public class EvFrontendEventBO extends EvFrontendEventPO {
    //驳回意见
    private String rejectOpinion;
}
