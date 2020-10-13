package com.talentcard.rabbit.service.impl;

import com.talentcard.rabbit.mapper.TrackMapper;
import com.talentcard.rabbit.pojo.TrackPO;
import com.talentcard.rabbit.service.ITrackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * @author: xiahui
 * @date: Created in 2020/10/13 10:29
 * @description: 追踪信息
 * @version: 1.0
 */
@Service
public class TrackServiceImpl implements ITrackService {
    private static final Logger logger = LoggerFactory.getLogger(TrackServiceImpl.class);

    @Autowired
    private TrackMapper trackMapper;

    @Override
    public void track(Map<String, Object> data) {
        logger.info("start track record !");
        // 追踪信息
        TrackPO trackPO = new TrackPO();
        trackPO.setTrackType((Byte) data.get("type"));
        trackPO.setTrackNode((Byte) data.get("node"));
        trackPO.setTrackContent((String) data.get("content"));
        trackPO.setTrackTime((Date) data.get("time"));
        trackMapper.insert(trackPO);
    }
}
