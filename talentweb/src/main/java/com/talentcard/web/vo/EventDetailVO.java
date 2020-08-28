package com.talentcard.web.vo;

import com.talentcard.common.pojo.EvEventLogPO;
import com.talentcard.common.pojo.EvEventPO;
import lombok.Data;

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
        eventDetailVO.setEventField(evEventPO.getEventField());
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
}
