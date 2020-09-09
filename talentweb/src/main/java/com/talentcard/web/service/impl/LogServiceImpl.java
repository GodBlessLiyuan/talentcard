package com.talentcard.web.service.impl;

import com.talentcard.common.mapper.OpwebRecordMapper;
import com.talentcard.common.pojo.OpwebRecordPO;
import com.talentcard.web.service.ILogService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * @Description:
 * @author: liyuan
 * @data 2020-07-27 20:44
 */
@Service
public class LogServiceImpl implements ILogService {

    @Resource
    private OpwebRecordMapper opwebRecordMapper;

    @Async("asyncTaskExecutor")
    @Override
    public void insertActionRecord(HttpSession session, String firstMenu, String secondMenu, String detail, String... params) {
        OpwebRecordPO opwebRecordPO = new OpwebRecordPO();
        opwebRecordPO.setUseId((Long) session.getAttribute("userId"));
        opwebRecordPO.setUsername((String) session.getAttribute("username"));
        opwebRecordPO.setFristMenu(firstMenu);
        opwebRecordPO.setSecondMenu(secondMenu);
        if (params != null && params.length > 0) {
            opwebRecordPO.setDetailInfo(String.format(detail, params));
        } else {
            opwebRecordPO.setDetailInfo(detail);
        }

        opwebRecordPO.setCreateTime(new Date());
        opwebRecordMapper.insert(opwebRecordPO);
    }


    @Async("asyncTaskExecutor")
    @Override
    public void insertActionRecord(long userId, String userName, String firstMenu, String secondMenu, String detail, String... params) {
        OpwebRecordPO opwebRecordPO = new OpwebRecordPO();
        opwebRecordPO.setUseId(userId);
        opwebRecordPO.setUsername(userName);
        opwebRecordPO.setFristMenu(firstMenu);
        opwebRecordPO.setSecondMenu(secondMenu);
        if (params != null && params.length > 0) {
            opwebRecordPO.setDetailInfo(String.format(detail, params));
        } else {
            opwebRecordPO.setDetailInfo(detail);
        }

        opwebRecordPO.setCreateTime(new Date());
        opwebRecordMapper.insert(opwebRecordPO);
    }
}
