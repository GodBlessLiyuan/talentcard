package com.talentcard.web.service.impl;

import com.github.pagehelper.Page;
import com.talentcard.common.bo.PolicyTypeBO;
import com.talentcard.common.mapper.PoTypeExcludeMapper;
import com.talentcard.common.mapper.PoTypeMapper;
import com.talentcard.common.mapper.PolicyMapper;
import com.talentcard.common.pojo.PoTypeExcludePO;
import com.talentcard.common.pojo.PoTypePO;
import com.talentcard.common.pojo.PolicyPO;
import com.talentcard.common.utils.PageQueryUtil;
import com.talentcard.common.vo.PageInfoVO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.constant.OpsRecordMenuConstant;
import com.talentcard.web.dto.PolicyTypeDTO;
import com.talentcard.web.service.ILogService;
import com.talentcard.web.service.IPolicyTypeService;
import com.talentcard.web.vo.PolicyTypeVO;
import org.apache.commons.beanutils.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @ Author     ：AnHongxu.
 * @ Date       ：Created in 10:19 2020/8/11
 * @ Description：政策类型Service接口实现类
 * @ Modified By：
 * @ Version:     1.0
 */
@Service
public class PolicyTypeServiceImpl implements IPolicyTypeService {
    @Autowired
    private PoTypeMapper poTypeMapper;
    @Autowired
    private PoTypeExcludeMapper poTypeExcludeMapper;
    @Autowired
    private ILogService logService;
    @Autowired
    private PolicyMapper policyMapper;

    /**
     * @Description 政策类型分页查询
     * @Param [reqData]
     * @Return com.talentcard.common.vo.ResultVO
     * @Author AnHongxu.
     * @Date 2020/8/13
     * @Time 15:59
     */
    @Override
    public ResultVO pageQuery(Map<String, Object> reqData) {
        Page<PolicyTypeBO> page = PageQueryUtil.startPage(reqData);
        List<PolicyTypeBO> policyTypeBOs = poTypeMapper.pageQuery(reqData);
        //将互斥id取出来，然后进作为List查询条件查询出对应的互斥政策名称放入list中
        for (int i = 0; i < policyTypeBOs.size(); i++) {
            policyTypeBOs.get(i).setExcludeIds(Arrays.asList((Long[]) ConvertUtils.convert(policyTypeBOs.get(i).getExcludeId().split(","), Long.class)));
            //根据互斥id查询互斥政策名称放入对应的List中
            List<PolicyTypeBO> policyTypeNameList = poTypeMapper.queryPtNameByPtId(policyTypeBOs.get(i).getExcludeIds());
            //将查询出的名字取出来放入List中
            List<String> ePolicyTypeNames = new ArrayList<>();
            for (PolicyTypeBO policyTypeBOForTypeName : policyTypeNameList) {
                ePolicyTypeNames.add(policyTypeBOForTypeName.getPTypeName());
            }
            //将上面去到的互斥名字数组放入BO中
            policyTypeBOs.get(i).setExcludeNames(ePolicyTypeNames);
        }
        return new ResultVO(1000, new PageInfoVO<>(page.getTotal(), PolicyTypeVO.convert(policyTypeBOs)));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO insert(HttpSession session, PolicyTypeDTO dto) {
        //从session中获取userId的值
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            // 用户过期
            return ResultVO.notLogin();
        }
        //将前台dto中获取的数据转换成po进行插入
        PoTypePO po = buildPOByDTO(new PoTypePO(), dto);
        poTypeMapper.insertSelective(po);
        logService.insertActionRecord(session, OpsRecordMenuConstant.F_TalentPolicyManager, OpsRecordMenuConstant.S_PolicyManager
                , "新增政策类型\"%s\"", po.getPTypeName());
        //取出刚刚插入的政策类型数据的政策类型id
        Long ptId = po.getPTid();
        //构造互斥表中的数据进行插入
        PoTypeExcludePO poTypeExcludePO = new PoTypeExcludePO();
        for (int i = 0; i < dto.getEids().length; i++) {
            poTypeExcludePO.setPTid1(ptId);
            poTypeExcludePO.setPTid2(Long.parseLong(dto.getEids()[i]));
            poTypeExcludeMapper.insert(poTypeExcludePO);
            logService.insertActionRecord(session, OpsRecordMenuConstant.F_TalentPolicyManager, OpsRecordMenuConstant.S_PolicyManager
                    , "互斥表新增数据进行的插入\"%s\"", poTypeExcludePO.getPTid1().toString() + ";" + poTypeExcludePO.getPTid2().toString());
            poTypeExcludePO.setPTid1(Long.parseLong(dto.getEids()[i]));
            poTypeExcludePO.setPTid2(ptId);
            poTypeExcludeMapper.insert(poTypeExcludePO);
            logService.insertActionRecord(session, OpsRecordMenuConstant.F_TalentPolicyManager, OpsRecordMenuConstant.S_PolicyManager
                    , "互斥表新增数据进行的插入\"%s\"", poTypeExcludePO.getPTid1().toString() + ";" + poTypeExcludePO.getPTid2().toString());
        }
        return new ResultVO(1000);
    }

    @Override
    public ResultVO queryExIdAndName(Map<String, Object> reqData) {
        Page<PolicyTypeBO> page = PageQueryUtil.startPage(reqData);
        List<PolicyTypeBO> policyTypeBOs = poTypeMapper.queryExIdAndName();
        return new ResultVO(1000, new PageInfoVO<>(page.getTotal(), PolicyTypeVO.convert(policyTypeBOs)));
    }

    @Override
    public ResultVO update(HttpSession session, PolicyTypeDTO dto) {
        //从session中获取userId的值
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            // 用户过期
            return ResultVO.notLogin();
        }
        //将前台dto中更改的数据转换成po进行更新
        PoTypePO po = buildUpdatePOByDTO(new PoTypePO(), dto);
        poTypeMapper.updateByPrimaryKeySelective(po);
        logService.insertActionRecord(session, OpsRecordMenuConstant.F_TalentPolicyManager, OpsRecordMenuConstant.S_PolicyManager
                , "政策类型数据更新\"%s\"", po.toString());
        //取出刚刚插入的政策类型数据的政策类型id
        Long ptId = po.getPTid();
        //更新后删除互斥表中的关联数据
        poTypeExcludeMapper.delete(ptId);
        logService.insertActionRecord(session, OpsRecordMenuConstant.F_TalentPolicyManager, OpsRecordMenuConstant.S_PolicyManager
                , "互斥表中删除更新的关联数据\"%s\"", ptId.toString());
        //构造互斥表中的数据进行插入
        PoTypeExcludePO poTypeExcludePO = new PoTypeExcludePO();
        for (int i = 0; i < dto.getEids().length; i++) {
            poTypeExcludePO.setPTid1(ptId);
            poTypeExcludePO.setPTid2(Long.parseLong(dto.getEids()[i]));
            poTypeExcludeMapper.insert(poTypeExcludePO);
            logService.insertActionRecord(session, OpsRecordMenuConstant.F_TalentPolicyManager, OpsRecordMenuConstant.S_PolicyManager
                    , "互斥表新增数据进行的插入\"%s\"", poTypeExcludePO.getPTid1().toString() + ";" + poTypeExcludePO.getPTid2().toString());
            poTypeExcludePO.setPTid1(Long.parseLong(dto.getEids()[i]));
            poTypeExcludePO.setPTid2(ptId);
            poTypeExcludeMapper.insert(poTypeExcludePO);
            logService.insertActionRecord(session, OpsRecordMenuConstant.F_TalentPolicyManager, OpsRecordMenuConstant.S_PolicyManager
                    , "互斥表新增数据进行的插入\"%s\"", poTypeExcludePO.getPTid1().toString() + ";" + poTypeExcludePO.getPTid2().toString());
        }
        //更新政策类型表中的互斥id字段，将互斥表中的关联关系进行回显
        //首先将政策类型表中的所有政策类型id查出来
        List<PolicyTypeBO> policyTypeBOs = poTypeMapper.queryExIdAndName();
        //遍历这些政策类型id，进行互斥表中的有哪些互斥id进行查询出来，并更新大搜政策类型表中
        PoTypePO poForUPdateEId = new PoTypePO();//新建pojo对象用来临时存储将要更新的数据
        for (PolicyTypeBO policyTypeBO : policyTypeBOs) {
            //根据政策类型id查询互斥表中互斥的有哪些字段
            List<PoTypeExcludePO> poTypeExcludePOS = poTypeExcludeMapper.queryExId(policyTypeBO.getPTid());
            //遍历这些互斥id，取出拼接成”，“分割的字符串
            String[] eIdsArry = new String[poTypeExcludePOS.size()];
            for (int i = 0; i < eIdsArry.length; i++) {
                eIdsArry[i] = poTypeExcludePOS.get(i).getPTid2().toString();
            }
            //查询出所有的互斥id进行拼接
            String exIds = String.join(",", eIdsArry);
            //将拼接后的互斥id更新到政策类型表中进行回显
            poForUPdateEId.setPTid(policyTypeBO.getPTid());
            poForUPdateEId.setExcludeId(exIds);
            poForUPdateEId.setUpdateTime(new Date());
            poTypeMapper.updateByPrimaryKeySelective(poForUPdateEId);
            logService.insertActionRecord(session, OpsRecordMenuConstant.F_TalentPolicyManager, OpsRecordMenuConstant.S_PolicyManager
                    , "更新政策类型表中的互斥id进行回显\"%s\"", poForUPdateEId.getExcludeId());
        }
        return new ResultVO(1000);

    }

    @Override
    public ResultVO changeStatus(HttpSession session, PolicyTypeDTO dto) {
        //从session中获取userId的值
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            // 用户过期
            return ResultVO.notLogin();
        }
        PoTypePO po = buildChangeStatusPOByDTO(new PoTypePO(), dto);
        poTypeMapper.updateByPrimaryKeySelective(po);

        /**
         * 下架时，需要执行将政策下架
         */
        if (po.getStatus() != 1) {
            Map<String, Object> map = new HashMap<>(3);
            map.put("pTid", po.getPTid());
            map.put("dr", 1);
            map.put("upDown", 1);

            List<PolicyPO> policyPOS = this.policyMapper.selectByMap(map);
            if (policyPOS != null && policyPOS.size() > 0) {
                for (PolicyPO po1 : policyPOS) {
                    po1.setUpDown((byte)2);
                    this.policyMapper.updateByPrimaryKey(po1);
                }
            }
        }

        logService.insertActionRecord(session, OpsRecordMenuConstant.F_TalentPolicyManager, OpsRecordMenuConstant.S_PolicyManager
                , "变更政策类型表数据状态\"%s\"", po.getStatus() == 1 ? "上架" : "下架");
        return new ResultVO(1000);
    }

    @Override
    public ResultVO delete(HttpSession session, PolicyTypeDTO dto) {
        //从session中获取userId的值
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            // 用户过期
            return ResultVO.notLogin();
        }
        PoTypePO po = buildDeletePOByDTO(new PoTypePO(), dto);
        //将该条数据变为删除状态
        poTypeMapper.updateByPrimaryKeySelective(po);
        logService.insertActionRecord(session, OpsRecordMenuConstant.F_TalentPolicyManager, OpsRecordMenuConstant.S_PolicyManager
                , "变更政策类型表数据为删除状态\"%s\"", po.getDr().toString());
        return new ResultVO(1000);
    }

    /**
     * 根据 dto 构建 po
     *
     * @param po
     * @param dto
     */
    private PoTypePO buildPOByDTO(PoTypePO po, PolicyTypeDTO dto) {
        po.setPTid(dto.getPtid());
        po.setPTypeName(dto.getPtname());
        po.setExcludeId(String.join(",", dto.getEids()));
        po.setStatus((byte) 2);
        po.setDr((byte) 1);
        po.setDescription(dto.getDesc());
        po.setUpdateTime(new Date());
        return po;
    }

    /**
     * 根据 dto 构建 po
     *
     * @param po
     * @param dto
     */
    private PoTypePO buildChangeStatusPOByDTO(PoTypePO po, PolicyTypeDTO dto) {
        po.setPTid(dto.getPtid());
        po.setStatus((dto.getStatus()));
        po.setUpdateTime(new Date());
        return po;
    }

    /**
     * 根据 dto 构建 po
     *
     * @param po
     * @param dto
     */
    private PoTypePO buildDeletePOByDTO(PoTypePO po, PolicyTypeDTO dto) {
        po.setPTid(dto.getPtid());
        po.setDr((byte) 1);
        po.setUpdateTime(new Date());
        return po;
    }

    /**
     * 根据 dto 构建 po
     *
     * @param po
     * @param dto
     */
    private PoTypePO buildUpdatePOByDTO(PoTypePO po, PolicyTypeDTO dto) {
        po.setPTid(dto.getPtid());
        po.setPTypeName(dto.getPtname());
        po.setExcludeId(String.join(",", dto.getEids()));
        po.setDr((byte) 1);
        po.setStatus((byte) 1);
        po.setDescription(dto.getDesc());
        po.setUpdateTime(new Date());
        return po;
    }
}

