package com.talentcard.web.service.impl;

import com.github.pagehelper.Page;
import com.talentcard.common.bo.EvFrontendEventBO;
import com.talentcard.common.constant.EventConstant;
import com.talentcard.common.mapper.*;
import com.talentcard.common.pojo.*;
import com.talentcard.common.utils.DateUtil;
import com.talentcard.common.utils.PageHelper;
import com.talentcard.common.utils.StringSortUtil;
import com.talentcard.common.vo.PageInfoVO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.constant.OpsRecordMenuConstant;
import com.talentcard.web.service.IActivitiesApprovalService;
import com.talentcard.web.service.ILogService;
import com.talentcard.web.service.IWxOfficalAccountService;
import com.talentcard.web.vo.ActivitiesApprovalVO;
import com.talentcard.web.vo.ApprovalRecordVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.talentcard.common.utils.DateUtil.YMD;

/**
 * @ Author     ：AnHongxu.
 * @ Date       ：Created in 14:32 2020/8/28
 * @ Description：前台人才活动
 * @ Modified By：
 * @ Version:     1.0
 */
@Slf4j
@Service
public class ActivitiesApprovalServiceImpl implements IActivitiesApprovalService {
    @Autowired
    private ILogService logService;
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
    @Autowired
    IWxOfficalAccountService wxOfficalAccountService;

    @Override
    public ResultVO approvalQuery(Map<String, Object> reqData) {
        Page<EvFrontendEventBO> page = PageHelper.startPage(reqData);
        List<EvFrontendEventBO> evFrontendEventBOS = evFrontendEventMapper.approvalQuery(reqData);
        return new ResultVO(1000, new PageInfoVO<>(page.getTotal(), ActivitiesApprovalVO.convert(evFrontendEventBOS)));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO cancel(HttpSession session, Map<String, Object> reqData) {
        //从session中获取userId的值
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            // 用户过期
            return ResultVO.notLogin();
        }
        //首先查询前台是否已经取消（根据活动id查询出后台是否取消状态）
        EvFrontendEventPO evFrontendEventPO = evFrontendEventMapper.selectByPrimaryKey(Long.parseLong(reqData.get("feid").toString()));
        //如果后台已经取消
        if (EventConstant.ADMINCANCEL == evFrontendEventPO.getStatus()) {
            return new ResultVO(1001);//管理员已取消
        }
        //如果没有取消则进行前台取消操作
        //管理员取消
        evFrontendEventPO.setStatus((byte) EventConstant.ADMINCANCEL);
        evFrontendEventPO.setUpdateTime(new Date());
        evFrontendEventMapper.updateByPrimaryKeySelective(evFrontendEventPO);
        //进行后台查询表更新操作
        EvEventQueryPO evEventQueryPO = evEventQueryMapper.queryByEid(Long.parseLong(reqData.get("feid").toString()));
        if (evEventQueryPO != null) {
            evEventQueryPO.setStatus((byte) 4);
            evEventQueryPO.setUpdateTime(new Date());
            evEventQueryMapper.updateByPrimaryKeySelective(evEventQueryPO);
        }
        //将取消额操作和取消的原因插入前台活动审批表
        EvFrontendEventApprovalPO evFrontendEventApprovalPO = new EvFrontendEventApprovalPO();
        evFrontendEventApprovalPO.setFeId(Long.parseLong(reqData.get("feid").toString()));
        evFrontendEventApprovalPO.setUserId((Long) session.getAttribute("userId"));
        evFrontendEventApprovalPO.setUsername((String) session.getAttribute("username"));
        evFrontendEventApprovalPO.setType((byte) 3);//活动取消
        evFrontendEventApprovalPO.setCreateTime(new Date());
        evFrontendEventApprovalPO.setUpdateTime(new Date());
        evFrontendEventApprovalPO.setOpinion(reqData.get("opinion").toString());
        evFrontendEventApprovalMapper.insertSelective(evFrontendEventApprovalPO);
        //取消活动后释放场地占用时间段
        //根据场地和日期查询所有占用的时间段
        Map<String, Object> map = new HashMap<>(1);
        map.put("efid", evFrontendEventPO.getEfId().toString());
        map.put("date", DateUtil.date2Str(evFrontendEventPO.getEventDate(), YMD));
        EvEventTimePO evEventTimePO = evEventTimeMapper.queryByPlaceAndDate(map);
        if (evEventTimePO != null) {
            String newInterval = StringSortUtil.sort(evEventTimePO.getTimeInterval(), evFrontendEventPO.getTimeInterval(), "");
            //将新的时间段更新会时间占用表中
            evEventTimePO.setTimeInterval(newInterval);
            evEventTimeMapper.updateByPrimaryKeySelective(evEventTimePO);
        }

        //将管理员取消活动后将消息推送给发起的用户
        //发送推送模板
        String activityName = evFrontendEventPO.getName();
        String openId = evFrontendEventPO.getOpenId();
        String opinion = reqData.get("opinion").toString();
        wxOfficalAccountService.messToBackEndEventCancel(openId, evFrontendEventPO.getFeId(),activityName, opinion);
        logService.insertActionRecord(session, OpsRecordMenuConstant.F_OtherService, OpsRecordMenuConstant.S_TalentActivity
                , "取消活动\"%s\"", evFrontendEventPO.getName());
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
    public ResultVO queryApprovalByFeid(Map<String, Object> reqData) {
        List<EvFrontendEventApprovalPO> evFrontendEventApprovalPOS = evFrontendEventApprovalMapper.queryApprovalByFeid(Long.parseLong(reqData.get("feid").toString()));
        return new ResultVO(1000, ApprovalRecordVO.convert(evFrontendEventApprovalPOS));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO approval(HttpSession session, Map<String, Object> reqData) {
        //从session中获取userId的值
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            // 用户过期
            return ResultVO.notLogin();
        }
        long feId = Long.parseLong(reqData.get("feid").toString());

        //进行审批表插入一条提交审批的插入记录
        EvFrontendEventApprovalPO evFrontendEventApprovalPO = new EvFrontendEventApprovalPO();
        evFrontendEventApprovalPO.setFeId(feId);
        evFrontendEventApprovalPO.setUserId((Long) session.getAttribute("userId"));
        evFrontendEventApprovalPO.setUsername((String) session.getAttribute("username"));
        evFrontendEventApprovalPO.setType((byte) 2);
        evFrontendEventApprovalPO.setCreateTime(new Date());
        evFrontendEventApprovalPO.setUpdateTime(new Date());
        //如果同意
        if ("1".equals(reqData.get("result").toString())) {
            evFrontendEventApprovalPO.setResult((byte) 1);
            evFrontendEventApprovalPO.setOpinion(reqData.get("opinion").toString());
            evFrontendEventApprovalMapper.insertSelective(evFrontendEventApprovalPO);
            //将该活动数据插入活动查询表
            EvFrontendEventPO evFrontendEventPO = evFrontendEventMapper.selectByPrimaryKey(feId);
            EvEventQueryPO evEventQueryPO = new EvEventQueryPO();
            evEventQueryPO.setEventId(evFrontendEventPO.getFeId());
            evEventQueryPO.setName(evFrontendEventPO.getName());
            evEventQueryPO.setEventTime(evFrontendEventPO.getTimeInterval());
            evEventQueryPO.setEfId(evFrontendEventPO.getEfId());
            evEventQueryPO.setType((byte) 1);
            evEventQueryPO.setStatus((byte) 2);
            evEventQueryPO.setCreateTime(evFrontendEventPO.getCreateTime());
            evEventQueryPO.setUpdateTime(evFrontendEventPO.getUpdateTime());
            evEventQueryPO.setStartTime(evFrontendEventPO.getStartTime());
            evEventQueryPO.setEndTime(evFrontendEventPO.getEndTime());
            evEventQueryPO.setDate(DateUtil.date2Str(evFrontendEventPO.getEventDate(), YMD));
            evEventQueryMapper.insertSelective(evEventQueryPO);
            //更新前台活动表的审批状态
            evFrontendEventPO.setStatus((byte) EventConstant.AGREE);
            evFrontendEventMapper.updateByPrimaryKeySelective(evFrontendEventPO);
            //发送推送模板
            String activityName = evFrontendEventPO.getName();
            String openId = evFrontendEventPO.getOpenId();
            String opinion = reqData.get("opinion").toString();
            wxOfficalAccountService.messToEventAgree(openId, activityName, opinion, feId, EventConstant.AGREE);
            logService.insertActionRecord(session, OpsRecordMenuConstant.F_OtherService, OpsRecordMenuConstant.F_TalentActivities
                    , "审批活动通过\"%s\"", activityName);
        }
        if ("2".equals(reqData.get("result").toString())) {
            evFrontendEventApprovalPO.setResult((byte) 2);
            evFrontendEventApprovalPO.setOpinion(reqData.get("opinion").toString());
            evFrontendEventApprovalMapper.insertSelective(evFrontendEventApprovalPO);
            //更新前台活动表的审批状态
            EvFrontendEventPO evFrontendEventPO = evFrontendEventMapper.selectByPrimaryKey(feId);
            evFrontendEventPO.setStatus((byte) EventConstant.REJECT);
            evFrontendEventMapper.updateByPrimaryKeySelective(evFrontendEventPO);
            //释放占用时间段
            //驳回活动后释放场地占用时间段
            //根据场地和日期查询所有占用的时间段
            Map<String, Object> map = new HashMap<>(2);
            map.put("efid", evFrontendEventPO.getEfId().toString());
            map.put("date", DateUtil.date2Str(evFrontendEventPO.getEventDate(), YMD));
            EvEventTimePO evEventTimePO = evEventTimeMapper.queryByPlaceAndDate(map);
            if (evEventTimePO != null) {
                String newInterval = StringSortUtil.sort(evEventTimePO.getTimeInterval(), evFrontendEventPO.getTimeInterval(), "");
                //将新的时间段更新会时间占用表中
                evEventTimePO.setTimeInterval(newInterval);
                evEventTimeMapper.updateByPrimaryKeySelective(evEventTimePO);
            }

            //发送推送模板
            String activityName = evFrontendEventPO.getName();
            String openId = evFrontendEventPO.getOpenId();
            //根据openid获取人才名称
            TalentPO talentPO = talentMapper.queryByOpenid(openId);
            String talentName = talentPO.getName();
            String opinion = reqData.get("opinion").toString();
            wxOfficalAccountService.messToEventReject(openId, talentName, activityName, opinion, feId, EventConstant.REJECT);
            logService.insertActionRecord(session, OpsRecordMenuConstant.F_OtherService, OpsRecordMenuConstant.S_TalentActivity
                    , "审批活动驳回\"%s\"", activityName);
        }
        return new ResultVO(1000);
    }

    @Override
    public ResultVO detail(Map<String, Object> reqData) {
        EvFrontendEventPO evFrontendEventPO = evFrontendEventMapper.selectByPrimaryKey(Long.parseLong(reqData.get("feid").toString()));
        return new ResultVO(1000, ActivitiesApprovalVO.convert(evFrontendEventPO));
    }

    @Override
    public ResultVO notApprovalNum() {
        Long notApprovalNum = evFrontendEventMapper.notApprovalNum();
        return new ResultVO(1000, notApprovalNum);
    }
}
