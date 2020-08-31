package com.talentcard.web.service.impl;

import com.github.pagehelper.Page;
import com.talentcard.common.bo.EvEventQueryBO;
import com.talentcard.common.mapper.EvEventQueryMapper;
import com.talentcard.common.utils.DateUtil;
import com.talentcard.common.utils.PageHelper;
import com.talentcard.common.vo.PageInfoVO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.ITalentEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    //已通过；未开始
    public static final Byte APPROVE_NOT_START = 1;
    //报名已结束
    public static final Byte SIGN_UP_END = 2;
    //已结束
    public static final Byte END = 3;
    //已下架
    public static final Byte DOWN = 4;
    //已取消
    public static final Byte CANCEL = 5;
    //报名中
    public static final Byte SIGN_UP_IN_PROGRESS = 6;
    //异常状态
    public static final Byte ERROR_STATUS = 10;

    @Override
    public ResultVO query(Integer pageNum, Integer pageSize, String name, Byte type, Byte status) {
        String currentTime = DateUtil.date2Str(new Date(), DateUtil.YMD_HMS);
        Page<EvEventQueryBO> page = PageHelper.startPage(pageNum, pageSize);
        List<EvEventQueryBO> evEventQueryBOList = evEventQueryMapper.query(name, type, status, currentTime);
        evEventQueryBOList = setQueryStatus(evEventQueryBOList);
        return new ResultVO<>(1000, new PageInfoVO<>(page.getTotal(), evEventQueryBOList));
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
            //前台活动；已通过；未开始
            if (startTime >= currentTime && status == 2 && type == 1) {
                evEventQueryBO.setActualStatus(APPROVE_NOT_START);
                //报名已结束，前台、后台；
            } else if (currentTime >= startTime && currentTime <= endTime
                    && status == 2 && (type == 1 | (type == 2 & upDown == 1))) {
                evEventQueryBO.setActualStatus(SIGN_UP_END);
                //已结束，前台、后台；
            } else if (status == 2 && endTime <= currentTime) {
                evEventQueryBO.setActualStatus(END);
                //已下架，后台；
            } else if (type == 2 && upDown == 2) {
                evEventQueryBO.setActualStatus(DOWN);
                //已取消，前台、后台；
            } else if (status == 4 || status == 5) {
                evEventQueryBO.setActualStatus(CANCEL);
                //报名中，后台；
            } else if (status == 2 && type == 2) {
                evEventQueryBO.setActualStatus(SIGN_UP_IN_PROGRESS);
            } else {
                evEventQueryBO.setActualStatus(ERROR_STATUS);
            }
        }
        return evEventQueryBOList;
    }
}
