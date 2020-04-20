package com.talentcard.web.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.talentcard.common.mapper.CardMapper;
import com.talentcard.common.pojo.CardPO;
import com.talentcard.common.utils.FileUtil;
import com.talentcard.common.utils.WechatApiUtil;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.ICardService;
import com.talentcard.web.utils.AccessTokenUtil;
import com.talentcard.web.utils.CardUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;


@Service
@EnableTransactionManagement
public class CardServiceImpl implements ICardService {
    @Value("${file.publicPath}")
    private String publicPath;
    @Value("${file.rootDir}")
    private String rootDir;
    @Value("${file.projectDir}")
    private String projectDir;
    @Value("${file.cardBackgroundDir}")
    private String cardBackgroundDir;
    @Autowired
    private CardMapper cardMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO add(String name, String title, String notice,
                        String description, String prerogative, MultipartFile background,
                        String initialWord, String initialNumber, Byte status) {
        //上传背景图片
        String picture = FileUtil.uploadFile
                (background, rootDir, projectDir, cardBackgroundDir, "cardBackground");
//        String pictureUploadCdnUrl = publicPath + picture;
        String pictureUploadCdnUrl = rootDir + picture;
        String pictureCDN = CardUtil.uploadPicture(pictureUploadCdnUrl);
        JSONObject jsonObject = JSONObject.parseObject(pictureCDN);
        pictureCDN = jsonObject.getString("url");
        if (pictureCDN == null || pictureCDN == "") {
            return new ResultVO(2321);
        }
        //创建卡，微信端
        //status=1为基本卡，否则为高级卡
        JSONObject wechatResult;
        if (status == 1) {
            wechatResult = CardUtil.addCommonCard(name, title, notice, description, prerogative, pictureCDN);
            if ((wechatResult.getInteger("errcode") == null)
                    || (wechatResult.getInteger("errcode") != 0)) {
                return new ResultVO(2320, wechatResult);
            }
        } else {
            wechatResult = CardUtil.addSeniorCard(name, title, notice, description, prerogative, pictureCDN);
            if ((wechatResult.getInteger("errcode") == null)
                    || (wechatResult.getInteger("errcode") != 0)) {
                return new ResultVO(2320, wechatResult);
            }

        }

        //创建卡，服务端
        CardPO cardPO = new CardPO();
        cardPO.setName(name);
        cardPO.setTitle(title);
        cardPO.setCurrNum(Long.valueOf(initialNumber));
        cardPO.setDescription(description);
        cardPO.setPicture(picture);
        cardPO.setPictureCdn(pictureCDN);
        cardPO.setPrerogative(prerogative);
        cardPO.setInitialWord(initialWord);
        cardPO.setInitialNum(initialNumber);
        cardPO.setCreateTime(new Date());
        cardPO.setStatus(status);
        cardPO.setMemberNum((long) 0);
        cardPO.setWxCardId(wechatResult.getString("card_id"));
        cardPO.setStatus((byte) 1);
        cardMapper.insertSelective(cardPO);
        return new ResultVO(1000, wechatResult);
    }

    @Override
    public ResultVO delete(Long cardId) {
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
        cardMapper.updateByPrimaryKeySelective(cardPO);
        return new ResultVO(1000);
    }
}
