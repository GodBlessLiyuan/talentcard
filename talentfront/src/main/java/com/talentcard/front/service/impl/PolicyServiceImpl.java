package com.talentcard.front.service.impl;

import com.talentcard.common.bo.PolicyApplyBO;
import com.talentcard.common.mapper.*;
import com.talentcard.common.pojo.PolicyApplyPO;
import com.talentcard.common.pojo.PolicyPO;
import com.talentcard.common.pojo.TalentPO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.front.service.IPolicyService;
import com.talentcard.front.vo.PolicyAppliesVO;
import com.talentcard.front.vo.PolicyApplyDetailVO;
import com.talentcard.front.vo.PolicyDetailVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: xiahui
 * @date: Created in 2020/4/9 16:55
 * @description: 政策权益
 * @version: 1.0
 */
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
    @Resource
    private PolicyApprovalMapper policyApprovalMapper;

    @Override
    public ResultVO policies(Long talentId) {
        TalentPO talentPO = talentMapper.selectByPrimaryKey(talentId);
        if (null == talentPO || talentPO.getStatus() == 2) {
            return new ResultVO(1000);
        }

        List<Integer> existEducations = educationMapper.queryNameByTalentId(talentId);
        List<Integer> existTitles = profTitleMapper.queryNameByTalentId(talentId);
        List<Integer> existQualities = profQualityMapper.queryNameByTalentId(talentId);
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

            boolean show = false;
            if (null != cardIds) {
                for (String cardId : cardIds) {
                    if (existCardId.toString().equals(cardId)) {
                        show = true;
                        break;
                    }
                }
            }
            if (!show && null != categories && null != existCategories) {
                for (String category : categories) {
                    if (!show) {
                        for (String existCategory : existCategories) {
                            if (category.equals(existCategory)) {
                                show = true;
                                break;
                            }
                        }
                    }
                }
            }
            if (!show && null != educations) {
                for (String educ : educations) {
                    if (existEducations.contains(educ)) {
                        show = true;
                        break;
                    }
                }
            }
            if (!show && null != titles) {
                for (String title : titles) {
                    if (existTitles.contains(title)) {
                        show = true;
                        break;
                    }
                }
            }
            if (!show && null != qualities) {
                for (String quality : qualities) {
                    if (existQualities.contains(quality)) {
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
            vo.setDesc(po.getDescription());
            vo.setBank(po.getBank());
            vo.setAnnex(po.getAnnex());
            vo.setApply(po.getApply());

            List<PolicyApplyPO> applyPOs = policyApplyMapper.queryByTidAndPid(talentId, po.getPolicyId());
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

                if (null != vo.getRight()) {
                    if (applyNum >= 1) {
                        vo.setRight((byte) 3);
                    }
                }
            }

            vos.add(vo);
        }

        return new ResultVO<>(1000, vos);
    }

    @Override
    public ResultVO apply(Long tid, Long pid) {
        return null;
    }

    @Override
    public ResultVO applies(Long tid) {
        List<PolicyApplyPO> pos = policyApplyMapper.queryByTalentId(tid);
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
}
