package com.talentcard.wechat.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.talentcard.common.bo.ActivcateBO;
import com.talentcard.common.mapper.*;
import com.talentcard.common.pojo.CertApprovalPO;
import com.talentcard.common.pojo.CertificationPO;
import com.talentcard.common.pojo.TalentPO;
import com.talentcard.common.pojo.UserCurrentInfoPO;
import com.talentcard.common.utils.WechatApiUtil;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.wechat.service.ICardActivateService;
import com.talentcard.wechat.utils.AccessTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;


@EnableTransactionManagement
@Service
public class CardActivateServiceImpl implements ICardActivateService {
    @Autowired
    private TalentMapper talentMapper;
    @Autowired
    CertificationMapper certificationMapper;
    @Autowired
    EducationMapper educationMapper;
    @Autowired
    ProfTitleMapper profTitleMapper;
    @Autowired
    ProfQualityMapper profQualityMapper;
    @Autowired
    UserCardMapper userCardMapper;
    @Autowired
    CertApprovalMapper certApprovalMapper;
    @Autowired
    UserCurrentInfoMapper userCurrentInfoMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO activate(String openId) {
        //判断是第一次激活还是第二次激活
        ActivcateBO firstActivate = talentMapper.activate(openId, (byte) 2);
        //0代表没有认证表status=1的数据，说明没有正在使用的卡，即第一次激活注册
        if (firstActivate != null) {
            //第一次激活
            Long certId = firstActivate.getCertId();
            //更新认证表
            CertificationPO certificationPO = certificationMapper.selectByPrimaryKey(certId);
            //certification表和三表，更新status=1:正在使用
            Byte status = 1;
            certificationPO.setStatus(status);
            certificationMapper.updateByPrimaryKeySelective(certificationPO);
            //更新三表
            educationMapper.updateStatusByCertId(certId, status);
            profTitleMapper.updateStatusByCertId(certId, status);
            profQualityMapper.updateStatusByCertId(certId, status);
            //user_card更新（查询status=1的卡，改为status=2）（之前待使用的旧卡变为正在使用）
            userCardMapper.updateStatusById(firstActivate.getTalentId(), (byte) 1, (byte) 2);
        } else {
            //第二次激活
            ActivcateBO activateInfo = talentMapper.activate(openId, (byte) 4);
            //1.cert_approval里的人才类别信息更新user_current_info表
            CertApprovalPO certApprovalPO = certApprovalMapper.findByCertId(activateInfo.getCertId());
            UserCurrentInfoPO userCurrentInfoPO = userCurrentInfoMapper.selectByPrimaryKey(activateInfo.getUciId());
            userCurrentInfoPO.setTalentCategory(certApprovalPO.getCategory());
            userCurrentInfoMapper.updateByPrimaryKeySelective(userCurrentInfoPO);
            //2.certification表和三表，更新status=1:正在使用
            Long certId = activateInfo.getCertId();
            CertificationPO certificationPO = certificationMapper.selectByPrimaryKey(certId);
            Byte status = 1;
            certificationPO.setStatus(status);
            certificationMapper.updateByPrimaryKeySelective(certificationPO);
            educationMapper.updateStatusByCertId(certId, status);
            profTitleMapper.updateStatusByCertId(certId, status);
            profQualityMapper.updateStatusByCertId(certId, status);
            //3.user_card更新（查询status=2的卡，改为status=3）（之前正在使用的旧卡变为废弃）
            userCardMapper.updateStatusById(activateInfo.getTalentId(), (byte) 2, (byte) 3);
            //4.user_card更新（查询status=1的卡，改为status=2）（之前待使用的旧卡变为正在使用）
            userCardMapper.updateStatusById(activateInfo.getTalentId(), (byte) 1, (byte) 2);
            //更新talent表status从2变为1，意为已认证
            TalentPO talentPO = talentMapper.selectByPrimaryKey(activateInfo.getTalentId());
            talentPO.setStatus((byte) 1);
            talentMapper.updateByPrimaryKeySelective(talentPO);
            //设置旧卡券失效
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", activateInfo.getCode());
            jsonObject.put("card_id", activateInfo.getCardId());
            String url = "https://api.weixin.qq.com/card/code/unavailable?access_token="
                    + AccessTokenUtil.getAccessToken();
            JSONObject result = WechatApiUtil.postRequest(url, jsonObject);
            if (result.getInteger("errcode") != 0) {
                return new ResultVO(2330, "激活失败");
            }
        }
        return new ResultVO(1000);
    }
}
