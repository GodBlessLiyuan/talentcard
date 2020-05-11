package com.talentcard.front.vo;

import com.talentcard.common.bo.StaffTripBO;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-05-11 14:34
 * @description 员工端绑定-使用扫描验证
 * 公众号下方：企业服务
 *
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
    private String activityName;

    /**
     * Bo转VO
     *
     * @param staffTripBO
     * @return
     */
    public static StaffVO convert(StaffTripBO staffTripBO) {
        StaffVO staffVO = new StaffVO();
        staffVO.setStaffId(staffTripBO.getStaffId());
        staffVO.setOpenId(staffTripBO.getOpenId());
        staffVO.setStaffName(staffTripBO.getStaffName());
        staffVO.setSex(staffTripBO.getSex());
        staffVO.setPhone(staffTripBO.getPhone());
        staffVO.setActivityFirstContent(staffTripBO.getActivityFirstContent());
        staffVO.setActivitySecondContent(staffTripBO.getActivitySecondContent());
        return staffVO;
    }
}
