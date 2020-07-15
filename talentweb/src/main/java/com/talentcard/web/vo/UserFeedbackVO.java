package com.talentcard.web.vo;

import com.talentcard.common.pojo.UserFeedbackPO;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @author: liyuan
 * @data 2020-07-14 20:03
 */
@Data
public class UserFeedbackVO {
    public static List<UserFeedbackVO> convert(List<UserFeedbackPO> pos) {
        if(pos==null||pos.size()==0){
            return null;
        }
        List<UserFeedbackVO> list=new ArrayList<>(pos.size());
        for(UserFeedbackPO po:pos){
            list.add(UserFeedbackVO.convertPO(po));
        }
        return list;
    }

    private static UserFeedbackVO convertPO(UserFeedbackPO po) {
        if(po==null){
            return null;
        }
        UserFeedbackVO userFeedbackVO=new UserFeedbackVO();
        userFeedbackVO.setPageType(po.getPageType());
        userFeedbackVO.setPageItem(po.getPageType());
        userFeedbackVO.setRelateItem(po.getRelateItem());
        userFeedbackVO.setProDescribe(po.getProDescribe());
        userFeedbackVO.setSubmitDate(po.getSubmitDate());
        return userFeedbackVO;
    }
    private Byte pageType;
    private Byte pageItem;
    private Byte relateItem;
    private String proDescribe;
    private Date submitDate;

}
