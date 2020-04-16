package com.talentcard.web.service.impl;
import com.alibaba.fastjson.JSONObject;
import com.talentcard.common.exception.WechatException;
import com.talentcard.common.pojo.CardPO;
import com.talentcard.common.pojo.wechat.create.BaseInfoPO;
import com.talentcard.common.pojo.wechat.create.CustomCell1PO;
import com.talentcard.common.pojo.wechat.create.MemberCardPO;
import com.talentcard.common.pojo.wechat.create.WxCardPO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.ICardService;
import com.talentcard.web.utils.AccessTokenUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Service
@EnableTransactionManagement
public class CardServiceImpl implements ICardService {
    @Override
    public ResultVO add(JSONObject jsonObject) {
        return new ResultVO(1000);
    }
}
