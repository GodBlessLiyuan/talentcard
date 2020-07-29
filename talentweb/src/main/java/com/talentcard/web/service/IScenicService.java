package com.talentcard.web.service;

import com.talentcard.common.pojo.CardPO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.dto.EditTripTimesDTO;
import com.talentcard.web.dto.ScenicDTO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @author: xiahui
 * @date: Created in 2020/5/9 16:04
 * @description: 景区
 * @version: 1.0
 */
public interface IScenicService {
    ResultVO query(int pageNum, int pageSize, Map<String, Object> reqMap);

    ResultVO edit(HttpSession session,ScenicDTO dto);

    ResultVO status(HttpSession session,Long scenicId, Byte status);

    ResultVO detail(Long scenicId);

    ResultVO upload(HttpSession session,MultipartFile file);

    ResultVO setTripTimes(HttpSession session,EditTripTimesDTO editTripTimesDTO);
}
