package com.talentcard.web.service.impl;

import com.talentcard.common.mapper.*;
import com.talentcard.common.pojo.*;
import com.talentcard.web.service.IBestPolicyToTalentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author: velve
 * @date: Created in 2020/8/17 15:17
 * @description: 筛选出最适合用户的政策信息
 * @version: 1.0
 */
@Service
public class BestPolicyToTalentServiceImpl implements IBestPolicyToTalentService {

    private static final Logger logger = LoggerFactory.getLogger(BestPolicyToTalentServiceImpl.class);
    /**
     * 人才类型表
     */
    @Autowired
    private TalentTypeMapper talentTypeMapper;
    /**
     *
     */
    @Autowired
    private PoComplianceMapper poComplianceMapper;
    /**
     * 政策信息表
     */
    @Autowired
    private PolicyMapper policyMapper;
    /**
     * 政策类型互斥表
     */
    @Autowired
    private PoTypeExcludeMapper poTypeExcludeMapper;
    /**
     * 政策享受人群标签属性表
     */
    @Autowired
    private PoSettingMapper poSettingMapper;

    @Async("asyncTaskExecutor")
    @Override
    public void asynBestPolicy() {

        /**
         * 1.查询出所有符合条件的用户信息
         */
        List<PolicyPO> policys = this.policyMapper.queryAll();

        List<Long> talentCard = new ArrayList<>(10);
        List<Long> category = new ArrayList<>(10);
        List<Integer> education = new ArrayList<>(10);
        List<Integer> title = new ArrayList<>(10);
        List<Integer> quality = new ArrayList<>(10);
        List<Long> honour = new ArrayList<>(10);

        Map<Long, PolicyPO> allPolicy = new HashMap<>(policys.size());
        for (PolicyPO po : policys) {

            allPolicy.put(po.getPolicyId(), po);
            List<PoSettingPO> poSettingPOS = poSettingMapper.selectByPolicyId(po.getPolicyId());
            if (poSettingPOS != null && poSettingPOS.size() > 0) {
                for (PoSettingPO poSettingPO : poSettingPOS) {
                    if (poSettingPO.getType() == 1) {
                        addListKey(talentCard, poSettingPO.getCardId());
                    } else if (poSettingPO.getType() == 2) {
                        addListKey(category, poSettingPO.getCategoryId());
                    } else if (poSettingPO.getType() == 3) {
                        addListKey(education, poSettingPO.getEducationId());
                    } else if (poSettingPO.getType() == 4) {
                        addListKey(title, poSettingPO.getTitleId());
                    } else if (poSettingPO.getType() == 5) {
                        addListKey(quality, poSettingPO.getQuality());
                    } else if (poSettingPO.getType() == 6) {
                        addListKey(honour, poSettingPO.getHonourId());
                    }
                }
            }

        }

        /**
         * 查询出符合所有政策条件的talentId
         */
        Map<String, List> map = new HashMap<>(6);

        setDBMapValue(map, "card", talentCard);
        setDBMapValue(map, "category", category);
        setDBMapValue(map, "education", education);
        setDBMapValue(map, "title", title);
        setDBMapValue(map, "quality", quality);
        setDBMapValue(map, "honour", honour);

        List<Long> allTalent = this.talentTypeMapper.selectByAllType(map);

        /**
         * 循环查找每一个人才对应的政策信息
         */
        for (Long talentId : allTalent) {
            bestTalent(talentId, allPolicy);
        }

        return;
    }


    @Async("asyncTaskExecutor")
    @Override
    public void asynBestPolicyForTalent(Long talentId) {

        /**
         * 1.查询出所有符合条件的用户信息
         */
        List<PolicyPO> policys = this.policyMapper.queryAll();
        List<Long> talentCard = new ArrayList<>(10);
        List<Long> category = new ArrayList<>(10);
        List<Integer> education = new ArrayList<>(10);
        List<Integer> title = new ArrayList<>(10);
        List<Integer> quality = new ArrayList<>(10);
        List<Long> honour = new ArrayList<>(10);

        Map<Long, PolicyPO> allPolicy = new HashMap<>(policys.size());
        for (PolicyPO po : policys) {

            allPolicy.put(po.getPolicyId(), po);
            List<PoSettingPO> poSettingPOS = poSettingMapper.selectByPolicyId(po.getPolicyId());
            if (poSettingPOS != null && poSettingPOS.size() > 0) {
                for (PoSettingPO poSettingPO : poSettingPOS) {
                    if (poSettingPO.getType() == 1) {
                        addListKey(talentCard, poSettingPO.getCardId());
                    } else if (poSettingPO.getType() == 2) {
                        addListKey(category, poSettingPO.getCategoryId());
                    } else if (poSettingPO.getType() == 3) {
                        addListKey(education, poSettingPO.getEducationId());
                    } else if (poSettingPO.getType() == 4) {
                        addListKey(title, poSettingPO.getTitleId());
                    } else if (poSettingPO.getType() == 5) {
                        addListKey(quality, poSettingPO.getQuality());
                    } else if (poSettingPO.getType() == 6) {
                        addListKey(honour, poSettingPO.getHonourId());
                    }
                }
            }

        }

        bestTalent(talentId, allPolicy);
        return;
    }

    /**
     * 数据库查询的Map中添加非空List
     *
     * @param map
     * @param key
     * @param value
     */
    private void setDBMapValue(Map map, String key, List value) {
        if (value != null && value.size() > 0) {
            map.put(key, value);
        }
    }

    /**
     * 向List中添加不重复的key
     *
     * @param list
     * @param key
     * @return
     */
    private boolean addListKey(List list, Object key) {
        if (!list.contains(key)) {
            list.add(key);
        }
        return true;
    }

    /**
     * 针对每一个人才进行政策选择
     *
     * @param talentId
     * @param allPolicy
     * @return
     */
    private boolean bestTalent(Long talentId, Map<Long, PolicyPO> allPolicy) {
        /**
         * 获取一个人所有符合条件的政策
         */
        List<TalentTypePO> talentTypePOS = this.talentTypeMapper.selectByTalentId(talentId);

        Map map = new HashMap(6);
        List<Long> talentCard = new ArrayList<>(1);
        List<Long> category = new ArrayList<>(1);
        List<Integer> education = new ArrayList<>(3);
        List<Integer> title = new ArrayList<>(3);
        List<Integer> quality = new ArrayList<>(3);
        List<Long> honour = new ArrayList<>(3);


        for (TalentTypePO talentTypePO : talentTypePOS) {
            if (talentTypePO.getType() == 1) {
                addListKey(talentCard, talentTypePO.getCardId());
            } else if (talentTypePO.getType() == 2) {
                addListKey(category, talentTypePO.getCategoryId());
            } else if (talentTypePO.getType() == 3) {
                addListKey(education, talentTypePO.getEducationId());
            } else if (talentTypePO.getType() == 4) {
                addListKey(title, talentTypePO.getTitleId());
            } else if (talentTypePO.getType() == 5) {
                addListKey(quality, talentTypePO.getQuality());
            } else if (talentTypePO.getType() == 6) {
                addListKey(honour, talentTypePO.getHonourId());
            }
        }

        setDBMapValue(map, "card", talentCard);
        setDBMapValue(map, "category", category);
        setDBMapValue(map, "education", education);
        setDBMapValue(map, "title", title);
        setDBMapValue(map, "quality", quality);
        setDBMapValue(map, "honour", honour);

        List<Long> onePolicys = this.poSettingMapper.selectByType(map);

        /**
         * 政策类型-政策
         */
        Map<Long, List<PolicyPO>> oneTypeToPolicy = new HashMap<>(5);
        for (Long p1 : onePolicys) {
            PolicyPO po = allPolicy.get(p1);
            if (po != null) {
                if (oneTypeToPolicy.containsKey(po.getPTid())) {
                    List<PolicyPO> typeTo = oneTypeToPolicy.get(po.getPTid());
                    if (typeTo != null) {
                        typeTo.add(po);
                    } else {
                        List<PolicyPO> pos = new ArrayList<>(2);
                        pos.add(po);
                        oneTypeToPolicy.put(po.getPTid(), pos);
                    }
                } else {
                    List<PolicyPO> pos = new ArrayList<>(2);
                    pos.add(po);
                    oneTypeToPolicy.put(po.getPTid(), pos);
                }
            }
        }

        Map<String, Object> m = new HashMap(2);
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        m.put("year", year);
        m.put("talentId", talentId);
        List<PoCompliancePO> oneApplyPolicy = this.poComplianceMapper.selectByYearTalent(m);
        /**
         * 申请过政策，同类政策删除，互斥政策标记为互斥
         */
        if (logger.isDebugEnabled()) {
            logger.debug("oneApplyPolicy size:{}", oneApplyPolicy.size());
        }
        if (oneApplyPolicy.size() > 0) {
            List<PoCompliancePO> needRemove = new ArrayList<>(5);
            for (PoCompliancePO poCompliancePO : oneApplyPolicy) {
                //包含当前政策，
                if (onePolicys.contains(poCompliancePO.getPolicyId())) {
                    /**
                     * 不包含当前规范的政策
                     * 0：未申请状态时，
                     * 1：已同意状态
                     * 2：已驳回状态，
                     * 3：待审批状态
                     * 10：不可申请状态
                     */
                    if (logger.isDebugEnabled()) {
                        logger.debug("onePolicys.contains {}  ", poCompliancePO);
                    }

                    if (poCompliancePO.getStatus() == 1 || poCompliancePO.getStatus() == 3) {

                    } else if (poCompliancePO.getStatus() == 0 || poCompliancePO.getStatus() == 2 || poCompliancePO.getStatus() == 10) {

                        PolicyPO po = allPolicy.get(poCompliancePO.getPolicyId());
                        List<PolicyPO> po1 = oneTypeToPolicy.get(po.getPTid());
                        if (po1 != null) {
                            /**
                             * 同类型删除
                             */
                            int maxFunds = po.getFunds();
                            for (PolicyPO po2 : po1) {
                                if (!po2.getPolicyId().equals(poCompliancePO.getPolicyId())) {
                                    if (po2.getFunds() > maxFunds) {
                                        maxFunds = po2.getFunds();
                                    }
                                }
                            }

                            if (logger.isDebugEnabled()) {
                                logger.debug("po.getFunds():{} maxFunds:{} ", po.getFunds(), maxFunds);
                            }
                            if (po.getFunds() != maxFunds) {

                                this.poComplianceMapper.deleteByPrimaryKey(poCompliancePO.getPCoId());
                                needRemove.add(poCompliancePO);
                                for (PolicyPO po2 : po1) {
                                    if (po2.getPolicyId().equals(poCompliancePO.getPolicyId())) {
                                        po1.remove(po2);
                                    }
                                }
                            } else {

                                /**
                                 * 不同类型互斥
                                 */
                                List<PoTypeExcludePO> poTypeExcludePOS = this.poTypeExcludeMapper.queryExId(po.getPTid());

                                boolean isFind = false;
                                if (poTypeExcludePOS != null && poTypeExcludePOS.size() > 0) {
                                    for (PoTypeExcludePO poTypeExcludePO : poTypeExcludePOS) {
                                        List<PolicyPO> po2 = oneTypeToPolicy.get(poTypeExcludePO.getPTid2());

                                        if (po2 != null && po2.size() > 0) {

                                            for (PolicyPO po3 : po2) {
                                                for (PoCompliancePO poCompliancePO1 : oneApplyPolicy) {
                                                    if (po3.getPolicyId().equals(poCompliancePO1.getPolicyId())) {

                                                        isFind = true;
                                                        if (poCompliancePO1.getStatus() == 1 || poCompliancePO1.getStatus() == 3) {
                                                            if (poCompliancePO.getStatus() != 10) {
                                                                poCompliancePO.setStatus((byte) 10);
                                                                this.poComplianceMapper.updateByPrimaryKey(poCompliancePO);
                                                            }
                                                        } else {
                                                            if (poCompliancePO.getStatus() == 10) {
                                                                poCompliancePO.setStatus((byte) 0);
                                                                this.poComplianceMapper.updateByPrimaryKey(poCompliancePO);
                                                            }
                                                        }
                                                        break;
                                                    }
                                                }
                                            }


                                        }
                                    }
                                }


                                /**
                                 * 当没有找到对应申请记录，并且自身申请记录状态是10，则进行修复
                                 */
                                if (logger.isDebugEnabled()) {
                                    logger.debug("isFind:{} ", isFind);
                                }
                                if (!isFind) {
                                    if (poCompliancePO.getStatus() == 10) {
                                        poCompliancePO.setStatus((byte) 0);
                                        this.poComplianceMapper.updateByPrimaryKey(poCompliancePO);
                                    }
                                }
                            }


                        }

                    }
                } else {
                    /**
                     * 不包含当前规范的政策
                     * 0：未申请状态时，删除
                     * 1：已同意状态
                     * 2：已驳回状态，删除
                     * 3：待审批状态
                     * 10：不可申请状态
                     */

                    if (poCompliancePO.getStatus() == 0 || poCompliancePO.getStatus() == 2 || poCompliancePO.getStatus() == 10) {
                        this.poComplianceMapper.deleteByPrimaryKey(poCompliancePO.getPCoId());
                        needRemove.add(poCompliancePO);
                    } else {
                        needRemove.add(poCompliancePO);
                        logger.error("contain bad data, poCompliancePO {} error.", poCompliancePO);
                    }
                }
            }
            if (needRemove.size() > 0) {
                oneApplyPolicy.remove(needRemove);
            }
        }

        for (Long poId : onePolicys) {

            boolean need = true;
            for (PoCompliancePO poCompliancePO : oneApplyPolicy) {
                if (poCompliancePO.getPolicyId().equals(poId)) {
                    need = false;
                    break;
                }
            }

            if (need) {
                PoCompliancePO poCompliancePO = new PoCompliancePO();
                poCompliancePO.setPolicyId(poId);
                poCompliancePO.setTalentId(talentId);
                poCompliancePO.setStatus((byte) 0);
                poCompliancePO.setYear(year);

                /**
                 * 不同类型互斥
                 */
                PolicyPO po = allPolicy.get(poId);
                List<PoTypeExcludePO> poTypeExcludePOS = this.poTypeExcludeMapper.queryExId(po.getPTid());

                if (poTypeExcludePOS != null && poTypeExcludePOS.size() > 0) {
                    for (PoTypeExcludePO poTypeExcludePO : poTypeExcludePOS) {
                        List<PolicyPO> po2 = oneTypeToPolicy.get(poTypeExcludePO.getPTid2());

                        if (po2 != null && po2.size() > 0) {
                            for (PolicyPO po3 : po2) {
                                for (PoCompliancePO poCompliancePO1 : oneApplyPolicy) {
                                    if (po3.getPolicyId().equals(poCompliancePO1.getPolicyId())) {

                                        if (poCompliancePO1.getStatus() == 1 || poCompliancePO1.getStatus() == 3) {
                                            if (poCompliancePO.getStatus() != 10) {
                                                poCompliancePO.setStatus((byte) 10);
                                            }
                                        }
                                        break;
                                    }
                                }
                            }

                        }
                    }
                }
                this.poComplianceMapper.insert(poCompliancePO);
            }
        }

        return true;
    }


}
