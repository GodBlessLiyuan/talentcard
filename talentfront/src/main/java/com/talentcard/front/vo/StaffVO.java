package com.talentcard.front.vo;

import com.talentcard.common.bo.StaffBO;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-05-11 14:34
 * @description 员工端绑定-使用扫描验证
 * 公众号下方：企业服务
 */
@Component
@Data
public class StaffVO {
    private Long staffId;
    private String openId;
    private String staffName;
    private Byte sex;
    private String phone;
    private Long activityFirstContent;
    private Long activitySecondContent;
    private String activitySecondContentName;

    /**
     * Bo转VO
     *
     * @param staffBO
     * @return
     */
    public static StaffVO convert(StaffBO staffBO) {
        StaffVO staffVO = new StaffVO();
        staffVO.setStaffId(staffBO.getStaffId());
        staffVO.setOpenId(staffBO.getOpenId());
        staffVO.setStaffName(staffBO.getStaffName());
        staffVO.setSex(staffBO.getSex());
        staffVO.setPhone(staffBO.getPhone());
        staffVO.setActivityFirstContent(staffBO.getActivityFirstContent());
        staffVO.setActivitySecondContent(staffBO.getActivitySecondContent());
        staffVO.setActivitySecondContentName(staffBO.getActivitySecondContentName());
        return staffVO;
    }
}
