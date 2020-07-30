package com.talentcard.web.service.impl;

import com.alibaba.fastjson.JSON;
import com.talentcard.common.constant.ConfigConst;
import com.talentcard.common.mapper.ConfigMapper;
import com.talentcard.common.mapper.TalentMapper;
import com.talentcard.common.mapper.TalentUnConfirmSendMapper;
import com.talentcard.common.pojo.ConfigPO;
import com.talentcard.common.pojo.TalentPO;
import com.talentcard.common.pojo.TalentUnConfirmSendPO;
import com.talentcard.common.utils.DateUtil;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.dto.MessageDTO;
import com.talentcard.web.utils.MessageUtil;
import com.talentcard.web.utils.WebParameterUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @Description:人才认证的每天发送消息通知的service
 * @author: liyuan
 * @data 2020-07-29 19:03
 */
@Service
public class TalentCertifDaySendService {
    @Autowired
    private TalentMapper talentMapper;
    @Autowired
    private ConfigMapper configMapper;
    @Autowired
    private TalentUnConfirmSendMapper talentUnConfirmSendMapper;
    @Value("${talent_uncertification.sendIncr}")
    private Integer SendIncr;//发送增量
    private static final Logger logger= LoggerFactory.getLogger(TalentCertifDaySendService.class);
   /***
    * 1：查询源数据，在一个断点id之后查询两天外的，搬到目标数据；
    * 目标数据按照没有被发送（已经包含发送失败的）（发送成功修改状态标志位）\
    * 不断循环发送，目标数据好像链条一样，不断地发送和更新状态；
    *  这个定时器在晚8点-晚10点的每10分钟执行一次。
    *  2：configPO的value是比较源数据的主键，update_time比较目标数据的，
    *  3：采取另外的思路是不断查源数据，发消息，写到数据库，最后晚10点是将目标数据没发的发一遍；（应该是对的，这里不再使用）
    * */
    @Scheduled(cron = "${talent_certification.dayTime}")
    @Transactional
    public ResultVO daySend() {
        ConfigPO configPO = configMapper.selectByPrimaryKey(ConfigConst.UnTalent_ID);
        if (configPO == null) {
            logger.info("t_config表还未构建数据");
            return new ResultVO(2000, "t_config表还未构建数据");
        }
        Calendar calendar = Calendar.getInstance();
        //源数据只搬运一次，如果更新时间是今天，则代表源数据搬运完成。这里代码只取年月日，首先判断更新时间小于当前
        if (DateUtil.date2Str(configPO.getUpdateTime(), DateUtil.YMD).compareTo(DateUtil.date2Str(calendar.getTime(), DateUtil.YMD)) < 0) {
            //构造两天前的数据
            calendar.add(Calendar.DAY_OF_MONTH, -2);
            List<TalentUnConfirmSendPO> originTalents = talentMapper.queryByBreakIDAndTime(Long.parseLong(configPO.getConfigValue()), DateUtil.date2Str(calendar.getTime(), DateUtil.YMD_HMS), (byte) 2);
            if (originTalents == null || originTalents.size() == 0) {
                logger.info("t_talent表没有人才未认证的数据");
                return new ResultVO(1000, "t_talent表没有人才未认证的数据");
            }
            configPO.setConfigValue(originTalents.get(originTalents.size() - 1).getTalentId().toString());
            configPO.setUpdateTime(new Date());
            configMapper.updateByPrimaryKey(configPO);//更新状态
            talentUnConfirmSendMapper.batchInsert(originTalents);
            logger.info("{}:未认证人才新增了{}条数据到表:t_talent_un_confirm_send",DateUtil.date2Str(new Date(),DateUtil.YMD_HMS),originTalents.size());
            return new ResultVO(1000,"未认证人才新增了数据条数："+originTalents.size());
        }
        /**
         * 执行不断发送的操作，（最初的一段时间不再用于发送了），
         * 增量式发送
         * */
        List<TalentUnConfirmSendPO> sendingPOS = talentUnConfirmSendMapper.getUnSend((byte) 2, SendIncr);
        if (sendingPOS == null || sendingPOS.size() == 0) {
            logger.info("t_talent_un_confirm_send表的数据都发完了");
            return new ResultVO(1000, "t_talent_un_confirm_send表的数据都发完了");
        }
        //构造目标，发送数据
        MessageDTO messageDTO = new MessageDTO();
        for (TalentUnConfirmSendPO sendingPO : sendingPOS) {
            messageDTO.setOpenid(sendingPO.getOpenId());
            TalentPO talentPO = talentMapper.selectByPrimaryKey(sendingPO.getTalentId());
            messageDTO.setFirst("您好，您还未进行人才认证");
            messageDTO.setKeyword1("申请人姓名：" + talentPO.getName());
            messageDTO.setKeyword2("所属单位：" + talentPO.getWorkUnit());
            messageDTO.setRemark("点击前往认证，认证后可享受多项人才权益哦");
            messageDTO.setTemplateId(4);
            messageDTO.setUrl(WebParameterUtil.getIndexUrl());//很多都是这个url
            String s = MessageUtil.sendTemplateMessage(messageDTO);
            logger.info(s);
//            String s="success";//测试使用
            //发送成功
            if (!StringUtils.isEmpty(s)&& (Integer) JSON.parseObject(s).get("errcode")==0) {
                sendingPO.setStatus((byte) 1);//已发
                talentUnConfirmSendMapper.updateStatusAndUpdateTime(sendingPO);
                logger.debug("发送给{}成功",sendingPO.getOpenId());
            }else{
                logger.error("发送给{}失败",sendingPO.getOpenId());
            }

        }
        logger.info("{}:发送了{}条数据",DateUtil.date2Str(new Date(),DateUtil.YMD_HMS),sendingPOS.size());
        return new ResultVO(1000,"本次发送的记录条数"+sendingPOS.size());
    }

}
