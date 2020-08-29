package com.talentcard.web.dto;

import com.talentcard.common.pojo.EvEventPO;
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
    private Date updateTime;
    /**
     * 可查看与申请此政策权益的人才卡IDs
     */
    private String[] cardId;
    /**
     * 可查看与申请此政策权益的人才类别IDs
     */
    private String[] category;
    /**
     * 可查看与申请此政策权益的人才学历IDs
     */
    private String[] education;
    /**
     * 可查看与申请此政策权益的人才职称IDs
     */
    private String[] title;
    /**
     * 可查看与申请此政策权益的人才职业资格IDs
     */
    private String[] qualityIds;
    /**
     * 可查看与申请此政策权益的人才荣誉IDs
     */
    private Long[] honour;

    /**
     *
     * @param evEventPO
     * @param eventDTO
     * @return evEventPO
     */
    public static EvEventPO dtoConvertPo(EvEventPO evEventPO, EventDTO eventDTO) {
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
        evEventPO.setUpdateTime(new Date());
        return evEventPO;
    }
}
