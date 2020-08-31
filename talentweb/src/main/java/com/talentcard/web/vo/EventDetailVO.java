package com.talentcard.web.vo;

import com.talentcard.common.pojo.EvEventEnjoyPO;
import com.talentcard.common.pojo.EvEventLogPO;
import com.talentcard.common.pojo.EvEventPO;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-08-28 09:11
 * @description
 */
@Data
public class EventDetailVO {
    private Long eventId;
    private Byte upDown;
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
    private Byte status;
    private Byte dr;
    private Date createTime;
    private Long userId;
    private List<EvEventLogPO> evEventLogPOList;
    private List<Long> card;
    private List<Long> category;
    private List<Integer> education;
    private List<Integer> title;
    private List<Integer> quality;
    private List<Long> honour;

    public static EventDetailVO convert(EvEventPO evEventPO) {
        EventDetailVO eventDetailVO = new EventDetailVO();
        eventDetailVO.setEventId(evEventPO.getEventId());
        eventDetailVO.setUpDown((evEventPO.getUpDown()));
        eventDetailVO.setName(evEventPO.getName());
        eventDetailVO.setNum(evEventPO.getNum());
        eventDetailVO.setSponsor(evEventPO.getSponsor());
        eventDetailVO.setTime(evEventPO.getTime());
        eventDetailVO.setDuration(evEventPO.getDuration());
        eventDetailVO.setDate(evEventPO.getDate());
        eventDetailVO.setEventField(evEventPO.getEfId());
        eventDetailVO.setDetail(evEventPO.getDetail());
        eventDetailVO.setProcess(evEventPO.getProcess());
        eventDetailVO.setTarget(evEventPO.getTarget());
        eventDetailVO.setContact(evEventPO.getContact());
        eventDetailVO.setPicture(evEventPO.getPicture());
        eventDetailVO.setRoleId(evEventPO.getRoleId());
        eventDetailVO.setIfQuota(evEventPO.getIfQuota());
        eventDetailVO.setMaleQuota(evEventPO.getMaleQuota());
        eventDetailVO.setFemaleQuota(evEventPO.getFemaleQuota());
        eventDetailVO.setUpdateTime(evEventPO.getUpdateTime());
        eventDetailVO.setCreateTime(evEventPO.getCreateTime());
        eventDetailVO.setUserId(evEventPO.getUserId());
        return eventDetailVO;
    }

    public static EventDetailVO setEnjoy(EventDetailVO eventDetailVO, List<EvEventEnjoyPO> evEventEnjoyPOList) {
        List<Long> card = new ArrayList();
        List<Long> category = new ArrayList();
        List<Integer> education = new ArrayList();
        List<Integer> title = new ArrayList();
        List<Integer> quality = new ArrayList();
        List<Long> honour = new ArrayList();
        for (EvEventEnjoyPO evEventEnjoyPO : evEventEnjoyPOList) {
            if (evEventEnjoyPO.getType() == 1) {
                card.add(evEventEnjoyPO.getCardId());
            } else if (evEventEnjoyPO.getType() == 2) {
                category.add(evEventEnjoyPO.getCategory());
            } else if (evEventEnjoyPO.getType() == 3) {
                education.add(evEventEnjoyPO.getEducation());
            } else if (evEventEnjoyPO.getType() == 4) {
                title.add(evEventEnjoyPO.getTitle());
            } else if (evEventEnjoyPO.getType() == 5) {
                quality.add(evEventEnjoyPO.getQuality());
            } else if (evEventEnjoyPO.getType() == 6) {
                honour.add(evEventEnjoyPO.getHonour());
            }
        }
        eventDetailVO.setCard(card);
        eventDetailVO.setCategory(category);
        eventDetailVO.setEducation(education);
        eventDetailVO.setTitle(title);
        eventDetailVO.setQuality(quality);
        eventDetailVO.setHonour(honour);
        return eventDetailVO;
    }
}
