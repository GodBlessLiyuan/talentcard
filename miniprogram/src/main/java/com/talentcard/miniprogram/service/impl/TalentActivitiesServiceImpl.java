package com.talentcard.miniprogram.service.impl;

import com.github.pagehelper.Page;
import com.talentcard.common.bo.EvFrontendEventBO;
import com.talentcard.common.mapper.*;
import com.talentcard.common.pojo.*;
import com.talentcard.common.utils.DateUtil;
import com.talentcard.common.utils.PageQueryUtil;
import com.talentcard.common.utils.VerificationCodeUtil;
import com.talentcard.common.vo.PageInfoVO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.miniprogram.dto.TalentActivitiesDTO;
import com.talentcard.miniprogram.service.ILogService;
import com.talentcard.miniprogram.service.ITalentActivitiesService;
import com.talentcard.miniprogram.vo.TalentActivitiesVO;
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
public class TalentActivitiesServiceImpl implements ITalentActivitiesService {
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO insert(HttpSession session, TalentActivitiesDTO dto) {
        //从session中获取userId的值
       /* Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            // 用户过期
            return ResultVO.notLogin();
        }*/
        //获取验证码和前台传的验证码进行比较
        String vcode1 = dto.getVcode();
        String vcode2 = VerificationCodeUtil.getCode(dto.getPhone());
        if(!vcode1.equals(vcode2)){
            return new ResultVO(1001);
        }
        //将前台dto中获取的数据转换成po进行插入,将数据插入到前台活动表中
        EvFrontendEventPO evFrontendEventPO = buildPOByDTO(new EvFrontendEventPO(), dto);
        evFrontendEventMapper.insert(evFrontendEventPO);
        /*//再将数据插入到活动查询表中
        EvEventQueryPO evEventQueryPO = new EvEventQueryPO();
        evEventQueryPO.setEqId(null);
        evEventQueryPO.setEventId(evFrontendEventPO.getFeId());
        evEventQueryPO.setName(evFrontendEventPO.getName());
        //活动查询表时间段拼接
        String date=DateUtil.date2Str(dto.getStime(),YMD_HMS)+"-"+DateUtil.date2Str(dto.getEtime(),YMD_HMS);
        evEventQueryPO.setEventTime(date);
        evEventQueryPO.setType((byte) 1);
        evEventQueryPO.setStatus((byte) 1);
        evEventQueryPO.setUpDown((byte) 1);
        evEventQueryPO.setCreateTime(new Date());
        evEventQueryMapper.insert(evEventQueryPO);*/
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
            EvEventTimePO evEventTimePO2 = new EvEventTimePO();
            //原来有的数组
            String array1[] = evEventTimePO.getTimeInterval().split(",");
            //新添加的数组
            String array2[] = dto.getInterval();
            //合并数组
            String[] result = Arrays.copyOf(array1, array1.length + array2.length);
            System.arraycopy(array2, 0, result, array1.length, array2.length);
            //去重
            HashSet<String> set = new HashSet();
            for (String i : result) {
                set.add(i);
            }
            String[] newArray = set.toArray(new String[1]);
            String newTimeinterval = String.join(",", newArray);
            evEventTimePO2.setId(evEventTimePO.getId());
            evEventTimePO2.setTimeInterval(newTimeinterval);
            evEventTimeMapper.updateByPrimaryKeySelective(evEventTimePO2);
        }
        return new ResultVO(1000);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO update(HttpSession session, TalentActivitiesDTO dto) {
        //从session中获取userId的值
        /*Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            // 用户过期
            return ResultVO.notLogin();
        }*/
        //将前台dto中获取的数据转换成po进行更新
        EvFrontendEventPO evFrontendEventPO = buildPOByDTO(new EvFrontendEventPO(), dto);
        evFrontendEventMapper.updateByPrimaryKeySelective(evFrontendEventPO);
       /* //更新后再进行更新活动查询表
        //再将数据插入到活动查询表中
        //先查询出活动id为当前id的数据主键，根据您主键进行更新
        EvEventQueryPO evEventQueryPO = evEventQueryMapper.queryByEid(dto.getFeid());
        //再将数据插入到活动查询表中
        EvEventQueryPO evEventQueryPO1 = new EvEventQueryPO();
        evEventQueryPO1.setEqId(evEventQueryPO.getEqId());
        //活动查询表时间段拼接
        String date=DateUtil.date2Str(dto.getStime(),YMD_HMS)+"-"+DateUtil.date2Str(dto.getEtime(),YMD_HMS);
        evEventQueryPO1.setEventTime(date);
        evEventQueryPO1.setType((byte) 1);
        evEventQueryPO1.setStatus((byte) 1);
        evEventQueryPO1.setUpDown((byte) 1);
        evEventQueryPO1.setCreateTime(new Date());
        evEventQueryPO1.setName(evFrontendEventPO.getName());
        evEventQueryMapper.updateByPrimaryKeySelective(evEventQueryPO1);*/
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
            EvEventTimePO evEventTimePO2 = new EvEventTimePO();
            //原来有的数组
            //原来有的数组
            String array1[] = evEventTimePO.getTimeInterval().split(",");
            //新添加的数组
            String array2[] = dto.getInterval();
            //合并数组
            String[] result = Arrays.copyOf(array1, array1.length + array2.length);
            System.arraycopy(array2, 0, result, array1.length, array2.length);
            //去重
            HashSet<String> set = new HashSet();
            for (String i : result) {
                set.add(i);
            }
            String[] newArray = set.toArray(new String[1]);
            String newTimeinterval = String.join(",", newArray);
            evEventTimePO2.setId(evEventTimePO.getId());
            evEventTimePO2.setTimeInterval(newTimeinterval);
            evEventTimeMapper.updateByPrimaryKeySelective(evEventTimePO2);
        }
        return new ResultVO(1000);
    }

    @Override
    public ResultVO pageQuery(Map<String, Object> reqData) {
        Page<EvFrontendEventBO> page = PageQueryUtil.startPage(reqData);
        List<EvFrontendEventBO> evFrontendEventBOS = evFrontendEventMapper.pageQuery(reqData);
        return new ResultVO(1000, new PageInfoVO<>(page.getTotal(), TalentActivitiesVO.convert(evFrontendEventBOS)));
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
        Map<String,Object> reqDate = new HashMap<>(1);
        reqData.put("efid",evFrontendEventPO.getEfId().toString());
        reqData.put("date",DateUtil.date2Str(evFrontendEventPO.getEventDate(),YMD));
        EvEventTimePO evEventTimePO = evEventTimeMapper.queryByPlaceAndDate(reqData);
        List<String> list=Arrays.asList(evEventTimePO.getTimeInterval().split(","));
        List<String> arrayList=new ArrayList<String>(list);//转换为ArrayLsit调用相关的remove方法
        //查出当前活动的时间段
        EvFrontendEventPO EvFrontendEventPO1 = evFrontendEventMapper.selectByPrimaryKey(Long.parseLong(reqData.get("feid").toString()));
        String[] thisInterval = EvFrontendEventPO1.getTimeInterval().split(",");
        //从以前的时间段将现在的时间段删掉
        for(int i=0;i<thisInterval.length;i++)
            if(arrayList.contains(thisInterval[i])){
                arrayList.remove(thisInterval[i]);
            }
        //将新的arraylist转为数组
        String[] newIntervalArray= (String[]) arrayList.toArray();
        String newInterval= StringUtils.join(newIntervalArray,",");
        //将新的时间段更新会时间占用表中
        evEventTimePO.setTimeInterval(newInterval);
        evEventTimeMapper.updateByPrimaryKeySelective(evEventTimePO);
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
        EvFrontendEventPO evFrontendEventPO= evFrontendEventMapper.selectByPrimaryKey(Long.parseLong(reqData.get("feid").toString()));
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
        String time=DateUtil.date2Str(dto.getStime(),YMD_HMS)+" ~ "+DateUtil.date2Str(dto.getEtime(),YMD_HMS);
        po.setTime(time);
        po.setDuration(dto.getDuration());
        po.setSponsor(dto.getSponsor());
        po.setDetail(dto.getDetail());
        po.setContactPerson(dto.getCperson());
        po.setPhone(dto.getPhone());
        po.setPicture(dto.getPicture());
        po.setTalentId(dto.getTid());
        po.setOpenId(dto.getOid());
        po.setStatus((byte) 1);
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
