package com.talentcard.front.service.impl;

import com.talentcard.common.bo.PolicyApplyBO;
import com.talentcard.common.bo.queryPolicyByTalentIdBO;
import com.talentcard.common.config.FilePathConfig;
import com.talentcard.common.mapper.*;
import com.talentcard.common.pojo.*;
import com.talentcard.common.utils.FileUtil;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.front.dto.PolicyApplyDTO;
import com.talentcard.front.service.IPolicyService;
import com.talentcard.front.vo.PolicyAppliesVO;
import com.talentcard.front.vo.PolicyApplyDetailVO;
import com.talentcard.front.vo.PolicyDetailVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author: xiahui
 * @date: Created in 2020/4/9 16:55
 * @description: 政策权益
 * @version: 1.0
 */
@EnableTransactionManagement
@Service
public class PolicyServiceImpl implements IPolicyService {
    @Resource
    private PolicyApplyMapper policyApplyMapper;
    @Resource
    private TalentMapper talentMapper;
    @Resource
    private PolicyMapper policyMapper;
    @Resource
    private EducationMapper educationMapper;
    @Resource
    private ProfTitleMapper profTitleMapper;
    @Resource
    private ProfQualityMapper profQualityMapper;
    @Autowired
    private TalentHonourMapper talentHonourMapper;
    @Resource
    private PolicyApprovalMapper policyApprovalMapper;
    @Resource
    private BankMapper bankMapper;
    @Resource
    private AnnexMapper annexMapper;
    @Autowired
    private FilePathConfig filePathConfig;
    @Autowired
    private PoComplianceMapper poComplianceMapper;

    @Override
    public ResultVO policies(String openid) {
        TalentPO talentPO = talentMapper.queryByOpenid(openid);
        if (null == talentPO) {
            // 人才不存在或已被删除
            return new ResultVO(1001);
        }
        if (talentPO.getStatus() == 2) {
            // 当前人才尚未认证
            return new ResultVO(1002);
        }

        Long talentId = talentPO.getTalentId();
        List<Integer> existEducations = educationMapper.queryNameByTalentId(talentId);
        List<Integer> existTitles = profTitleMapper.queryNameByTalentId(talentId);
        List<Integer> existQualities = profQualityMapper.queryNameByTalentId(talentId);
        List<Long> existHonours = talentHonourMapper.queryNameByTalentId(talentId);
        String[] existCategories = null;
        if (null != talentPO.getCategory()) {
            existCategories = talentPO.getCategory().split(",");
        }

        Long existCardId = talentPO.getCardId();

        List<PolicyPO> pos = policyMapper.queryByDr((byte) 1);
        List<PolicyPO> showPOs = new ArrayList<>();
        for (PolicyPO po : pos) {
            String[] cardIds = null;
            String[] categories = null;
            String[] educations = null;
            String[] titles = null;
            String[] qualities = null;
            String[] honourIds = null;
            if (null != po.getCards()) {
                cardIds = po.getCards().split(",");
            }
            if (null != po.getCategories()) {
                categories = po.getCategories().split(",");
            }
            if (null != po.getEducations()) {
                educations = po.getEducations().split(",");
            }
            if (null != po.getTitles()) {
                titles = po.getTitles().split(",");
            }
            if (null != po.getQualities()) {
                qualities = po.getQualities().split(",");
            }
            if (null != po.getHonourIds()) {
                honourIds = po.getHonourIds().split(",");
            }

            boolean show = false;
            if (null != cardIds) {
                for (String cardId : cardIds) {
                    if (!StringUtils.isEmpty(cardId) && existCardId.toString().equals(cardId)) {
                        show = true;
                        break;
                    }
                }
            }
            if (!show && null != categories && null != existCategories) {
                for (String category : categories) {
                    if (!show) {
                        for (String existCategory : existCategories) {
                            if (!StringUtils.isEmpty(existCategory) && category.equals(existCategory)) {
                                show = true;
                                break;
                            }
                        }
                    }
                }
            }
            if (!show && null != educations) {
                for (String educ : educations) {
                    if (!StringUtils.isEmpty(educ) && existEducations.toString().contains(educ)) {
                        show = true;
                        break;
                    }
                }
            }
            if (!show && null != titles) {
                for (String title : titles) {
                    if (!StringUtils.isEmpty(title) && existTitles.toString().contains(title)) {
                        show = true;
                        break;
                    }
                }
            }
            if (!show && null != qualities) {
                for (String quality : qualities) {
                    if (!StringUtils.isEmpty(quality) && existQualities.toString().contains(quality)) {
                        show = true;
                        break;
                    }
                }
            }

            if (!show && null != honourIds) {
                for (String honourId : honourIds) {
                    if (!StringUtils.isEmpty(honourId) && existHonours.toString().contains(honourId)) {
                        show = true;
                        break;
                    }
                }
            }

            if (show) {
                showPOs.add(po);
            }
        }

        List<PolicyDetailVO> vos = new ArrayList<>();
        for (PolicyPO po : showPOs) {
            PolicyDetailVO vo = new PolicyDetailVO();
            vo.setPid(po.getPolicyId());
            vo.setName(po.getName());
            vo.setTname(talentPO.getName());
            vo.setDesc(po.getDescription());
            vo.setBank(po.getBank());
            vo.setAnnex(po.getAnnex());
            vo.setApply(po.getApply());
            vo.setColor(po.getColor());
            vo.setInfo(po.getAnnexInfo());
            if (!StringUtils.isEmpty(po.getApplyForm())) {
                vo.setForm(filePathConfig.getPublicBasePath() + po.getApplyForm());
            }

            if (null != po.getApply() && po.getApply() == 1) {
                // 根据频次计算相隔月份
                int month = 0;
                if (null != po.getUnit() && null != po.getRate() && null != po.getTimes()) {
                    if (po.getUnit() == 1) {
                        month = po.getRate() * 12;  // 年
                    } else if (po.getUnit() == 2) {
                        month = po.getRate() * 3;   // 季
                    } else if (po.getUnit() == 3) {
                        month = po.getRate();       // 月
                    }
                }

                List<PolicyApplyPO> applyPOs = policyApplyMapper.queryByTidAndPidAndMonth(talentId, po.getPolicyId(), month);
                if (null == applyPOs || applyPOs.size() == 0) {
                    vo.setRight((byte) 1);
                } else {
                    int applyNum = 0;
                    for (PolicyApplyPO applyPO : applyPOs) {
                        if (applyPO.getStatus() == 3) {
                            vo.setRight((byte) 2);
                            break;
                        } else if (applyPO.getStatus() == 1) {
                            applyNum++;
                        }
                    }

                    if (null == vo.getRight()) {
                        if (null == po.getUnit()) {
                            vo.setRight((byte) 1);
                        } else {
                            if (applyNum >= po.getTimes()) {
                                vo.setRight((byte) 3);
                                vo.setTitle(genTitle(po));
                            } else {
                                vo.setRight((byte) 1);
                            }
                        }
                    }
                }
            }

            vos.add(vo);
        }

        return new ResultVO<>(1000, vos);
    }

    /**
     * 生成提示文案
     *
     * @param po
     * @return
     */
    private String genTitle(PolicyPO po) {
        StringBuffer sb = new StringBuffer();
        sb.append("当前政策");
        if (po.getUnit() == 4) {
            sb.append("终身");
        } else {
            if (po.getRate() == 1) {
                sb.append("每");
            } else {
                sb.append(po.getRate());
            }

            sb.append(po.getUnit() == 1 ? "年" : po.getUnit() == 2 ? "季" : "月");
        }

        sb.append("仅能申请");
        sb.append(po.getTimes());
        sb.append("次，请勿重复申请");

        return sb.toString();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultVO apply(PolicyApplyDTO dto) {
        TalentPO talentPO = talentMapper.queryByOpenid(dto.getOpenid());
        if (null == talentPO) {
            // 当前人才不存在或已被删除
            return new ResultVO(1001);
        }
        PolicyPO policyPO = policyMapper.selectByPrimaryKey(dto.getPid());
        if (null == policyPO) {
            // 当前政策不存在或已被删除
            return new ResultVO(1002);
        }
//        List<PolicyApplyPO> applyPOs = policyApplyMapper.queryByTidAndPidAndMonth(talentPO.getTalentId(), dto.getPid(), null);
//        for (PolicyApplyPO applyPO : applyPOs) {
//            if (applyPO.getStatus() == 3) {
//                // 已有待审批的申请，请勿重复申请
//                return new ResultVO(1003);
//            }
//        }

        PolicyApplyPO applyPO = new PolicyApplyPO();
        applyPO.setTalentId(talentPO.getTalentId());
        applyPO.setTalentName(talentPO.getName());
        applyPO.setPolicyId(dto.getPid());
        applyPO.setPolicyName(policyPO.getName());
        applyPO.setCreateTime(new Date());
        applyPO.setStatus((byte) 3);
        policyApplyMapper.insert(applyPO);

        PolicyApprovalPO approvalPO = new PolicyApprovalPO();
        approvalPO.setPaId(applyPO.getPaId());
        approvalPO.setCreateTime(new Date());
        approvalPO.setType((byte) 1);
        policyApprovalMapper.insert(approvalPO);

        if (dto.getCard() != null && dto.getBank() != null && policyPO.getBank() == 1) {
            BankPO bankPO = new BankPO();
            bankPO.setNum(dto.getCard());
            bankPO.setName(dto.getBank());
            bankPO.setPaId(applyPO.getPaId());
            bankMapper.insert(bankPO);
        }
        if (null != dto.getFiles() && dto.getFiles().length > 0) {
            List<AnnexPO> annexPOs = new ArrayList<>();
            for (MultipartFile file : dto.getFiles()) {
                AnnexPO annexPO = new AnnexPO();
                annexPO.setName(file.getOriginalFilename());
                annexPO.setLocation(FileUtil.uploadFile(file, filePathConfig.getLocalBasePath(), filePathConfig.getProjectDir(), filePathConfig.getAnnexDir(), "annex"));
                annexPO.setPaId(applyPO.getPaId());
                annexPOs.add(annexPO);
            }
            annexMapper.batchInsert(annexPOs);
        }

        return new ResultVO(1000);
    }

    @Override
    public ResultVO applies(String openid) {
        TalentPO talentPO = talentMapper.queryByOpenid(openid);
        if (null == talentPO) {
            return new ResultVO(1001);
        }

        List<PolicyApplyPO> pos = policyApplyMapper.queryByTalentId(talentPO.getTalentId());
        return new ResultVO<>(1000, PolicyAppliesVO.convert(pos));
    }

    @Override
    public ResultVO detail(Long paid) {
        PolicyApplyBO bo = policyApplyMapper.queryDetail(paid);
        if (null == bo) {
            return new ResultVO(2000);
        }

        return new ResultVO<>(1000, PolicyApplyDetailVO.convert(bo));
    }

    @Override
    public ResultVO queryBankCardInfo(String openId) {
        List<BankPO> bankInfoList = policyMapper.queryBankCardInfo(openId);
        if (bankInfoList == null || bankInfoList.size() == 0) {
            return new ResultVO(1102);
        }

        return new ResultVO(1000, bankInfoList.get(0));
    }

    @Override
    public ResultVO policyFindOne(Long policyId) {
        PolicyPO policyPO = policyMapper.selectByPrimaryKey(policyId);
        return new ResultVO(1000, policyPO);
    }

    @Override
    public ResultVO myPolicy(String openId) {
        TalentPO talentPO = talentMapper.selectByOpenId(openId);
        if (talentPO == null) {
            return new ResultVO(2500);
        }
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        List<queryPolicyByTalentIdBO> queryPolicyByTalentIdBOList = poComplianceMapper.
                queryPolicyByTalentId(talentPO.getTalentId(), year);
        return new ResultVO(1000, queryPolicyByTalentIdBOList);
    }
}
