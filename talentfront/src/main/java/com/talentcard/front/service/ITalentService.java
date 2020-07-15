package com.talentcard.front.service;

import com.alibaba.fastjson.JSONObject;
import com.talentcard.common.pojo.TalentPO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.common.vo.TalentTypeVO;
import com.talentcard.front.dto.IdentificationDTO;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author：chenXU
 * @Date: Created in 2020/04/10 09:10
 * @Description: 人才用的sevice层接口
 */
public interface ITalentService {
    /**
     * 根据和openId，返回当前状态和卡号
     *
     * @param openId
     * @return
     */
    ResultVO<TalentPO> findStatus(String openId);

    /**
     * 注册
     *
     * @param jsonObject
     * @return
     */
    ResultVO register(JSONObject jsonObject);

    /**
     * 根据OpenId查找认证完成之前的基本信息
     * 或者认证之后的信息
     *
     * @param openId
     * @return
     */
    ResultVO findInfo(String openId);

    /**
     * 认证
     * @param identificationDTO
     * @return
     */
    ResultVO identification(IdentificationDTO identificationDTO);


    /**
     * 获取微信用户信息，类型，会员卡号等
     *
     * @param openId
     * @return
     */
    TalentTypeVO getTalentInfo(String openId);

    /**
     * 填充 Union Id
     *
     * @return
     */
    ResultVO fillUnion();


    ResultVO updateUnionId(String token, String openId);

    /**
     * 清除用户缓存信息
     * @param openId
     */
    void clearRedisCache(String openId);
}
