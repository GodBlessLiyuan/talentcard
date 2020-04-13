package com.talentcard.front.service;

import com.alibaba.fastjson.JSONObject;
import com.talentcard.common.pojo.TalentPO;
import com.talentcard.common.vo.ResultVO;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.transform.Result;
import java.util.HashMap;

/**
 * @Author：chenXU
 * @Date: Created in 2020/04/10 09:10
 * @Description: 人才用的sevice层接口
 */
public interface ITalentService {
    ResultVO<TalentPO> findStatus(String openId);

    /**
     * 注册
     *
     * @param jsonObject
     * @return
     */
    ResultVO register(JSONObject jsonObject);

    /**
     * 根据对应条件查找一个
     *
     * @param hashMap
     * @return
     */
    ResultVO findOne(HashMap<String, Object> hashMap);

    /**
     * @param openId
     * @param political
     * @param education
     * @param school
     * @param firstClass
     * @param major
     * @param profQualityCategory
     * @param profQualityInfo
     * @param profTitleCategory
     * @param profTitleInfo
     * @param educPicture
     * @param profTitlePicture
     * @param profQualityPicture
     * @return
     */
    ResultVO identification(String openId,
                            Byte political,
                            Integer education,
                            String school,
                            Byte firstClass,
                            String major,
                            Integer profQualityCategory,
                            String profQualityInfo,
                            Integer profTitleCategory,
                            String profTitleInfo,
                            MultipartFile educPicture,
                            MultipartFile profTitlePicture,
                            MultipartFile profQualityPicture);

    /**
     * 激活
     * @param openId
     * @param code
     * @return
     */
    ResultVO activate(String openId, String code);
}
