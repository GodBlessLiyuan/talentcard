package com.talentcard.web.service.impl;

import com.github.pagehelper.Page;
import com.talentcard.common.config.FilePathConfig;
import com.talentcard.common.mapper.PolicyMapper;
import com.talentcard.common.pojo.PolicyPO;
import com.talentcard.common.utils.FileUtil;
import com.talentcard.common.utils.PageHelper;
import com.talentcard.common.vo.PageInfoVO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.constant.OpsRecordMenuConstant;
import com.talentcard.web.dto.PolicyDTO;
import com.talentcard.web.service.ILogService;
import com.talentcard.web.service.IPolicyService;
import com.talentcard.web.utils.PolicyNameUtil;
import com.talentcard.web.vo.PolicyDetailVO;
import com.talentcard.web.vo.PolicyVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author: xiahui
 * @date: Created in 2020/4/14 14:22
 * @description: 人才政策管理
 * @version: 1.0
 */
@Service
public class PolicyServiceImpl implements IPolicyService {
    @Resource
    private PolicyMapper policyMapper;
    @Autowired
    private FilePathConfig filePathConfig;
    @Autowired
    private ILogService logService;
    @Override
    public ResultVO query(int pageNum, int pageSize, Map<String, Object> reqMap) {
        Page<PolicyPO> page = PageHelper.startPage(pageNum, pageSize);
        List<PolicyPO> pos = policyMapper.query(reqMap);
        return new ResultVO<>(1000, new PageInfoVO<>(page.getTotal(), PolicyVO.convert(pos)));
    }

    @Override
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

        PolicyPO po = buildPOByDTO(new PolicyPO(), dto);
        po.setUserId((Long) session.getAttribute("userId"));
        po.setCreateTime(new Date());
        po.setDr((byte) 1);

        policyMapper.insert(po);
        logService.insertActionRecord(session, OpsRecordMenuConstant.F_TalentPolicyManager,OpsRecordMenuConstant.S_PolicyManager
                ,"新增政策\"%s\"", PolicyNameUtil.getNameNumber(po));
        return new ResultVO(1000);
    }

    @Override
    public ResultVO update(HttpSession session, PolicyDTO dto) {
        //从session中获取userId的值
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            // 用户过期
            return ResultVO.notLogin();
        }
        PolicyPO po = policyMapper.selectByPrimaryKey(dto.getPid());
        if (null == po) {
            // 数据已被删除
            return new ResultVO(1001);
        }
        policyMapper.updateByPrimaryKey(buildPOByDTO(po, dto));
        logService.insertActionRecord(session, OpsRecordMenuConstant.F_TalentPolicyManager,OpsRecordMenuConstant.S_PolicyManager,
                "编辑政策\"%s\"", PolicyNameUtil.getNameNumber(po));
        return new ResultVO(1000);
    }

    @Override
    public ResultVO delete(HttpSession session,Long pid) {
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
        logService.insertActionRecord(session,OpsRecordMenuConstant.F_TalentPolicyManager,OpsRecordMenuConstant.S_PolicyManager,
                "删除政策\"%s\"",PolicyNameUtil.getNameNumber(policyPO));
        return new ResultVO(1000);
    }

    @Override
    public ResultVO detail(Long pid) {
        PolicyPO po = policyMapper.selectByPrimaryKey(pid);
        if (null == po) {
            // 数据已被删除
            return new ResultVO(1001);
        }

        return new ResultVO<>(1000, PolicyDetailVO.convert(po));
    }

    @Override
    public ResultVO upload(HttpSession session,MultipartFile file) {
        //从session中获取userId的值
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            // 用户过期
            return ResultVO.notLogin();
        }
        String picture = FileUtil.uploadFile(file, filePathConfig.getLocalBasePath(), filePathConfig.getProjectDir(), filePathConfig.getAnnexDir(), "annex");
        logService.insertActionRecord(session,OpsRecordMenuConstant.F_TalentPolicyManager,OpsRecordMenuConstant.S_PolicyManager,
                "%s上传了政策文件",(String) session.getAttribute("username"));
        return new ResultVO<>(1000, filePathConfig.getPublicBasePath() + picture);
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
}
