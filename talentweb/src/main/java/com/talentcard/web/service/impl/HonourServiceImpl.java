package com.talentcard.web.service.impl;

import com.github.pagehelper.Page;
import com.talentcard.common.constant.TalentConstant;
import com.talentcard.common.mapper.HonourMapper;
import com.talentcard.common.pojo.HonourPO;
import com.talentcard.common.utils.PageHelper;
import com.talentcard.common.utils.redis.RedisMapUtil;
import com.talentcard.common.vo.PageInfoVO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.constant.OpsRecordMenuConstant;
import com.talentcard.web.service.IHonourService;
import com.talentcard.web.service.ILogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-08-04 09:58
 * @description
 */
@Service
public class HonourServiceImpl implements IHonourService {
    @Autowired
    private HonourMapper honourMapper;
    @Autowired
    private ILogService logService;
    @Autowired
    private RedisMapUtil redisMapUtil;

    @Override
    public ResultVO add(String name, String description, HttpSession httpSession) {
        //从session中获取userId的值
        Long userId = (Long) httpSession.getAttribute("userId");
        if (userId == null) {
            // 用户过期
            return ResultVO.notLogin();
        }
        Integer ifExistName = honourMapper.ifExistName(name);
        if (ifExistName != 0) {
            return new ResultVO(2730, "人才类别或者人才荣誉重复了！");
        }
        HonourPO honourPO = new HonourPO();
        honourPO.setName(name);
        honourPO.setDescription(description);
        honourPO.setCreateTime(new Date());
        honourPO.setUpdateTime(new Date());
        honourPO.setDr((byte) 1);
        honourPO.setStatus((byte) 2);
        honourMapper.insertSelective(honourPO);
        logService.insertActionRecord(httpSession, OpsRecordMenuConstant.F_TalentLabelManage, OpsRecordMenuConstant.S_TalentHonour,
                "新增人才荣誉\"%s\"", name);
        /**
         * 清除用户缓存
         */
        this.redisMapUtil.del(TalentConstant.TALENT_HONOUR_LIST);
        return new ResultVO(1000);
    }

    @Override
    public ResultVO edit(Long honourId, String description, HttpSession httpSession) {
        //从session中获取userId的值
        Long userId = (Long) httpSession.getAttribute("userId");
        if (userId == null) {
            // 用户过期
            return ResultVO.notLogin();
        }
        HonourPO honourPO = honourMapper.selectByPrimaryKey(honourId);
        if (honourPO == null) {
            return new ResultVO(2720, "查无此人才荣誉！");
        }
        honourPO.setDescription(description);
        honourPO.setUpdateTime(new Date());
        honourMapper.updateByPrimaryKeySelective(honourPO);
        logService.insertActionRecord(httpSession, OpsRecordMenuConstant.F_TalentLabelManage, OpsRecordMenuConstant.S_TalentHonour,
                "编辑人才荣誉\"%s\"", honourPO.getName());
        /**
         * 清除用户缓存
         */
        this.redisMapUtil.del(TalentConstant.TALENT_HONOUR_LIST);
        return new ResultVO(1000);
    }

    @Override
    public ResultVO upDown(Long honourId, Byte status, HttpSession httpSession) {
        //从session中获取userId的值
        Long userId = (Long) httpSession.getAttribute("userId");
        if (userId == null) {
            // 用户过期
            return ResultVO.notLogin();
        }
        HonourPO honourPO = honourMapper.selectByPrimaryKey(honourId);
        if (honourPO == null) {
            return new ResultVO(2720, "查无此人才荣誉！");
        }
        honourPO.setStatus(status);
        honourPO.setUpdateTime(new Date());
        honourMapper.updateByPrimaryKeySelective(honourPO);
        if (status == 1) {
            logService.insertActionRecord(httpSession, OpsRecordMenuConstant.F_TalentLabelManage, OpsRecordMenuConstant.S_TalentHonour,
                    "上架人才荣誉\"%s\"", honourPO.getName());
        } else {
            logService.insertActionRecord(httpSession, OpsRecordMenuConstant.F_TalentLabelManage, OpsRecordMenuConstant.S_TalentHonour,
                    "下架人才荣誉\"%s\"", honourPO.getName());
        }
        /**
         * 清除用户缓存
         */
        this.redisMapUtil.del(TalentConstant.TALENT_HONOUR_LIST);
        return new ResultVO(1000);
    }

    @Override
    public ResultVO query(int pageNum, int pageSize, String name, Byte status, Byte type) {
        Page<HonourPO> page = PageHelper.startPage(pageNum, pageSize);
        List<HonourPO> honourPOList = honourMapper.query(name, status, type);
        return new ResultVO<>(1000, new PageInfoVO<>(page.getTotal(), honourPOList));
    }

    @Override
    public ResultVO findOne(Long honourId) {
        HonourPO honourPO = honourMapper.selectByPrimaryKey(honourId);
        if (honourPO == null) {
            return new ResultVO(2720, "查无此人才荣誉！");
        }
        return new ResultVO(1000, honourPO);
    }
}
