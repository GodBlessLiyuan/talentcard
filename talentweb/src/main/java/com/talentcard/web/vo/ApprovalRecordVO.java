package com.talentcard.web.vo;

import com.talentcard.common.bo.EvFrontendEventBO;
import com.talentcard.common.pojo.EvFrontendEventApprovalPO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ Author     ：AnHongxu.
 * @ Date       ：Created in 15:26 2020/9/3
 * @ Description：审批记录VO
 * @ Modified By：
 * @ Version:     1.0
 */
@Data
public class ApprovalRecordVO extends EvFrontendEventApprovalPO implements Serializable {
    //活动类型转换为字符串前端展示
    String typeString;

    //审批结果转换为字符串前端展示
    String resultString;

    /**
     * pos 转 vos
     *
     * @param pos
     * @return
     */
    public static List<ApprovalRecordVO> convert(List<EvFrontendEventApprovalPO> pos) {
        List<ApprovalRecordVO> vos = new ArrayList<>();
        for (EvFrontendEventApprovalPO po : pos) {
            vos.add(ApprovalRecordVO.convert(po));
        }
        return vos;
    }

    public static ApprovalRecordVO convert(EvFrontendEventApprovalPO po) {
        ApprovalRecordVO vo = new ApprovalRecordVO();
        vo.setApprovalId(po.getApprovalId());
        vo.setFeId(po.getFeId());
        vo.setUserId(po.getUserId());
        vo.setUsername(po.getUsername());
        vo.setCreateTime(po.getCreateTime());
        vo.setType((po.getType()));
        if (po.getType() != null) {
            if (po.getType() == 1) {
                vo.setTypeString("提交申请");
            }
            if (po.getType() == 2) {
                vo.setTypeString("活动审批");
            }
            if (po.getType() == 1) {
                vo.setTypeString("活动取消");
            }
        }
        vo.setUpdateTime(po.getUpdateTime());
        vo.setResult(po.getResult());
        if (po.getResult() != null) {
            if (po.getResult() == 1) {
                vo.setResultString("通过");
            }
            if (po.getResult() == 2) {
                vo.setResultString("驳回");
            }
        }
            vo.setOpinion(po.getOpinion());
            return vo;
        }
}
