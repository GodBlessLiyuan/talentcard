package com.talentcard.web.service;

import com.talentcard.common.vo.PageInfoVO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.vo.TalentVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: xiahui
 * @date: Created in 2020/4/16 9:15
 * @description: 人才管理
 * @version: 1.0
 */
public interface ITalentService {
    /**
     * 查询
     *
     * @param pageNum
     * @param pageSize
     * @param reqMap
     * @return
     */
    ResultVO query(int pageNum, int pageSize, Map<String, Object> reqMap);

    /**
     * 查看详情
     *
     * @param tid
     * @return
     */
    ResultVO detail(Long tid);

    /**
     * 产看认证人才
     *
     * @param pageNum
     * @param pageSize
     * @param reqMap
     * @return
     */
    ResultVO queryCert(int pageNum, int pageSize, Map<String, Object> reqMap);

    ResultVO edit(Long talentId, Long cardId);

    /**
     * 批量认证
     *
     * @param file
     * @return
     */
    ResultVO batchCertificate(Long cardId, String talentCategory, Long talentHonour, MultipartFile file);

    /**
     * 清理用户缓存
     * @param openId
     */
    void clearRedisCache(String openId);

    ResultVO findBatchCertificate(int pageNum, int pageSize, HashMap<String, Object> hashMap);
}
