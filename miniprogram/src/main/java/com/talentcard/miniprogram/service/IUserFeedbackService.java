package com.talentcard.miniprogram.service;

import com.talentcard.common.vo.ResultVO;
import com.talentcard.miniprogram.dto.UserFeedBackDTO;

public interface IUserFeedbackService {
    ResultVO insert(UserFeedBackDTO userFeedBackDTO);
}
