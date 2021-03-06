package com.talentcard.front.service.impl;

import com.talentcard.common.bo.BankInfoBO;
import com.talentcard.common.bo.PolicyApplyBO;
import com.talentcard.common.bo.QueryPolicyByTalentIdBO;
import com.talentcard.common.config.FilePathConfig;
import com.talentcard.common.constant.TrackConstant;
import com.talentcard.common.mapper.*;
import com.talentcard.common.pojo.*;
import com.talentcard.common.utils.FileUtil;
import com.talentcard.common.utils.rabbit.BsnRabbitUtil;
import com.talentcard.common.utils.rabbit.RabbitUtil;
import com.talentcard.common.utils.rabbit.chaincodeEntities.Application;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.front.dto.PolicyApplyDTO;
import com.talentcard.front.service.IBestPolicyToTalentService;
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
import java.util.*;

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
    @Autowired
    private PoTypeExcludeMapper poTypeExcludeMapper;
    @Autowired
    private IBestPolicyToTalentService iBestPolicyToTalentService;
    @Autowired
    private BsnRabbitUtil bsnRabbitUtil;

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
        if (!StringUtils.isEmpty(talentPO.getCategory())) {
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
                        // 年
                        month = po.getRate() * 12;
                    } else if (po.getUnit() == 2) {
                        // 季
                        month = po.getRate() * 3;
                    } else if (po.getUnit() == 3) {
                        // 月
                        month = po.getRate();
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

        /**
         * 检查政策是否下线
         */
        if (policyPO.getUpDown() != 1) {
            return new ResultVO(1002);
        }

        /**
         * 当用户没有符合条件的政策时，提示对应政策
         */
        Date current = new Date();
        int res = updatePOCompliance(talentPO.getTalentId(), policyPO.getPolicyId(), 3, current);
        if (res == -1) {
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
        applyPO.setCreateTime(current);
        applyPO.setStatus((byte) 3);
        policyApplyMapper.insert(applyPO);

        PolicyApprovalPO approvalPO = new PolicyApprovalPO();
        approvalPO.setPaId(applyPO.getPaId());
        approvalPO.setCreateTime(current);
        approvalPO.setType((byte) 1);
        policyApprovalMapper.insert(approvalPO);

        if (dto.getCard() != null && dto.getBank() != null) {
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

        iBestPolicyToTalentService.asynBestPolicyForTalent(talentPO.getTalentId());

        /*区块链埋点*/
        String eventLog = talentPO.getName() + "申请政策\"" + policyPO.getName() + "\"";
        RabbitUtil.sendTrackMsg(TrackConstant.POLICY_TRACK, TrackConstant.POLICY_APPLY, eventLog, true);

        Application application = new Application();
        application.setId(String.format("%s_%s",applyPO.getPaId(),talentPO.getTalentId()));
        application.setUid(String.valueOf(talentPO.getTalentId()));
        application.setPid(String.valueOf(policyPO.getPolicyId()));
        application.setPolicy(policyPO.getName());
        application.setStatus(String.valueOf(TrackConstant.POLICY_APPLY));
        bsnRabbitUtil.sendApplication(application, false);

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
        List<QueryPolicyByTalentIdBO> queryPolicyByTalentIdBOList = poComplianceMapper.
                queryPolicyByTalentId(talentPO.getTalentId(), year);
        //加入颜色标签
        for (QueryPolicyByTalentIdBO queryPolicyByTalentIdBO : queryPolicyByTalentIdBOList) {
            PolicyPO policyPO = policyMapper.selectByPrimaryKey(queryPolicyByTalentIdBO.getPolicyId());
            queryPolicyByTalentIdBO.setColor(policyPO.getColor());
        }
        queryPolicyByTalentIdBOList = QueryPolicyByTalentIdBO.setValueActualStatus(queryPolicyByTalentIdBOList);
        return new ResultVO(1000, queryPolicyByTalentIdBOList);
    }

    @Override
    public ResultVO findExcludePolicy(Long policyId) {
        PolicyPO policyPO = policyMapper.selectByPrimaryKey(policyId);
        if (policyPO == null) {
            return new ResultVO(2740);
        }
        Long policyTypeId = policyPO.getPTid();
        List<String> stringList = poTypeExcludeMapper.findExcludePolicy(policyTypeId);
        return new ResultVO(1000, stringList);
    }

    @Override
    public ResultVO findBankInfo(String openId) {
        TalentPO talentPO = talentMapper.selectByOpenId(openId);
        if (talentPO == null) {
            return new ResultVO(2500);
        }
        BankInfoBO bankInfoBO = bankMapper.findBankInfo(talentPO.getTalentId());
        if (bankInfoBO == null) {
            bankInfoBO = new BankInfoBO();
        }
        bankInfoBO.setTalentName(talentPO.getName());
        return new ResultVO(1000, bankInfoBO);
    }


    /**
     * 更新人才政策匹配表
     *
     * @param talentId
     * @param policyId
     * @return -1：没有找到匹配的政策信息
     */
    private int updatePOCompliance(Long talentId, Long policyId, int status, Date current) {
        /**
         * 同步政策申请表到符合政策条件的记录表中
         */
        Map<String, Object> map = new HashMap<>(2);
        map.put("talentId", talentId);
        map.put("policyId", policyId);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        map.put("year", year);
        List<PoCompliancePO> poCompliancePOS = this.poComplianceMapper.selectByPolicyTalent(map);

        if (poCompliancePOS != null && poCompliancePOS.size() > 0) {
            for (PoCompliancePO poCompliancePO : poCompliancePOS) {
                poCompliancePO.setStatus((byte) status);
                poCompliancePO.setApplyTime(current);
                this.poComplianceMapper.updateByPrimaryKey(poCompliancePO);
                return 0;
            }
        }

        return -1;
    }
}
