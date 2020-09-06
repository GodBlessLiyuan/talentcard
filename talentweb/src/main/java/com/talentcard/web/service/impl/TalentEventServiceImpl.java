package com.talentcard.web.service.impl;

import com.github.pagehelper.Page;
import com.talentcard.common.bo.EvEventQueryBO;
import com.talentcard.common.constant.EventConstant;
import com.talentcard.common.mapper.EvEventQueryMapper;
import com.talentcard.common.mapper.EvEventTalentMapper;
import com.talentcard.common.utils.DateUtil;
import com.talentcard.common.utils.PageHelper;
import com.talentcard.common.vo.PageInfoVO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.ITalentEventService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-08-31 14:51
 * @description
 */
@Service
public class TalentEventServiceImpl implements ITalentEventService {
    @Autowired
    EvEventQueryMapper evEventQueryMapper;
    @Autowired
    EvEventTalentMapper evEventTalentMapper;

    @Override
    public ResultVO query(Integer pageNum, Integer pageSize, String name, Byte type, Byte status) {
        String currentTime = DateUtil.date2Str(new Date(), DateUtil.YMD_HMS);
        Page<EvEventQueryBO> page = PageHelper.startPage(pageNum, pageSize);
        List<EvEventQueryBO> evEventQueryBOList = evEventQueryMapper.query(name, type, status, currentTime);
        evEventQueryBOList = setQueryStatus(evEventQueryBOList);
        return new ResultVO<>(1000, new PageInfoVO<>(page.getTotal(), evEventQueryBOList));
    }

    @Override
    public ResultVO countNum(Long eventId) {
        Integer num = evEventTalentMapper.countByEventId(eventId);
        return new ResultVO(1000, num);
    }

    /**
     * 设定里面的状态
     *
     * @param evEventQueryBOList
     * @return
     */
    public List<EvEventQueryBO> setQueryStatus(List<EvEventQueryBO> evEventQueryBOList) {
        Long currentTime = System.currentTimeMillis();
        Long startTime;
        Long endTime;
        Byte status;
        Byte type;
        Byte upDown;
        for (EvEventQueryBO evEventQueryBO : evEventQueryBOList) {
            startTime = evEventQueryBO.getStartTime().getTime();
            endTime = evEventQueryBO.getEndTime().getTime();
            type = evEventQueryBO.getType();
            status = evEventQueryBO.getStatus();
            upDown = evEventQueryBO.getUpDown();

            evEventQueryBO.setStatus((byte)EventConstant.getStatus(currentTime, startTime, endTime, status, upDown));
            evEventQueryBO.setActualStatus(evEventQueryBO.getStatus());

            //改变日期
            if (!StringUtils.isEmpty(evEventQueryBO.getDate())) {
                /**
                 * yyyy-MM-dd转换成yyyy/MM/dd
                 */
                Date date = DateUtil.str2Date(evEventQueryBO.getDate(), DateUtil.YMD);
                String s_date = DateUtil.date2Str(date, DateUtil.YMD_);
                evEventQueryBO.setDate(s_date);
            }
        }
        return evEventQueryBOList;
    }

}
