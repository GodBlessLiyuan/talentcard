package com.talentcard.common.constant;



/**
 * @author: velve
 * @date: Created in 2020/6/8 14:10
 * @description: 存储到redis中的key值
 * @version: 1.0
 */
public class TalentConstant {
    /**
     * 用户足迹
     */
    public static final String TALENT_FOOTPIRNT = "footprint";
    /**
     * 用户收藏
     */
    public static final String TALENT_MYCOLLECT = "MyCollect";

    public static final String DEFAULT_TALENT_OPENID = "000000000000000";

    /**
     * 用户有效的旅游次数
     */
    public static final String TALENT_AVAILABLE = "trip_available";
    /**
     * banner信息
     */
    public static final String BANNER_INFO ="banner_info";
    /**
     * 判断是否为默认游客账号
     * @param openId
     * @return
     */
    public static boolean isDefaultTalent(String openId){
        if(openId == null || openId.length()==0 || DEFAULT_TALENT_OPENID.equals(openId)){
            return true;
        } else {
            return false;
        }
    }
}
