package com.talentcard.web.service.impl;

import com.github.pagehelper.Page;
import com.talentcard.common.mapper.ScenicMapper;
import com.talentcard.common.mapper.StaffMapper;
import com.talentcard.common.pojo.StaffPO;
import com.talentcard.common.utils.PageHelper;
import com.talentcard.common.vo.PageInfoVO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.constant.OpsRecordMenuConstant;
import com.talentcard.web.service.ILogService;
import com.talentcard.web.service.IStaffService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-05-11 11:44
 * @description
 */
@Service
public class StaffServiceImpl implements IStaffService {
    private static final Logger logger = LoggerFactory.getLogger(StaffServiceImpl.class);
    @Autowired
    private StaffMapper staffMapper;
    @Autowired
    private ILogService logService;
    @Override
    public ResultVO query(int pageNum, int pageSize, HashMap<String, Object> hashMap) {
        Page<StaffPO> page = PageHelper.startPage(pageNum, pageSize);
        List<StaffPO> staffPOList = staffMapper.findStaffByFactor(hashMap);
        return new ResultVO(1000, new PageInfoVO<>(page.getTotal(), staffPOList));
    }

    @Override
    public ResultVO delete(HttpSession session,Long staffId) {
        //从session中获取userId的值
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            // 用户过期
            return ResultVO.notLogin();
        }
        StaffPO staffPO = staffMapper.selectByPrimaryKey(staffId);
        if (staffPO == null) {
            return new ResultVO(2503, "没有此员工");
        }
        staffPO.setDr((byte) 2);
        int updateResult = staffMapper.updateByPrimaryKeySelective(staffPO);
        if (updateResult == 0) {
            logger.error("update staffMapper error");
        }
        logService.insertActionRecord(session, OpsRecordMenuConstant.F_ExternalFunction,OpsRecordMenuConstant.S_Staff_Config,
                "删除员工\"%s\"",staffPO.getName());
        return new ResultVO(1000);
    }
}
