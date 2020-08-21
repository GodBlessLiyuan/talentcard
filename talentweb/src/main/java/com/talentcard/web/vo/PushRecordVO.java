package com.talentcard.web.vo;

import com.talentcard.common.bo.PoComplianceBO;
import com.talentcard.common.pojo.OpSendmessagePO;
import com.talentcard.common.pojo.PoStatisticsPO;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ Author     ：AnHongxu.
 * @ Date       ：Created in 15:08 2020/8/21
 * @ Description：一键推送消息列表查询
 * @ Modified By：
 * @ Version:     1.0
 */
@Slf4j
@Data
public class PushRecordVO implements Serializable {

    /**
     * 人才政策id
     */

    private Long pid;

    /**
     * 用户Id
     */

    private Long uid;

    /**
     * 用户名
     */

    private String uname;

    /**
     * 成功推送量
     */

    private Long success;

    /**
     * 失败推送量
     */

    private Long failure;

    /**
     * 创建时间
     */
    private Date ctime;

    /**
     * po 转 vo
     *
     * @param po
     * @return
     */

    /**
     * pos 转 vos
     *
     * @param pos
     * @return
     */

    public static List<PushRecordVO> convert(List<OpSendmessagePO> pos) {
        List<PushRecordVO> vos = new ArrayList<>();
        for (OpSendmessagePO po : pos) {
            vos.add(PushRecordVO.convert(po));
        }
        return vos;
    }

    public static PushRecordVO convert(OpSendmessagePO po) {
        PushRecordVO vo = new PushRecordVO();
        vo.setPid(po.getPolicyId());
        vo.setUid(po.getUserId());
        vo.setSuccess(po.getSuccess());
        vo.setFailure(po.getFailure());
        vo.setUname(po.getUsername());
        vo.setCtime(po.getCreateTime());
        return vo;
    }
}

