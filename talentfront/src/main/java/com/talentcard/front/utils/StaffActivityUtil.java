package com.talentcard.front.utils;

import com.talentcard.common.mapper.ScenicMapper;
import com.talentcard.common.pojo.ScenicPO;
import com.talentcard.common.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-05-12 14:51
 * @description 特殊福利活动，员工模块的工具类
 * 获取二级活动名字
 */
@Component
public class StaffActivityUtil {

    @Autowired
    private ScenicMapper scMapper;
    private static ScenicMapper scenicMapper;

    /**
     * 构造方法执行后的初始化
     */
    @PostConstruct
    public void StaffActivityUtilInitialize() {
        scenicMapper = scMapper;
    }

    public static String getActivitySecondContentName(Long activityFirstContentId,
                                                      Long activitySecondContentId) {
        String activitySecondContentName = "";
        /**
         * 旅游
         */
        if (activityFirstContentId == 1) {
            ScenicPO scenicPO = scenicMapper.selectByPrimaryKey(activitySecondContentId);
            if (scenicPO != null) {
                activitySecondContentName = scenicPO.getName();
            }
            /**
             * 农家乐
             */
        } else if (activityFirstContentId == 2) {

            /**
             * 未来活动1
             */
        } else if (activityFirstContentId == 3) {
            /**
             * 未来活动2
             */
        } else if (activityFirstContentId == 4) {

        }
        return activitySecondContentName;

    }
}
