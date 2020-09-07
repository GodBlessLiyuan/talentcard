package com.talentcard.miniprogram.vo;

import com.talentcard.common.bo.EvFrontendEventBO;
import com.talentcard.common.constant.EventConstant;
import com.talentcard.common.pojo.EvFrontendEventPO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @ Author     ：AnHongxu.
 * @ Date       ：Created in 14:24 2020/8/28
 * @ Description：人才活动VO
 * @ Modified By：
 * @ Version:     1.0
 */
@Data
public class TalentActivitiesVO implements Serializable {
    /**
     * 活动id
     */
    private Long feid;

    /**
     * 活动名称
     */
    private String name;
    /**
     * 活动时间
     */
    private String time;
    /**
     * 活动场地id
     */
    private Long efid;
    /**
     * 活动时长
     */
    private Double duration;
    /**
     * 活动参与人数
     */
    private Integer num;
    /**
     * 活动日期
     */
    private Date edate;
    /**
     * 时间间隔
     */
    private List<String> interval;
    /**
     * 活动发起方
     */
    private String sponsor;
    /**
     * 活动详情
     */
    private String detail;
    /**
     * 联系人
     */
    private String cperson;
    /**
     * 联系方式
     */
    private String phone;
    /**
     * 活动图片
     */
    private String picture;
    /**
     * 人才ID
     */
    private Long talentId;
    /**
     * 人才openid
     */
    private String  oid;
    /**
     * 1：已同意；2：已驳回；3：待审批
     */
    private Byte status;
    /**
     * 创建时间
     */
    private Date ctime;
    /**
     * 活动开始时间
     */
    private Date stime;
    /**
     * 活动结束时间
     */
    private Date etime;
    /**
     * 活动状态展示：
     * 1）审批中：只要活动没有审批就显示为审批中；
     *
     * 2）已通过：审批通过，且活动还没有开始，活动状态为“已通过”；
     *
     * 3）进行中：审批通过，且当前时间≥活动开始时间，小于活动结束时间；
     *
     * 3）已结束：当前时间≥活动结束时间的，活动状态为“已结束”；
     *
     * 4）已驳回：审批的时候被驳回，活动状态为“已驳回”。
     *
     * 5）已取消：活动结束前都可以取消，这样可以保证及时释放场地资源；如果活动取消，则状态变为”已取消“；
     */
    private Byte sshow;

    private String ropinion;

    /**
     * pos 转 vos
     *
     * @param bos
     * @return
     */
    public static List<TalentActivitiesVO> convert(List<EvFrontendEventBO> bos) {
        List<TalentActivitiesVO> vos = new ArrayList<>();
        for (EvFrontendEventBO bo : bos) {
            vos.add(TalentActivitiesVO.convert(bo));
        }
        return vos;
    }

    public static TalentActivitiesVO convert(EvFrontendEventBO bo) {
        TalentActivitiesVO vo = new TalentActivitiesVO();
        vo.setFeid(bo.getFeId());
        vo.setName(bo.getName());
        vo.setTime(bo.getTime());
        vo.setEfid(bo.getEfId());
        vo.setDuration(bo.getDuration());
        vo.setNum(bo.getEventNum());
        vo.setEdate(bo.getEventDate());
        vo.setInterval(Arrays.asList(bo.getTimeInterval().split(",")));
        vo.setSponsor(bo.getSponsor());
        vo.setDetail(bo.getDetail());
        vo.setCperson(bo.getContactPerson());
        vo.setPhone(bo.getPhone());
        vo.setPicture(bo.getPicture());
        vo.setTalentId(bo.getTalentId());
        vo.setOid(bo.getOpenId());
        //1：提交待审批；2：已同意（已通过）；3：已驳回； 4：管理员取消；5：用户取消
        vo.setStatus(bo.getStatus());
        vo.setCtime(bo.getCreateTime());
        vo.setStime(bo.getStartTime());
        vo.setEtime(bo.getEndTime());
        vo.setRopinion(bo.getRejectOpinion());
        //进行实践判断，对于前台展示不同的状态
        //当前时间
       /* Date nowDate=new Date();
        if (bo.getStatus()==1){
            vo.setSshow((byte)1);//审批中
        }
        if (bo.getStatus()==2&&nowDate.compareTo(bo.getStartTime())<0){
            vo.setSshow((byte)2);//未开始
        }
        if (bo.getStatus()==2&&nowDate.compareTo(bo.getStartTime())>=0&&nowDate.compareTo(bo.getEndTime())<0){
            vo.setSshow((byte)3);//进行中
        }
        if (bo.getStatus()==2 && nowDate.compareTo(bo.getEndTime())>=0){
            vo.setSshow((byte)4);//已结束
        }
        if (bo.getStatus()==3){
            vo.setSshow((byte)5);//已驳回
        }
        if (bo.getStatus()==4||bo.getStatus()==5){
            vo.setSshow((byte)6);//已取消
        }*/
        //进行时间判断，对于前台展示不同的状态
        //当前时间
        Date nowDate=new Date();
        Long currentTime=nowDate.getTime();
        //将当前时间转换为long类型
        Long startTime=vo.getStime().getTime();
        Long endTime=vo.getEtime().getTime();
        vo.setSshow((byte) EventConstant.getStatus(currentTime,startTime,endTime,vo.getStatus(), (byte) 100));
        return vo;
    }
    public static TalentActivitiesVO convert(EvFrontendEventPO bo) {
        TalentActivitiesVO vo = new TalentActivitiesVO();
        vo.setFeid(bo.getFeId());
        vo.setName(bo.getName());
        vo.setTime(bo.getTime());
        vo.setEfid(bo.getEfId());
        vo.setDuration(bo.getDuration());
        vo.setNum(bo.getEventNum());
        vo.setEdate(bo.getEventDate());
        vo.setInterval(Arrays.asList(bo.getTimeInterval().split(",")));
        vo.setSponsor(bo.getSponsor());
        vo.setDetail(bo.getDetail());
        vo.setCperson(bo.getContactPerson());
        vo.setPhone(bo.getPhone());
        vo.setPicture(bo.getPicture());
        vo.setTalentId(bo.getTalentId());
        vo.setOid(bo.getOpenId());
        //1：提交待审批；2：已同意（已通过）；3：已驳回； 4：管理员取消；5：用户取消
        vo.setStatus(bo.getStatus());
        vo.setCtime(bo.getCreateTime());
        vo.setStime(bo.getStartTime());
        vo.setEtime(bo.getEndTime());
        //进行实践判断，对于前台展示不同的状态
        //当前时间
        /*Date nowDate=new Date();
        if (bo.getStatus()==1){
            vo.setSshow((byte)1);//审批中
        }
        if (bo.getStatus()==2&&nowDate.compareTo(bo.getStartTime())<0){
            vo.setSshow((byte)2);//未开始
        }
        if (bo.getStatus()==2&&nowDate.compareTo(bo.getStartTime())>=0&&nowDate.compareTo(bo.getEndTime())<0){
            vo.setSshow((byte)3);//进行中
        }
        if (bo.getStatus()==2 && nowDate.compareTo(bo.getEndTime())>=0){
            vo.setSshow((byte)4);//已结束
        }
        if (bo.getStatus()==3){
            vo.setSshow((byte)5);//已驳回
        }
        if (bo.getStatus()==4||bo.getStatus()==5){
            vo.setSshow((byte)6);//已取消
        }*/
        //进行时间判断，对于前台展示不同的状态
        //当前时间
        Date nowDate=new Date();
        Long currentTime=nowDate.getTime();
        //将当前时间转换为long类型
        Long startTime=vo.getStime().getTime();
        Long endTime=vo.getEtime().getTime();
        vo.setSshow((byte) EventConstant.getStatus(currentTime,startTime,endTime,vo.getStatus(), (byte) 100));
        return vo;
    }

}
