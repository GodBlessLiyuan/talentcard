package com.talentcard.miniprogram.service.impl;

import com.github.pagehelper.Page;
import com.talentcard.common.bo.EvFrontendEventBO;
import com.talentcard.common.constant.EventConstant;
import com.talentcard.common.mapper.*;
import com.talentcard.common.pojo.*;
import com.talentcard.common.utils.DateUtil;
import com.talentcard.common.utils.PageHelper;
import com.talentcard.common.utils.StringSortUtil;
import com.talentcard.common.utils.VerificationCodeUtil;
import com.talentcard.common.vo.PageInfoVO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.miniprogram.constant.ApprovalStatusConstant;
import com.talentcard.miniprogram.dto.TalentActivitiesDTO;
import com.talentcard.miniprogram.service.ITalentActivitiesService;
import com.talentcard.miniprogram.vo.TalentActivitiesVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.talentcard.common.utils.DateUtil.YMD;
import static com.talentcard.common.utils.DateUtil.YMD_HMS;

/**
 * @ Author     ：AnHongxu.
 * @ Date       ：Created in 14:32 2020/8/28
 * @ Description：前台人才活动
 * @ Modified By：
 * @ Version:     1.0
 */
@Slf4j
@Service
public class TalentActivitiesServiceImpl implements ITalentActivitiesService {

    @Autowired
    private EvFrontendEventMapper evFrontendEventMapper;
    @Autowired
    private EvEventQueryMapper evEventQueryMapper;
    @Autowired
    TalentMapper talentMapper;
    @Autowired
    EvEventFieldMapper evEventFieldMapper;
    @Autowired
    EvEventTimeMapper evEventTimeMapper;
    @Autowired
    EvFrontendEventApprovalMapper evFrontendEventApprovalMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO insert(HttpSession session, TalentActivitiesDTO dto) {
        //获取验证码和前台传的验证码进行比较
        String vcode1 = dto.getVcode();
        if (StringUtils.isBlank(vcode1)) {
            return new ResultVO(1002);//用户输入验证码为空
        }
        String vcode2 = VerificationCodeUtil.getCode(dto.getPhone());
        if (!vcode1.equals(vcode2)) {
            return new ResultVO(1001);//验证码不匹配
        }
        //将前台dto中获取的数据转换成po进行插入,将数据插入到前台活动表中
        EvFrontendEventPO evFrontendEventPO = buildPOByDTO(new EvFrontendEventPO(), dto);
        evFrontendEventMapper.insert(evFrontendEventPO);

        //进行审批表插入一条提交申请的插入记录
        EvFrontendEventApprovalPO evFrontendEventApprovalPO = new EvFrontendEventApprovalPO();
        evFrontendEventApprovalPO.setFeId(evFrontendEventPO.getFeId());
        evFrontendEventApprovalPO.setUserId((Long) session.getAttribute("userId"));
        evFrontendEventApprovalPO.setUsername((String) session.getAttribute("username"));
        evFrontendEventApprovalPO.setType((byte) 1);
        evFrontendEventApprovalPO.setCreateTime(new Date());
        evFrontendEventApprovalPO.setUpdateTime(new Date());
        evFrontendEventApprovalMapper.insertSelective(evFrontendEventApprovalPO);
        //根据活动场地和活动的日期进行活动场地占用情况的插入或更新
        //判断是插入还是更新
        //如果根据活动场地和日期没有查到数据，则进行插入操作,否则进行时间段合并进行更新
        Map<String, Object> map = new HashMap(2);
        map.put("efid", dto.getEfid());
        map.put("date", dto.getDate());
        EvEventTimePO evEventTimePO = evEventTimeMapper.queryByPlaceAndDate(map);

        if (evEventTimePO == null) {
            EvEventTimePO evEventTimePO1 = new EvEventTimePO();
            evEventTimePO1.setEfId(evFrontendEventPO.getEfId());
            evEventTimePO1.setPlaceDate(evFrontendEventPO.getEventDate());
            evEventTimePO1.setTimeInterval(evFrontendEventPO.getTimeInterval());
            evEventTimeMapper.insert(evEventTimePO1);
        } else {
            if (dto.getInterval() != null && dto.getInterval().length > 0) {
                String newTimeinterval = String.join(",", dto.getInterval());
                newTimeinterval = StringSortUtil.sort(evEventTimePO.getTimeInterval(), "", newTimeinterval);

                evEventTimePO.setTimeInterval(newTimeinterval);
                evEventTimeMapper.updateByPrimaryKeySelective(evEventTimePO);
            }
        }
        return new ResultVO(1000);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO update(HttpSession session, TalentActivitiesDTO dto) {
        EvFrontendEventPO old = evFrontendEventMapper.selectByPrimaryKey(dto.getFeid());
        if (old == null) {
            return new ResultVO(2000);
        }

        /**
         * 当活动已经审批通过需要时，不可以更新活动状态
         */
        if (old.getStatus() == EventConstant.AGREE) {
            return new ResultVO(2000);
        }

        String oldTimeInterval = old.getTimeInterval();

        //将前台dto中获取的数据转换成po进行更新
        EvFrontendEventPO evFrontendEventPO = buildPOByDTO(old, dto);
        evFrontendEventMapper.updateByPrimaryKeySelective(evFrontendEventPO);

        //进行审批表插入一条提交申请的插入记录
        EvFrontendEventApprovalPO evFrontendEventApprovalPO = new EvFrontendEventApprovalPO();
        evFrontendEventApprovalPO.setFeId(evFrontendEventPO.getFeId());
        evFrontendEventApprovalPO.setType((byte) 1);
        evFrontendEventApprovalPO.setCreateTime(new Date());
        evFrontendEventApprovalPO.setUpdateTime(new Date());

        evFrontendEventApprovalMapper.insertSelective(evFrontendEventApprovalPO);
        //根据活动场地和活动的日期进行活动场地占用情况的插入或更新
        //判断是插入还是更新
        //如果根据活动场地和日期没有查到数据，则进行插入操作,否则进行时间段合并进行更新
        Map<String, Object> map = new HashMap(1);
        map.put("efid", dto.getEfid());
        map.put("date", dto.getDate());
        EvEventTimePO evEventTimePO = evEventTimeMapper.queryByPlaceAndDate(map);
        if (evEventTimePO == null) {
            EvEventTimePO evEventTimePO1 = new EvEventTimePO();
            evEventTimePO1.setEfId(evFrontendEventPO.getEfId());
            evEventTimePO1.setPlaceDate(evFrontendEventPO.getEventDate());
            evEventTimePO1.setTimeInterval(evFrontendEventPO.getTimeInterval());
            evEventTimeMapper.insert(evEventTimePO1);
        } else {
            String newTimeinterval = StringSortUtil.sort(evEventTimePO.getTimeInterval(), oldTimeInterval, evFrontendEventPO.getTimeInterval());
            evEventTimePO.setTimeInterval(newTimeinterval);
            evEventTimeMapper.updateByPrimaryKeySelective(evEventTimePO);
        }
        return new ResultVO(1000);
    }

    @Override
    public ResultVO pageQuery(Map<String, Object> reqData) {
        Page<EvFrontendEventBO> page = PageHelper.startPage(reqData);
        List<EvFrontendEventBO> evFrontendEventBOS = evFrontendEventMapper.pageQuery(reqData);
        return new ResultVO(1000, new PageInfoVO<>(page.getTotal(), TalentActivitiesVO.convert(evFrontendEventBOS)));
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO cancel(HttpSession session, Map<String, Object> reqData) {
        //首先查询前台是否已经取消（根据活动id查询出后台是否取消状态）
        EvFrontendEventPO evFrontendEventPO = evFrontendEventMapper.selectByPrimaryKey(Long.parseLong(reqData.get("feid").toString()));

        if(evFrontendEventPO ==  null){
            return new ResultVO(2000);
        }
        //如果后台已经取消
        if (EventConstant.ADMINCANCEL == evFrontendEventPO.getStatus()) {
            return new ResultVO(1001);//管理员已取消
        }
        //如果没有取消则进行前台取消操
        evFrontendEventPO.setStatus((byte) EventConstant.USERCANCEL);
        evFrontendEventPO.setUpdateTime(new Date());
        evFrontendEventMapper.updateByPrimaryKeySelective(evFrontendEventPO);
        //进行后台查询表更新操作
        EvEventQueryPO evEventQueryPO = evEventQueryMapper.queryByEid(Long.parseLong(reqData.get("feid").toString()));
        if (evEventQueryPO != null) {
            evEventQueryPO.setStatus((byte) EventConstant.USERCANCEL);
            evEventQueryPO.setUpdateTime(new Date());
            evEventQueryMapper.updateByPrimaryKeySelective(evEventQueryPO);
        }
        //将活动取消的记录插入活动审批表中
        EvFrontendEventApprovalPO evFrontendEventApprovalPO = new EvFrontendEventApprovalPO();
        evFrontendEventApprovalPO.setFeId(Long.parseLong(reqData.get("feid").toString()));
        evFrontendEventApprovalPO.setUserId((Long) session.getAttribute("userId"));
        evFrontendEventApprovalPO.setUsername((String) session.getAttribute("username"));
        evFrontendEventApprovalPO.setType(ApprovalStatusConstant.CANCEL);//活动取消
        evFrontendEventApprovalPO.setCreateTime(new Date());
        evFrontendEventApprovalPO.setUpdateTime(new Date());
        evFrontendEventApprovalMapper.insertSelective(evFrontendEventApprovalPO);
        //取消活动后释放场地占用时间段
        //根据场地和日期查询所有占用的时间段
        Map<String, Object> map = new HashMap<>(1);
        map.put("efid", evFrontendEventPO.getEfId().toString());
        map.put("date", DateUtil.date2Str(evFrontendEventPO.getEventDate(), YMD));
        EvEventTimePO evEventTimePO = evEventTimeMapper.queryByPlaceAndDate(map);

        if(evEventTimePO != null){
            String newInterval =  StringSortUtil.sort(evEventTimePO.getTimeInterval(),evFrontendEventPO.getTimeInterval(),"");
            //将新的时间段更新会时间占用表中
            evEventTimePO.setTimeInterval(newInterval);
            evEventTimeMapper.updateByPrimaryKey(evEventTimePO);
        }

        return new ResultVO(1000);
    }

    @Override
    public ResultVO queryTalentByOpenId(Map<String, Object> reqData) {
        TalentPO talentPO = talentMapper.queryByOpenid(reqData.get("oid").toString());
        return new ResultVO(1000, talentPO);
    }

    @Override
    public ResultVO queryAllPlace() {
        List<EvEventFieldPO> evEventFieldPOS = evEventFieldMapper.selectAllPlace();
        return new ResultVO(1000, evEventFieldPOS);
    }

    @Override
    public ResultVO queryByPlaceAndDate(Map<String, Object> reqData) {
        EvEventTimePO evEventTimePO = evEventTimeMapper.queryByPlaceAndDate(reqData);
        return new ResultVO(1000, evEventTimePO);
    }

    @Override
    public ResultVO queryPlaceById(Map<String, Object> reqData) {
        EvEventFieldPO evEventFieldPO = evEventFieldMapper.selectByPrimaryKey(Long.parseLong(reqData.get("efid").toString()));
        return new ResultVO(1000, evEventFieldPO);
    }

    @Override
    public ResultVO queryByFeid(Map<String, Object> reqData) {
        EvFrontendEventApprovalPO evFrontendEventApprovalPO = evFrontendEventApprovalMapper.queryByFeid(Long.parseLong(reqData.get("feid").toString()));
        return new ResultVO(1000, evFrontendEventApprovalPO);
    }

    @Override
    public ResultVO detail(Map<String, Object> reqData) {
        EvFrontendEventPO evFrontendEventPO = evFrontendEventMapper.selectByPrimaryKey(Long.parseLong(reqData.get("feid").toString()));
        return new ResultVO(1000, TalentActivitiesVO.convert(evFrontendEventPO));
    }

    /**
     * 根据 dto 构建 po
     *
     * @param po
     * @param dto
     */
    private EvFrontendEventPO buildPOByDTO(EvFrontendEventPO po, TalentActivitiesDTO dto) {
        po.setFeId(dto.getFeid());
        po.setName(dto.getName());
        po.setEfId(dto.getEfid());
        String time = DateUtil.date2Str(dto.getStime(), YMD_HMS) + " ~ " + DateUtil.date2Str(dto.getEtime(), YMD_HMS);
        po.setTime(time);
        po.setDuration(dto.getDuration());
        po.setSponsor(dto.getSponsor());
        po.setDetail(dto.getDetail());
        po.setContactPerson(dto.getCperson());
        po.setPhone(dto.getPhone());
        po.setPicture(dto.getPicture());
        po.setTalentId(dto.getTid());
        po.setOpenId(dto.getOid());
        po.setStatus((byte) EventConstant.APPLY);
        po.setEventDate(DateUtil.str2Date(dto.getDate(), YMD));
        po.setCreateTime(new Date());
        po.setEndTime(dto.getEtime());
        po.setStartTime(dto.getStime());
        po.setEventNum(dto.getNum());
        String interval = String.join(",", dto.getInterval());
        po.setTimeInterval(interval);
        po.setUpdateTime(new Date());
        return po;
    }
}
