package com.talentcard.web.service;

import com.talentcard.common.vo.PageInfoVO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.dto.PolicyDTO;
import com.talentcard.web.vo.PolicyVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: xiahui
 * @date: Created in 2020/4/14 14:22
 * @description: 人才政策管理
 * @version: 1.0
 */
public interface IPolicyService {
    /**
     * 查询
     *
     * @param pageNum
     * @param pageSize
     * @param hashMap
     * @return
     */
    ResultVO query(int pageNum, int pageSize, HashMap<String, Object> hashMap);

    /**
     * 插入
     *
     * @param session
     * @param dto
     * @return
     */
    ResultVO insert(HttpSession session, PolicyDTO dto);

    /**
     * 更新
     *
     * @param session
     * @param dto
     * @return
     */
    ResultVO update(HttpSession session, PolicyDTO dto);

    /**
     * 删除
     *
     * @param reqData
     * @return
     */
    ResultVO delete(HttpSession session,Map<String, Object> reqData);

    /**
     * 查看详情
     *
     * @param pid
     * @return
     */
    ResultVO detail(Long pid);

    /**
     * 上传文件
     *
     * @param file
     * @return
     */
    ResultVO upload(HttpSession session, MultipartFile file);

    /**
     * 上下架政策
     *
     * @param session
     * @param policyId
     * @return
     */
    ResultVO upDown(HttpSession session, Long policyId, Byte upDown);
}
