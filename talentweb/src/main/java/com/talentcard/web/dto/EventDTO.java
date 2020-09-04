package com.talentcard.web.dto;

import com.talentcard.common.pojo.EvEventPO;
import com.talentcard.common.pojo.EvEventQueryPO;
import com.talentcard.common.utils.DateUtil;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-08-27 14:47
 * @description
 */
@Data
@Component
public class EventDTO {
    private Long eventId;
    private String name;
    private String num;
    private String sponsor;
    private String time;
    private Double duration;
    private String date;
    private Long eventField;
    private String detail;
    private String process;
    private String target;
    private String contact;
    private String picture;
    private Long roleId;
    private Byte ifQuota;
    private Integer maleQuota;
    private Integer femaleQuota;
    private Integer eventQuota;
    private Long[] card;
    private Long[] category;
    private Integer[] education;
    private Integer[] title;
    private Integer[] quality;
    private Long[] honour;
    private String startTime;
    private String endTime;

    /**
     * @param evEventPO
     * @param eventDTO
     * @return evEventPO
     */
    public static EvEventPO setEventPO(EvEventPO evEventPO, EventDTO eventDTO) {
        evEventPO.setName(eventDTO.getName());
        evEventPO.setNum(eventDTO.getNum());
        evEventPO.setSponsor(eventDTO.getSponsor());
        evEventPO.setTime(eventDTO.getTime());
        evEventPO.setDuration(eventDTO.getDuration());
        evEventPO.setDate(eventDTO.getDate());
        evEventPO.setEfId(eventDTO.getEventField());
        evEventPO.setDetail(eventDTO.getDetail());
        evEventPO.setProcess(eventDTO.getProcess());
        evEventPO.setTarget(eventDTO.getTarget());
        evEventPO.setContact(eventDTO.getContact());
        evEventPO.setRoleId(eventDTO.getRoleId());
        evEventPO.setPicture(eventDTO.getPicture());
        evEventPO.setIfQuota(eventDTO.getIfQuota());
        evEventPO.setMaleQuota(eventDTO.getMaleQuota());
        evEventPO.setFemaleQuota(eventDTO.getFemaleQuota());
        evEventPO.setEventQuota(eventDTO.getEventQuota());
        evEventPO.setCurrentNum(0);
        evEventPO.setCurrentMale(0);
        evEventPO.setCurrentFemale(0);
        evEventPO.setUpdateTime(new Date());
        evEventPO.setStatus((byte) 2);
        evEventPO.setUpDown((byte) 2);
        evEventPO.setDr((byte) 1);
        evEventPO.setStartTime(DateUtil.str2Date(eventDTO.getStartTime(), DateUtil.YMD_HMS));
        evEventPO.setEndTime(DateUtil.str2Date(eventDTO.getEndTime(), DateUtil.YMD_HMS));
        return evEventPO;
    }

    /**
     * 构建evEventQueryPO
     *
     * @param evEventQueryPO
     * @param eventDTO
     * @return
     */
    public static EvEventQueryPO setEventQueryPO(EvEventQueryPO evEventQueryPO, EventDTO eventDTO) {
        evEventQueryPO.setName(eventDTO.getName());
        evEventQueryPO.setEventTime(eventDTO.getTime());
        evEventQueryPO.setEfId(eventDTO.getEventField());
        evEventQueryPO.setType((byte) 2);
        evEventQueryPO.setStatus((byte) 2);
        evEventQueryPO.setUpDown((byte) 2);
        evEventQueryPO.setCreateTime(new Date());
        evEventQueryPO.setStartTime(DateUtil.str2Date(eventDTO.getStartTime(), DateUtil.YMD_HMS));
        evEventQueryPO.setEndTime(DateUtil.str2Date(eventDTO.getEndTime(), DateUtil.YMD_HMS));
        evEventQueryPO.setUpdateTime(new Date());
        evEventQueryPO.setDate(eventDTO.getDate());
        return evEventQueryPO;
    }
}
