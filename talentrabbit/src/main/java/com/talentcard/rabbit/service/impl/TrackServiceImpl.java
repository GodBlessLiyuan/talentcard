package com.talentcard.rabbit.service.impl;

import com.talentcard.rabbit.mapper.TrackMapper;
import com.talentcard.rabbit.service.ITrackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: xiahui
 * @date: Created in 2020/10/13 10:29
 * @description: TODO
 * @version: 1.0
 */
@Service
public class TrackServiceImpl implements ITrackService {
    private static final Logger logger = LoggerFactory.getLogger(TrackServiceImpl.class);

    @Autowired
    private TrackMapper trackMapper;

    @Override
    public void track(Object data) {
        logger.info("start record track !");
        logger.info((String) data);
    }
}
