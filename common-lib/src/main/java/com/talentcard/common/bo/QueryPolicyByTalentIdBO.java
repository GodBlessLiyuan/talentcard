package com.talentcard.common.bo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-08-19 11:10
 * @description
 */
@Data
public class QueryPolicyByTalentIdBO {
    //政策id
    private Long policyId;
    //政策名称
    private String policyName;
    //政策活动开始时间
    private Date startTime;
    //政策活动结束时间
    private Date endTime;
    //状态 0：未申请；1：已同意；2：已驳回；3：待审批；10：不可申请（互斥申请政策存在）
    private Byte status;
    //实际状态 1可申请；2未开启；3已失效 5:审批中；10不可申请
    private Byte actualStatus;
    //可申请
    public static final Byte ENABLE_APPLY = 1;
    //未开启
    public static final Byte WAIT_START = 2;
    //已失效
    public static final Byte EXPIRE = 3;
    //待审批
    public static final Byte WAIT_APPROVAL = 5;
    //不可申请
    public static final Byte UNABLE_APPLY = 10;
    public static List<QueryPolicyByTalentIdBO> setValueActualStatus(List<QueryPolicyByTalentIdBO> queryPolicyByTalentIdBOList) {
        Long current = System.currentTimeMillis();
        Long start;
        Long end;
        Byte status;
        for (QueryPolicyByTalentIdBO queryPolicyByTalentIdBO : queryPolicyByTalentIdBOList) {
            if (queryPolicyByTalentIdBO.getStatus() == null) {
                continue;
            }
            status = queryPolicyByTalentIdBO.getStatus();
            if (status == 0) {
                if (queryPolicyByTalentIdBO.getStartTime() == null || queryPolicyByTalentIdBO.getEndTime() == null) {
                    continue;
                }
                start = queryPolicyByTalentIdBO.getStartTime().getTime();
                end = queryPolicyByTalentIdBO.getEndTime().getTime();
                if (current >= end) {
                    //已失效
                    queryPolicyByTalentIdBO.setActualStatus(QueryPolicyByTalentIdBO.EXPIRE);
                } else if (current <= start) {
                    //未开启
                    queryPolicyByTalentIdBO.setActualStatus(QueryPolicyByTalentIdBO.WAIT_START);
                } else if (current >= start && current <= end) {
                    //可申请
                    queryPolicyByTalentIdBO.setActualStatus(QueryPolicyByTalentIdBO.ENABLE_APPLY);
                }
                //待审批
            } else if (status == 3) {
                queryPolicyByTalentIdBO.setActualStatus(QueryPolicyByTalentIdBO.WAIT_APPROVAL);
                //无法申请
            } else if (status == 10) {
                queryPolicyByTalentIdBO.setActualStatus(QueryPolicyByTalentIdBO.UNABLE_APPLY);
            }

        }
        return queryPolicyByTalentIdBOList;
    }

}
