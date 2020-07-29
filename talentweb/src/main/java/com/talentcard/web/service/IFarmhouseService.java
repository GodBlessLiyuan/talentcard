package com.talentcard.web.service;

import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.dto.FarmhouseDTO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author: xiahui
 * @date: Created in 2020/5/11 18:42
 * @description: 农家乐
 * @version: 1.0
 */
public interface IFarmhouseService {
    ResultVO query(int pageNum, int pageSize, Map<String, Object> reqMap);

    ResultVO edit(HttpSession session,FarmhouseDTO dto);

    ResultVO status(HttpSession session,Long farmhouseId, Byte status);

    ResultVO detail(Long farmhouseId);

    ResultVO upload(HttpSession session,MultipartFile file);
}
