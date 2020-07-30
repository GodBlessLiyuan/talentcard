package com.talentcard.web.service.impl;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

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
    public ResultVO daySend(){
        /**
         * 如果时间超过22点则退出
         * */
        if(DateUtil.date2Str(new Date(),DateUtil.HM).compareTo("22:00")>0){
            logger.info("超过22点，发送完毕");
            return new ResultVO(1000,"超过22点，发送完毕");
        }
        ConfigPO configPO = configMapper.selectByPrimaryKey(ConfigConst.UnTalent_ID);
        if(configPO==null){
            logger.info("t_config表还未构建数据");
            return new ResultVO(2000,"t_config表还未构建数据");
        }
        Calendar calendar=Calendar.getInstance();
        //源数据只搬运一次，如果更新时间是今天，则代表源数据搬运完成。这里代码只取年月日，首先判断更新时间小于当前
        if(DateUtil.date2Str(configPO.getUpdateTime(),DateUtil.YMD).compareTo(DateUtil.date2Str(calendar.getTime(),DateUtil.YMD))<0){
            //构造两天前的数据
            calendar.add(Calendar.DAY_OF_MONTH,-2);
            List<TalentUnConfirmSendPO> originTalents=talentMapper.queryByBreakIDAndTime(Long.parseLong(configPO.getConfigKey()),DateUtil.date2Str(calendar.getTime(),DateUtil.YMD_HMS));
            if(originTalents==null||originTalents.size()==0){
                logger.info("人才未认证没有数据");
                return new ResultVO(1000,"人才未认证没有数据");
            }
            talentUnConfirmSendMapper.batchInsert(originTalents);
        }else{

        }



    }

}
