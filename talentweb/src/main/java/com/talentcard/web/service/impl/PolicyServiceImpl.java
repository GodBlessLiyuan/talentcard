package com.talentcard.web.service.impl;

import com.github.pagehelper.Page;
import com.talentcard.common.bo.PolicyQueryBO;
import com.talentcard.common.config.FilePathConfig;
import com.talentcard.common.mapper.*;
import com.talentcard.common.pojo.PoSettingPO;
import com.talentcard.common.pojo.PoTypePO;
import com.talentcard.common.pojo.PolicyPO;
import com.talentcard.common.utils.DateUtil;
import com.talentcard.common.utils.FileUtil;
import com.talentcard.common.utils.PageHelper;
import com.talentcard.common.vo.PageInfoVO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.constant.OpsRecordMenuConstant;
import com.talentcard.web.dto.PolicyDTO;
import com.talentcard.web.service.IBestPolicyToTalentService;
import com.talentcard.web.service.ILogService;
import com.talentcard.web.service.IPolicyService;
import com.talentcard.web.utils.PolicyNameUtil;
import com.talentcard.web.vo.PolicyDetailVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author: xiahui
 * @date: Created in 2020/4/14 14:22
 * @description: 人才政策管理
 * @version: 1.0
 */
@EnableTransactionManagement
@Service
public class PolicyServiceImpl implements IPolicyService {
    @Resource
    private PolicyMapper policyMapper;
    @Autowired
    private FilePathConfig filePathConfig;
    @Autowired
    private ILogService logService;
    @Autowired
    private PoSettingMapper poSettingMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private PoComplianceMapper poComplianceMapper;
    @Autowired
    private IBestPolicyToTalentService iBestPolicyToTalentService;
    @Autowired
    private PoTypeMapper poTypeMapper;

    @Override
    public ResultVO query(int pageNum, int pageSize, HashMap<String, Object> hashMap) {
        Page<PolicyQueryBO> page = PageHelper.startPage(pageNum, pageSize);
        List<PolicyQueryBO> policyQueryBOList = policyMapper.policyQuery(hashMap);
        policyQueryBOList = PolicyQueryBO.setUpStatus(policyQueryBOList);
        return new ResultVO<>(1000, new PageInfoVO<>(page.getTotal(), policyQueryBOList));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO insert(HttpSession session, PolicyDTO dto) {
        //从session中获取userId的值
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            // 用户过期
            return ResultVO.notLogin();
        }
        PolicyPO existPO = policyMapper.queryByNum(dto.getNum());
        if (null != existPO) {
            // 编号不能重复
            return new ResultVO(2422);
        }

        //新建policy表
        PolicyPO po = buildPOByDTO(new PolicyPO(), dto);
        po.setUserId((Long) session.getAttribute("userId"));
        po.setCreateTime(new Date());
        po.setDr((byte) 1);
        policyMapper.add(po);
        //新建setting表
        Long policyId = po.getPolicyId();
        insertPolicySetting(dto, policyId);

        logService.insertActionRecord(session, OpsRecordMenuConstant.F_TalentPolicyManager, OpsRecordMenuConstant.S_PolicyManager
                , "新增政策\"%s\"", PolicyNameUtil.getNameNumber(po));

        return new ResultVO(1000);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO update(HttpSession session, PolicyDTO dto) {
        //从session中获取userId的值
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            // 用户过期
            return ResultVO.notLogin();
        }
        Long policyId = dto.getPid();
        PolicyPO po = policyMapper.selectByPrimaryKey(policyId);
        if (null == po) {
            // 数据已被删除
            return new ResultVO(1001);
        }
        policyMapper.updateByPrimaryKeySelective(buildPOByDTO(po, dto));
        //新建setting表
        poSettingMapper.deleteByPolicyId(policyId);
        insertPolicySetting(dto, policyId);

        logService.insertActionRecord(session, OpsRecordMenuConstant.F_TalentPolicyManager, OpsRecordMenuConstant.S_PolicyManager,
                "编辑政策\"%s\"", PolicyNameUtil.getNameNumber(po));
        return new ResultVO(1000);
    }

    @Override
    public ResultVO delete(HttpSession session, Long pid) {
        //从session中获取userId的值
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            // 用户过期
            return ResultVO.notLogin();
        }
        PolicyPO policyPO = policyMapper.selectByPrimaryKey(pid);
        if (policyPO == null) {
            return new ResultVO(2402, "查无此政策！");
        }
        policyPO.setDr((byte) 2);
        policyMapper.updateByPrimaryKey(policyPO);
        logService.insertActionRecord(session, OpsRecordMenuConstant.F_TalentPolicyManager, OpsRecordMenuConstant.S_PolicyManager,
                "删除政策\"%s\"", PolicyNameUtil.getNameNumber(policyPO));
        return new ResultVO(1000);
    }

    @Override
    public ResultVO detail(Long pid) {
        PolicyPO po = policyMapper.selectByPrimaryKey(pid);
        if (null == po) {
            // 数据已被删除
            return new ResultVO(1001);
        }
        PolicyDetailVO policyDetailVO = PolicyDetailVO.convert(po);
//        RolePO rolePO = roleMapper.selectByPrimaryKey(po.getRoleId());
//        if (rolePO != null) {
//            //将roleId转换为名称，即责任单位名称
//            policyDetailVO.setResponsibleUnit(rolePO.getName());
//        }
        //计算符合条件人数
        Long meetConditionNumber = poComplianceMapper.countMeetConditionNumber(pid);
        policyDetailVO.setMeetConditionNumber(meetConditionNumber);
        return new ResultVO<>(1000, policyDetailVO);
    }

    @Override
    public ResultVO upload(HttpSession session, MultipartFile file) {
        //从session中获取userId的值
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            // 用户过期
            return ResultVO.notLogin();
        }
        String picture = FileUtil.uploadFile(file, filePathConfig.getLocalBasePath(), filePathConfig.getProjectDir(), filePathConfig.getAnnexDir(), "annex");
        logService.insertActionRecord(session, OpsRecordMenuConstant.F_TalentPolicyManager, OpsRecordMenuConstant.S_PolicyManager,
                "%s上传了政策文件", (String) session.getAttribute("username"));
        return new ResultVO<>(1000, filePathConfig.getPublicBasePath() + picture);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVO upDown(HttpSession session, Long policyId, Byte upDown) {
        PolicyPO policyPO = policyMapper.selectByPrimaryKey(policyId);
        if (policyPO == null || upDown == null) {
            return new ResultVO<>(2740, "无此政策！");
        }
        if (upDown == 1) {
            PoTypePO poTypePO = poTypeMapper.selectByPrimaryKey(policyPO.getPTid());
            if (poTypePO == null) {
                return new ResultVO<>(2744, "无此政策大类！");
            }
            if (poTypePO.getStatus() == 2) {
                return new ResultVO<>(2745, "该政策的政策大类已下架，所以无法上架此政策！");
            }
        }
        policyPO.setUpDown(upDown);
        policyMapper.updateByPrimaryKeySelective(policyPO);
        if (upDown == 1) {
            logService.insertActionRecord(session, OpsRecordMenuConstant.F_TalentPolicyManager, OpsRecordMenuConstant.S_PolicyManager,
                    "上架政策\"%s\"", policyPO.getName()+":"+policyPO.getNum());
        } else if (upDown == 2) {
            logService.insertActionRecord(session, OpsRecordMenuConstant.F_TalentPolicyManager, OpsRecordMenuConstant.S_PolicyManager,
                    "下架政策\"%s\"", policyPO.getName()+":"+policyPO.getNum());
        }

        /**
         * 重新计算适配的人群
         */
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
            @Override
            public void afterCommit() {
                iBestPolicyToTalentService.asynBestPolicy();
            }
        });
        return new ResultVO(1000);
    }

    /**
     * 根据 dto 构建 po
     *
     * @param po
     * @param dto
     */
    private PolicyPO buildPOByDTO(PolicyPO po, PolicyDTO dto) {
        po.setName(dto.getName());
        po.setNum(dto.getNum());
        po.setDescription(dto.getDesc());
        po.setCards(String.join(",", dto.getCardIds()));
        po.setCategories(String.join(",", dto.getCategoryIds()));
        po.setEducations(String.join(",", dto.getEducIds()));
        po.setTitles(String.join(",", dto.getTitleIds()));
        po.setQualities(String.join(",", dto.getQualityIds()));
        po.setBank(dto.getBank());
        po.setAnnex(dto.getAnnex());
        po.setDescription(dto.getDesc());
        String honour = listLongToString(dto.getTalentHonourIds(), ",");
        po.setHonourIds(honour);
        po.setColor(dto.getColor());
        po.setAnnexInfo(dto.getInfo());
        if (!StringUtils.isEmpty(dto.getForm())) {
            po.setApplyForm(dto.getForm().split(FilePathConfig.getStaticPublicBasePath())[1]);
        }
        po.setFunds(dto.getFunds());

        po.setPTid(dto.getPolicyType());
        po.setRoleId(dto.getRoleId());
        po.setIfSocialSecurity(dto.getIfSocialSecurity());
        po.setSocialArea(dto.getSocialArea());
        po.setSocialTimes(dto.getSocialTimes());
        po.setSocialUnit(dto.getSocialUnit());
        po.setFundsForm(dto.getFundsForm());
        po.setDeclarationTarget(dto.getDeclarationTarget());
        Date start = DateUtil.str2Date(dto.getStartTime() + " 00:00:00", DateUtil.YMD_HMS);
        Date end = DateUtil.str2Date(dto.getEndTime() + " 23:59:59", DateUtil.YMD_HMS);
        po.setStartTime(start);
        po.setEndTime(end);
        po.setApplyMaterials(dto.getApplyMaterials());
        po.setBonus(dto.getBonus());
        po.setBusinessProcess(dto.getBusinessProcess());
        po.setPhone(dto.getPhone());
        po.setUpDown((byte) 2);
        po.setUpdateTime(new Date());
        return po;
    }

    /**
     * List<Long>轉化成String
     *
     * @param longs
     * @param sign
     * @return
     */
    private String listLongToString(Long[] longs, String sign) {
        StringBuffer sb = new StringBuffer();
        int le = longs.length;
        if (le == 0) {
            return null;
        }
        for (int i = 0; i < le - 1; i++) {
            sb.append(longs[i]).append(",");
        }
        sb.append(longs[le - 1]);
        return sb.toString();
    }

    /**
     * 新建setting表
     *
     * @param policyDTO
     * @param policyId
     */
    private void insertPolicySetting(PolicyDTO policyDTO, Long policyId) {
//        新建setting表
        PoSettingPO policySettingPO;
        if (policyDTO.getCardIds() != null) {
            for (String cardId : policyDTO.getCardIds()) {
                policySettingPO = new PoSettingPO();
                policySettingPO.setCardId(Long.parseLong(cardId));
                policySettingPO.setPolicyId(policyId);
                policySettingPO.setType((byte) 1);
                poSettingMapper.insert(policySettingPO);
            }
        }
        if (policyDTO.getCategoryIds() != null) {
            for (String categoryId : policyDTO.getCategoryIds()) {
                policySettingPO = new PoSettingPO();
                policySettingPO.setCategoryId(Long.parseLong(categoryId));
                policySettingPO.setPolicyId(policyId);
                policySettingPO.setType((byte) 2);
                poSettingMapper.insert(policySettingPO);
            }
        }
        if (policyDTO.getEducIds() != null) {
            for (String education : policyDTO.getEducIds()) {
                policySettingPO = new PoSettingPO();
                policySettingPO.setEducationId(Integer.parseInt(education));
                policySettingPO.setPolicyId(policyId);
                policySettingPO.setType((byte) 3);
                poSettingMapper.insert(policySettingPO);
            }
        }
        if (policyDTO.getTitleIds() != null) {
            for (String title : policyDTO.getTitleIds()) {
                policySettingPO = new PoSettingPO();
                policySettingPO.setTitleId(Integer.parseInt(title));
                policySettingPO.setPolicyId(policyId);
                policySettingPO.setType((byte) 4);
                poSettingMapper.insert(policySettingPO);
            }
        }
        if (policyDTO.getQualityIds() != null) {
            for (String quality : policyDTO.getQualityIds()) {
                policySettingPO = new PoSettingPO();
                policySettingPO.setQuality(Integer.parseInt(quality));
                policySettingPO.setPolicyId(policyId);
                policySettingPO.setType((byte) 5);
                poSettingMapper.insert(policySettingPO);
            }
        }
        if (policyDTO.getTalentHonourIds() != null) {
            for (Long honour : policyDTO.getTalentHonourIds()) {
                policySettingPO = new PoSettingPO();
                policySettingPO.setHonourId(honour);
                policySettingPO.setPolicyId(policyId);
                policySettingPO.setType((byte) 6);
                poSettingMapper.insert(policySettingPO);
            }
        }
    }
}
