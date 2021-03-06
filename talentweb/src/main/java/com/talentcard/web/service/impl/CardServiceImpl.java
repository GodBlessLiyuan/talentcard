package com.talentcard.web.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.talentcard.common.config.FilePathConfig;
import com.talentcard.common.mapper.CardMapper;
import com.talentcard.common.mapper.OpwebRecordMapper;
import com.talentcard.common.mapper.PolicyMapper;
import com.talentcard.common.mapper.UserMapper;
import com.talentcard.common.pojo.CardPO;
import com.talentcard.common.pojo.PolicyPO;
import com.talentcard.common.utils.FileUtil;
import com.talentcard.common.utils.WechatApiUtil;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.constant.OpsRecordMenuConstant;
import com.talentcard.web.service.ICardService;
import com.talentcard.web.service.ILogService;
import com.talentcard.web.utils.AccessTokenUtil;
import com.talentcard.web.utils.CardUtil;
import com.talentcard.web.vo.CardIdAndNameVO;
import com.talentcard.web.vo.CardVO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


@Service
@EnableTransactionManagement
public class CardServiceImpl implements ICardService {
    private static final Logger logger = LoggerFactory.getLogger(CardServiceImpl.class);

    @Autowired
    private FilePathConfig filePathConfig;
    @Value("${wechat.logoUrl}")
    private String logoUrl;
    @Autowired
    private CardMapper cardMapper;
    @Autowired
    private PolicyMapper policyMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private OpwebRecordMapper opwebRecordMapper;
    @Autowired
    private ILogService logService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO add(String name, String title, String notice,
                        String description, String prerogative, MultipartFile background,
                        String initialWord, String areaNum, String businessDescription,
                        Byte status, String color, Integer tripTimes, HttpSession httpSession) {
        //从session中获取userId的值
        Long userId = (Long) httpSession.getAttribute("userId");
        if (userId == null) {
            // 用户过期
            return ResultVO.notLogin();
        }
        //判断是否已经存在该初始字段
        Integer ifExistInitialWord = cardMapper.ifExistInitialWord(initialWord);
        if (ifExistInitialWord != null && ifExistInitialWord != 0) {
            return new ResultVO(2328, "已存在该初始字段");
        }
        //上传背景图片到服务器上
        String picture = FileUtil.uploadFile
                (background, filePathConfig.getLocalBasePath(), filePathConfig.getProjectDir(), filePathConfig.getCardBackgroundDir(), "cardBackground");
//        String pictureUploadCdnUrl = publicPath + picture;
        String pictureUploadCdnUrl = filePathConfig.getLocalBasePath() + picture;
        logger.info("pictureUploadCdnUrl", pictureUploadCdnUrl);
        //服务器到cdn上
        String pictureCDN = CardUtil.uploadPicture(pictureUploadCdnUrl);
        //转为json格式，再转为url，去掉反斜线
        JSONObject pictureObject = JSONObject.parseObject(pictureCDN);
        pictureCDN = pictureObject.getString("url");
        if (pictureCDN == null || pictureCDN.equals("")) {
            return new ResultVO(2321, "会员卡背景图上传失败");
        }
        //上传logo图片
        //公网访问url
        String publicLogoUrl = filePathConfig.getPublicBasePath() + logoUrl;
        //服务器url
        String serverLogoUrl = filePathConfig.getLocalBasePath() + logoUrl;
//        String logoCDN = CardUtil.uploadPicture(publicLogoUrl);
        logger.info("serverLogoUrl: {}", serverLogoUrl);
        //服务器到cdn上
        String logoCDN = CardUtil.uploadPicture(serverLogoUrl);
        //转为json格式，再转为url，去掉反斜线
        JSONObject logoObject = JSONObject.parseObject(logoCDN);
        logoCDN = logoObject.getString("url");
        if (logoCDN == null || logoCDN.equals("")) {
            return new ResultVO(2325, "会员卡logo图CDN上传失败");
        }
        //创建卡，微信端
        CardPO cardPO = new CardPO();
        //status=1为基本卡，否则为高级卡
        JSONObject wechatResult;
        /**
         * 基本卡
         */
        if (status == 1) {
            CardPO defaultCard = cardMapper.findDefaultCard();
            //已存在基本卡，最多1张
            if (defaultCard != null) {
                return new ResultVO(2326, "已存在基础卡");
            }
            wechatResult = CardUtil.addCommonCard(name, title, notice, description, prerogative, pictureCDN, logoCDN, color);
            if ((wechatResult.getInteger("errcode") == null)
                    || (wechatResult.getInteger("errcode") != 0)) {
                return new ResultVO(2320, wechatResult);
            }
        } else {
            /**
             * 高级卡
             */
            wechatResult = CardUtil.addSeniorCard(name, title, notice, description, prerogative, pictureCDN, logoCDN, color);
            if ((wechatResult.getInteger("errcode") == null)
                    || (wechatResult.getInteger("errcode") != 0)) {
                return new ResultVO(2320, wechatResult);
            }
        }
        //创建卡，本地服务端
        cardPO.setName(name);
        cardPO.setTitle(title);
        cardPO.setCurrNum((long) 1);
        cardPO.setDescription(description);
        cardPO.setPicture(picture);
        cardPO.setPictureCdn(pictureCDN);
        cardPO.setPrerogative(prerogative);
        cardPO.setInitialWord(initialWord);
        cardPO.setAreaNum(areaNum);
        cardPO.setCreateTime(new Date());
        cardPO.setStatus(status);
        cardPO.setMemberNum((long) 0);
        cardPO.setWxCardId(wechatResult.getString("card_id"));
        cardPO.setDr((byte) 1);
        cardPO.setLogoUrl(logoUrl);
        cardPO.setWaitingMemberNum((long) 0);
        cardPO.setBusinessDescription(businessDescription);
        //从session里取出创建者信息
        logger.info("session: {}", httpSession);
        String createPerson = (String) httpSession.getAttribute("username");
        logger.info("username: {}", createPerson);
        cardPO.setCreatePerson(createPerson);
        cardPO.setUpdatePerson(createPerson);
        cardPO.setUpdateTime(new Date());
        cardPO.setTripTimes(tripTimes);
        cardMapper.insertSelective(cardPO);
        logService.insertActionRecord(httpSession, OpsRecordMenuConstant.F_CardManager, OpsRecordMenuConstant.S_TalentCardManager,
                "新增人才卡\"%s\"", CardUtil.getCardName(cardPO));

        return new ResultVO(1000, wechatResult);
    }

    @Override
    public ResultVO edit(Long cardId, String title, String businessDescription,
                         MultipartFile background, Integer tripTimes, HttpSession httpSession) {
        //从session中获取userId的值
        Long userId = (Long) httpSession.getAttribute("userId");
        if (userId == null) {
            // 用户过期
            return ResultVO.notLogin();
        }
        if (StringUtils.isEmpty(title)
                && StringUtils.isEmpty(businessDescription)
                && background == null) {
            return new ResultVO(2324, "会员卡编辑失败，啥参数都没给啊");
        }
        CardPO cardPO = cardMapper.selectByPrimaryKey(cardId);
        if (cardPO == null) {
            return new ResultVO(2600);
        }
        //上传背景图片
        String picture = "";
        String pictureCDN = "";
        if (background != null) {
            picture = FileUtil.uploadFile
                    (background, filePathConfig.getLocalBasePath(), filePathConfig.getProjectDir(), filePathConfig.getCardBackgroundDir(), "cardBackground");
//        String pictureUploadCdnUrl = publicPath + picture;
            String pictureUploadCdnUrl = filePathConfig.getLocalBasePath() + picture;
            //背景图上传到cdn上
            pictureCDN = CardUtil.uploadPicture(pictureUploadCdnUrl);
            JSONObject pictureObject = JSONObject.parseObject(pictureCDN);
            pictureCDN = pictureObject.getString("url");
            if (pictureCDN == null || pictureCDN.equals("")) {
                return new ResultVO(2321, "会员卡背景图上传失败");
            }
        }
        //根据cardId找到wx的cardId
        String wxCardId = cardPO.getWxCardId();
        JSONObject paramObject = new JSONObject();
        paramObject.put("card_id", wxCardId);
        JSONObject memberCard = new JSONObject();
        JSONObject baseInfo = new JSONObject();
        /**
         * 根据编辑条件，决定传的json参数
         */
        //背景图
        if (pictureCDN != null && !pictureCDN.equals("")) {
            memberCard.put("background_pic_url", pictureCDN);
            cardPO.setPicture(picture);
            cardPO.setPictureCdn(pictureCDN);
        }
        //卡片标题
        if (title != null && !title.equals("")) {
            baseInfo.put("title", title);
            cardPO.setTitle(title);
        }

        memberCard.put("base_info", baseInfo);
        paramObject.put("member_card", memberCard);

        String url = "https://api.weixin.qq.com/card/update?access_token="
                + AccessTokenUtil.getAccessToken();
        if (!StringUtils.isEmpty(pictureCDN)
                || !StringUtils.isEmpty(title)) {
            JSONObject result = WechatApiUtil.postRequest(url, paramObject);
        }
        //从session里取出更新者信息
        String updatePerson = (String) httpSession.getAttribute("username");
        cardPO.setUpdatePerson(updatePerson);
        cardPO.setUpdateTime(new Date());
        cardPO.setBusinessDescription(businessDescription);
        //旅游次数
        if (tripTimes != null && tripTimes != 0) {
            cardPO.setTripTimes(tripTimes);
        }
        int updateResult = cardMapper.updateByPrimaryKeySelective(cardPO);
        if (updateResult == 0) {
            logger.error("update cardMapper error");
        }
        logService.insertActionRecord(httpSession, OpsRecordMenuConstant.F_CardManager, OpsRecordMenuConstant.S_TalentCardManager,
                "修改人才卡\"%s\"", CardUtil.getCardName(cardPO));

        return new ResultVO(1000);
    }

    @Override
    public ResultVO query(HashMap<String, Object> hashMap) {
        List<CardPO> cardPOList = cardMapper.findByFactor(hashMap);
        //PO转VO
        List<CardVO> cardVOList = CardVO.convert(cardPOList);
        return new ResultVO(1000, cardVOList);
    }

    @Override
    public ResultVO findOne(Long cardId) {
        CardPO cardPO = cardMapper.selectByPrimaryKey(cardId);
        if (cardPO == null) {
            return new ResultVO(2600, "查无此卡！");
        }
        CardVO cardVO = CardVO.convert(cardPO);
        ArrayList<String> policyInfo = new ArrayList<>();
        //取得卡号的String类型
        String cardIdString = cardId.toString();
        //取得全部权益数据
        List<PolicyPO> policyPOList = policyMapper.queryByDr((byte) 1);
        //用逗号拆分，判断权益表的cards是否有当前cardId
        for (PolicyPO policyPO : policyPOList) {
            if (!StringUtils.isEmpty(policyPO.getCards())) {
                String[] policys = policyPO.getCards().split(",");
                for (String policy : policys) {
                    if (cardIdString.equals(policy)) {
                        policyInfo.add(policyPO.getName());
                    }
                }
            }
        }
        //添加功public path
        String pictureUrl = cardPO.getPicture();
        if (pictureUrl != null && pictureUrl != "") {
            cardPO.setPicture(filePathConfig.getPublicBasePath() + pictureUrl);
        }
        cardVO.setPolicyInfo(policyInfo);
        return new ResultVO(1000, cardVO);
    }

    @Override
    public ResultVO delete(Long cardId, HttpSession httpSession) {
        //从session中获取userId的值
        Long userId = (Long) httpSession.getAttribute("userId");
        if (userId == null) {
            // 用户过期
            return ResultVO.notLogin();
        }
        //判断会员卡人数是否为0，0则不可删除
        Integer existMember = cardMapper.findIfExistMember(cardId);
        if (existMember != null && existMember != 0) {
            return new ResultVO(2322, "会员卡人数不为0，删除失败");
        }
        //删除卡服务器
        CardPO cardPO = cardMapper.selectByPrimaryKey(cardId);
        String wxCardId = cardPO.getWxCardId();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("card_id", wxCardId);
        String url = "https://api.weixin.qq.com/card/delete?access_token="
                + AccessTokenUtil.getAccessToken();
        JSONObject result = WechatApiUtil.postRequest(url, jsonObject);

        if (result.getInteger("errcode") != 0) {
            return new ResultVO(2323, "会员卡删除失败，微信服务器原因");
        }
        //删除卡服务端
        cardPO.setDr((byte) 2);
        int updateResult = cardMapper.updateByPrimaryKeySelective(cardPO);
        if (updateResult == 0) {
            logger.error("update cardMapper error");
        }
        logService.insertActionRecord(httpSession, OpsRecordMenuConstant.F_CardManager, OpsRecordMenuConstant.S_TalentCardManager,
                "删除人才卡\"%s\"", CardUtil.getCardName(cardPO));

        return new ResultVO(1000, result);
    }

    @Override
    public ResultVO findSeniorCard(HashMap<String, Object> hashMap) {
        List<CardPO> cardPOList = cardMapper.findSeniorCard(hashMap);
        //PO转VO
        List<CardVO> cardVOList = CardVO.convert(cardPOList);
        return new ResultVO(1000, cardVOList);
    }

    @Override
    public ResultVO queryCardIdName() {
        List<CardPO> pos = cardMapper.queryCardIdName();
        List<CardIdAndNameVO> vo = CardIdAndNameVO.convert(pos);
        return new ResultVO(1000, vo);
    }

}
