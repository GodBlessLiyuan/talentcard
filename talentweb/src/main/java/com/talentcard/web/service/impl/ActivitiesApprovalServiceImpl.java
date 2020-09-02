package com.talentcard.web.service.impl;

import com.github.pagehelper.Page;
import com.talentcard.common.bo.EvFrontendEventBO;
import com.talentcard.common.mapper.*;
import com.talentcard.common.pojo.*;
import com.talentcard.common.utils.DateUtil;
import com.talentcard.common.utils.PageQueryUtil;
import com.talentcard.common.vo.PageInfoVO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.constant.OpsRecordMenuConstant;
import com.talentcard.web.service.IActivitiesApprovalService;
import com.talentcard.web.service.ILogService;
import com.talentcard.web.service.IWxOfficalAccountService;
import com.talentcard.web.vo.ActivitiesApprovalVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.*;

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
        Page<EvFrontendEventBO> page = PageQueryUtil.startPage(reqData);
        List<EvFrontendEventBO> evFrontendEventBOS = evFrontendEventMapper.approvalQuery(reqData);
        return new ResultVO(1000, new PageInfoVO<>(page.getTotal(), ActivitiesApprovalVO.convert(evFrontendEventBOS)));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO cancel(HttpSession session, Map<String, Object> reqData) {
        String role = reqData.get("role").toString();//区分取消的角色
        //从session中获取userId的值
       /* Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            // 用户过期
            return ResultVO.notLogin();
        }*/
        //首先查询前台是否已经取消（根据活动id查询出后台是否取消状态）
        EvFrontendEventPO evFrontendEventPO = evFrontendEventMapper.selectByPrimaryKey(Long.parseLong(reqData.get("feid").toString()));
        //如果后台已经取消
        if (4 == evFrontendEventPO.getStatus()) {
            return new ResultVO(1001);//管理员已取消
        }
        //如果没有取消则进行前台取消操作
        if ("1".equals(role)) {//管理员取消
            evFrontendEventPO.setStatus((byte) 4);
            evFrontendEventPO.setUpdateTime(new Date());
            evFrontendEventMapper.updateByPrimaryKeySelective(evFrontendEventPO);
            //进行后台查询表更新操作
            EvEventQueryPO evEventQueryPO = evEventQueryMapper.queryByEid(Long.parseLong(reqData.get("feid").toString()));
            if (evEventQueryPO != null) {
                evEventQueryPO.setStatus((byte) 4);
                evEventQueryPO.setUpdateTime(new Date());
                evEventQueryMapper.updateByPrimaryKeySelective(evEventQueryPO);
            }
        }
        //如果没有取消则进行前台取消操作
        if ("2".equals(role)) {//用户取消
            evFrontendEventPO.setStatus((byte) 5);
            evFrontendEventPO.setUpdateTime(new Date());
            evFrontendEventMapper.updateByPrimaryKeySelective(evFrontendEventPO);
            //进行后台查询表更新操作
            EvEventQueryPO evEventQueryPO = evEventQueryMapper.queryByEid(Long.parseLong(reqData.get("feid").toString()));
            if (evEventQueryPO != null) {
                evEventQueryPO.setStatus((byte) 5);
                evEventQueryPO.setUpdateTime(new Date());
                evEventQueryMapper.updateByPrimaryKeySelective(evEventQueryPO);
            }
        }
        //取消活动后释放场地占用时间段
        //根据场地和日期查询所有占用的时间段
        Map<String, Object> reqDate = new HashMap<>(1);
        reqData.put("efid", evFrontendEventPO.getEfId().toString());
        reqData.put("date", DateUtil.date2Str(evFrontendEventPO.getEventDate(), YMD));
        EvEventTimePO evEventTimePO = evEventTimeMapper.queryByPlaceAndDate(reqData);
        List<String> list = Arrays.asList(evEventTimePO.getTimeInterval().split(","));
        List<String> arrayList = new ArrayList<String>(list);//转换为ArrayLsit调用相关的remove方法
        //查出当前活动的时间段
        EvFrontendEventPO EvFrontendEventPO1 = evFrontendEventMapper.selectByPrimaryKey(Long.parseLong(reqData.get("feid").toString()));
        String[] thisInterval = EvFrontendEventPO1.getTimeInterval().split(",");
        //从以前的时间段将现在的时间段删掉
        for (int i = 0; i < thisInterval.length; i++)
            if (arrayList.contains(thisInterval[i])) {
                arrayList.remove(thisInterval[i]);
            }
        //将新的arraylist转为数组
        String[] newIntervalArray = (String[]) arrayList.toArray();
        String newInterval = StringUtils.join(newIntervalArray, ",");
        //将新的时间段更新会时间占用表中
        evEventTimePO.setTimeInterval(newInterval);
        evEventTimeMapper.updateByPrimaryKeySelective(evEventTimePO);
        logService.insertActionRecord(session, OpsRecordMenuConstant.F_TalentActivities, OpsRecordMenuConstant.S_ActivitiesApproval
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
        return new ResultVO(1000, evFrontendEventApprovalPOS);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO approval(HttpSession session, Map<String, Object> reqData) {
        //进行审批表插入一条提交申请的插入记录
        EvFrontendEventApprovalPO evFrontendEventApprovalPO = new EvFrontendEventApprovalPO();
        evFrontendEventApprovalPO.setFeId(Long.parseLong(reqData.get("feid").toString()));
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
            EvFrontendEventPO evFrontendEventPO = evFrontendEventMapper.selectByPrimaryKey(Long.parseLong(reqData.get("feid").toString()));
            EvEventQueryPO evEventQueryPO = new EvEventQueryPO();
            evEventQueryPO.setEventId(evFrontendEventPO.getFeId());
            evEventQueryPO.setName(evFrontendEventPO.getName());
            evEventQueryPO.setEventTime(evFrontendEventPO.getTime());
            evEventQueryPO.setEfId(evFrontendEventPO.getEfId());
            evEventQueryPO.setType((byte) 1);
            evEventQueryPO.setStatus((byte) 2);
            evEventQueryPO.setCreateTime(evFrontendEventPO.getCreateTime());
            evEventQueryPO.setUpdateTime(new Date());
            evEventQueryPO.setStartTime(evFrontendEventPO.getStartTime());
            evEventQueryPO.setEndTime(evFrontendEventPO.getEndTime());
            evEventQueryMapper.insertSelective(evEventQueryPO);
            //更新前台活动表的审批状态
            evFrontendEventPO.setStatus((byte) 2);
            evFrontendEventMapper.updateByPrimaryKeySelective(evFrontendEventPO);
            //发送推送模板
            String activityName=evFrontendEventPO.getName();
            String openId=evFrontendEventPO.getOpenId();
            String opinion=reqData.get("opinion").toString();
            wxOfficalAccountService.messToEventAgree(openId,activityName);
            logService.insertActionRecord(session, OpsRecordMenuConstant.F_TalentActivities, OpsRecordMenuConstant.S_ActivitiesApproval
                    , "审批活动通过\"%s\"", activityName);
        }
        if ("2".equals(reqData.get("result").toString())) {
            evFrontendEventApprovalPO.setResult((byte) 2);
            evFrontendEventApprovalPO.setOpinion(reqData.get("opinion").toString());
            evFrontendEventApprovalMapper.insertSelective(evFrontendEventApprovalPO);
            //更新前台活动表的审批状态
            EvFrontendEventPO evFrontendEventPO = evFrontendEventMapper.selectByPrimaryKey(Long.parseLong(reqData.get("feid").toString()));
            evFrontendEventPO.setStatus((byte) 3);
            evFrontendEventMapper.updateByPrimaryKeySelective(evFrontendEventPO);
            //释放占用时间段
            //驳回活动后释放场地占用时间段
            //根据场地和日期查询所有占用的时间段
            Map<String, Object> reqDate = new HashMap<>(1);
            reqData.put("efid", evFrontendEventPO.getEfId().toString());
            reqData.put("date", DateUtil.date2Str(evFrontendEventPO.getEventDate(), YMD));
            EvEventTimePO evEventTimePO = evEventTimeMapper.queryByPlaceAndDate(reqData);
            List<String> list = Arrays.asList(evEventTimePO.getTimeInterval().split(","));
            List<String> arrayList = new ArrayList<String>(list);//转换为ArrayLsit调用相关的remove方法
            //查出当前活动的时间段
            EvFrontendEventPO EvFrontendEventPO1 = evFrontendEventMapper.selectByPrimaryKey(Long.parseLong(reqData.get("feid").toString()));
            String[] thisInterval = EvFrontendEventPO1.getTimeInterval().split(",");
            //从以前的时间段将现在的时间段删掉
            for (int i = 0; i < thisInterval.length; i++)
                if (arrayList.contains(thisInterval[i])) {
                    arrayList.remove(thisInterval[i]);
                }
            //将新的arraylist转为数组
            String[] newIntervalArray = (String[]) arrayList.toArray();
            String newInterval = StringUtils.join(newIntervalArray, ",");
            //将新的时间段更新会时间占用表中
            evEventTimePO.setTimeInterval(newInterval);
            evEventTimeMapper.updateByPrimaryKeySelective(evEventTimePO);
            //发送推送模板
            String activityName=evFrontendEventPO.getName();
            String openId=evFrontendEventPO.getOpenId();
            //根据openid获取人才名称
            TalentPO talentPO = talentMapper.queryByOpenid(openId);
            String talentName = talentPO.getName();
            String opinion=reqData.get("opinion").toString();
            wxOfficalAccountService.messToEventReject(openId,talentName,activityName,opinion);
            logService.insertActionRecord(session, OpsRecordMenuConstant.F_TalentActivities, OpsRecordMenuConstant.S_ActivitiesApproval
                    , "审批活动驳回\"%s\"", activityName);
        }
        return new ResultVO(1000);
    }
    @Override
    public ResultVO detail(Map<String, Object> reqData) {
        EvFrontendEventPO evFrontendEventPO= evFrontendEventMapper.selectByPrimaryKey(Long.parseLong(reqData.get("feid").toString()));
        return new ResultVO(1000, ActivitiesApprovalVO.convert(evFrontendEventPO));
    }
}