package com.talentcard.web.service.impl;

import com.talentcard.common.mapper.*;
import com.talentcard.common.pojo.*;
import com.talentcard.common.utils.redis.RedisMapUtil;
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
    /**
     * 政策统计信息
     */
    @Autowired
    private PoStatisticsMapper poStatisticsMapper;
    @Autowired
    private RedisMapUtil redisMapUtil;
    @Autowired
    private TalentMapper talentMapper;


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


        /**
         * 删除已经匹配的用户，但是政策修改后，此用户不再享受任何政策
         */
        List<Long> notTalent = this.talentTypeMapper.selectByNotType(map);
        if (notTalent != null && notTalent.size() > 0) {
            Map<String, Object> not = new HashMap(2);
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);

            for (Long talentId : notTalent) {
                not.clear();
                not.put("talentId", talentId);
                not.put("year", year);
                List<PoCompliancePO> list = this.poComplianceMapper.selectByPolicyTalent(not);
                if (list != null && list.size() > 0) {
                    for (PoCompliancePO poCompliancePO : list) {
                        if (poCompliancePO.getStatus() != 1 && poCompliancePO.getStatus() != 3) {
                            this.poComplianceMapper.deleteByPrimaryKey(poCompliancePO.getPCoId());
                        }
                    }
                }
            }
        }


        List<Long> allTalent = this.talentTypeMapper.selectByAllType(map);

        /**
         * 循环查找每一个人才对应的政策信息
         */
        for (Long talentId : allTalent) {
            bestTalent(talentId, allPolicy);

            TalentPO po = talentMapper.selectByPrimaryKey(talentId);
            if (po != null) {
                redisMapUtil.del(po.getOpenId());
            }
        }

        this.deletePolicy();

        updatePolicyStatistics(allPolicy);
        return;
    }

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

        TalentPO po = talentMapper.selectByPrimaryKey(talentId);
        if (po != null) {
            redisMapUtil.del(po.getOpenId());
        }

        /**
         * 更新政策对应的统计信息
         */
        updatePolicyStatistics(allPolicy);
        return;
    }

    /**
     * 更新政策对应的统计信息
     *
     * @param allPolicy
     * @return
     */
    private boolean updatePolicyStatistics(Map<Long, PolicyPO> allPolicy) {
        Map<String, Object> map = new HashMap<>(1);
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        for (Long policyId : allPolicy.keySet()) {
            map.clear();
            map.put("policyId", policyId);
            map.put("year", year);
            List<PoStatisticsPO> bos = this.poComplianceMapper.policyCount(map);
            if (bos != null && bos.size() > 0) {
                for (PoStatisticsPO bo : bos) {
                    if (bo != null) {
                        PoStatisticsPO b = this.poStatisticsMapper.selectByPrimaryKey(bo.getPolicyId());
                        bo.setTotal(bo.getNotApply() + bo.getNotApproval() + bo.getPass() + bo.getReject());

                        if (b != null) {
                            this.poStatisticsMapper.updateByPrimaryKey(bo);
                        } else {
                            this.poStatisticsMapper.insert(bo);
                        }
                    } else {
                        PoStatisticsPO b = this.poStatisticsMapper.selectByPrimaryKey(policyId);
                        if (b == null) {
                            PoStatisticsPO t = new PoStatisticsPO();
                            t.setTotal(0L);
                            t.setNotApply(0L);
                            t.setNotApproval(0L);
                            t.setPass(0L);
                            t.setPolicyId(policyId);
                            t.setReject(0L);
                            this.poStatisticsMapper.insert(t);
                        } else {
                            b.setTotal(0L);
                            b.setNotApply(0L);
                            b.setNotApproval(0L);
                            b.setPass(0L);
                            b.setReject(0L);
                            this.poStatisticsMapper.updateByPrimaryKey(b);
                        }
                    }
                }
            }
        }


        return true;
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
        //oneApplyPolicy保存当前人才申请的所有记录
        List<PoCompliancePO> oneApplyPolicy = this.poComplianceMapper.selectByYearTalent(m);


        /**
         * 讲人才所有的申请按照类型分组
         */
        Map<Long, List<PoCompliancePO>> oneApplyTypeToPolicy = new HashMap<>(5);
        Map<Long, PoCompliancePO> oneApplyMapPolicy = new HashMap<>();
        for (PoCompliancePO p1 : oneApplyPolicy) {

            /**
             * 当下架时，不执行遍历
             */
            if (!allPolicy.containsKey(p1.getPolicyId())) {
                if (p1.getStatus() != 1 && p1.getStatus() != 3) {
                    this.poComplianceMapper.deleteByPrimaryKey(p1.getPCoId());
                }
                continue;
            } else if(!onePolicys.contains(p1.getPolicyId())){
                if (p1.getStatus() != 1 && p1.getStatus() != 3) {
                    this.poComplianceMapper.deleteByPrimaryKey(p1.getPCoId());
                    continue;
                }
            }

            oneApplyMapPolicy.put(p1.getPolicyId(), p1);

            PolicyPO po = allPolicy.get(p1.getPolicyId());
            if (po != null) {
                if (oneApplyTypeToPolicy.containsKey(po.getPTid())) {
                    List<PoCompliancePO> typeTo = oneApplyTypeToPolicy.get(po.getPTid());
                    if (typeTo != null) {
                        typeTo.add(p1);
                    } else {
                        List<PoCompliancePO> pos = new ArrayList<>(2);
                        pos.add(p1);
                        oneApplyTypeToPolicy.put(po.getPTid(), pos);
                    }
                } else {
                    List<PoCompliancePO> pos = new ArrayList<>(2);
                    pos.add(p1);
                    oneApplyTypeToPolicy.put(po.getPTid(), pos);
                }
            }
        }


        /**
         * 相同类型内寻找最优政策
         */
        for (Long pTid : oneTypeToPolicy.keySet()) {
            List<PolicyPO> policyPOS = oneTypeToPolicy.get(pTid);
            long applyPolicyId = 0L;
            for (PolicyPO po : policyPOS) {
                if (oneApplyMapPolicy.containsKey(po.getPolicyId())) {
                    PoCompliancePO poCompliancePO = oneApplyMapPolicy.get(po.getPolicyId());
                    if (poCompliancePO != null) {
                        if (poCompliancePO.getStatus() == 1 || poCompliancePO.getStatus() == 3) {
                            applyPolicyId = po.getPolicyId();
                        }
                    }
                }
            }

            logger.info("pTid:{} applyPolicyId:{}", pTid,applyPolicyId);

            if (applyPolicyId != 0) {
                for (PolicyPO po : policyPOS) {
                    if (oneApplyMapPolicy.containsKey(po.getPolicyId())) {
                        PoCompliancePO poCompliancePO = oneApplyMapPolicy.get(po.getPolicyId());
                        if (poCompliancePO.getStatus() != 1 && poCompliancePO.getStatus() != 3) {
                            this.poComplianceMapper.deleteByPrimaryKey(poCompliancePO.getPCoId());
                            oneApplyMapPolicy.remove(po.getPolicyId());
                        }
                    }
                }
            } else {
                int maxFund = 0;
                for (PolicyPO po : policyPOS) {
                    if (po.getFunds() > maxFund) {
                        maxFund = po.getFunds();
                    }
                }
                logger.error("maxFund size:{}", maxFund);
                for (PolicyPO po : policyPOS) {
                    if (po.getFunds() >= maxFund) {
                        if (oneApplyMapPolicy.containsKey(po.getPolicyId())) {
                            PoCompliancePO poCompliancePO = oneApplyMapPolicy.get(po.getPolicyId());
                            if (poCompliancePO != null) {
                                if (poCompliancePO.getStatus() == 10) {
                                    poCompliancePO.setStatus((byte)11);
                                    this.poComplianceMapper.updateByPrimaryKey(poCompliancePO);
                                }
                            }
                        } else {
                            PoCompliancePO poCompliancePO = new PoCompliancePO();
                            poCompliancePO.setPolicyId(po.getPolicyId());
                            poCompliancePO.setTalentId(talentId);
                            poCompliancePO.setStatus((byte) 11);
                            poCompliancePO.setYear(year);
                            this.poComplianceMapper.insert(poCompliancePO);

                            oneApplyMapPolicy.put(po.getPolicyId(), poCompliancePO);
                            if (oneApplyTypeToPolicy.containsKey(po.getPTid())) {
                                List<PoCompliancePO> poCompliancePOS = oneApplyTypeToPolicy.get(po.getPTid());
                                if (poCompliancePOS != null) {
                                    poCompliancePOS.add(poCompliancePO);
                                } else {
                                    poCompliancePOS = new ArrayList<>(1);
                                    poCompliancePOS.add(poCompliancePO);
                                    oneApplyTypeToPolicy.put(po.getPTid(), poCompliancePOS);
                                }
                            } else {
                                List<PoCompliancePO> poCompliancePOS = new ArrayList<>(1);
                                poCompliancePOS.add(poCompliancePO);
                                oneApplyTypeToPolicy.put(po.getPTid(), poCompliancePOS);
                            }
                        }
                    } else {
                        /**
                         * 当存在历史政策记录时，删除未申请和不可申请状态的政策
                         */
                        if(oneApplyMapPolicy.containsKey(po.getPolicyId())){
                            PoCompliancePO poCompliancePO = oneApplyMapPolicy.get(po.getPolicyId());
                            if (poCompliancePO != null) {
                                if (poCompliancePO.getStatus() != 1 && poCompliancePO.getStatus() != 3) {
                                    this.poComplianceMapper.deleteByPrimaryKey(poCompliancePO.getPCoId());
                                    oneApplyMapPolicy.remove(po.getPolicyId());
                                }
                            }
                        }
                    }
                }
            }
        }
        /**
         * 将Map中只保留当前每组内最优的结果
         */
        for (Long pTid : oneApplyTypeToPolicy.keySet()) {
            List<PoCompliancePO> poCompliancePOS = oneApplyTypeToPolicy.get(pTid);
            if (poCompliancePOS != null && poCompliancePOS.size() > 0) {
                List<PoCompliancePO> remove = new ArrayList<>(poCompliancePOS.size());
                for (PoCompliancePO poCompliancePO : poCompliancePOS) {
                    if (!oneApplyMapPolicy.containsKey(poCompliancePO.getPolicyId())) {
                        remove.add(poCompliancePO);
                    }
                }
                if (remove.size() > 0) {
                    poCompliancePOS.removeAll(remove);
                }
            }
        }
        /**
         * 更新不同分组之间的互斥关系
         */
        for (Long pTid : oneApplyTypeToPolicy.keySet()) {
            List<PoCompliancePO> poCompliancePOS = oneApplyTypeToPolicy.get(pTid);
            if (poCompliancePOS.size() > 0) {
                boolean haveApply = false;
                for (PoCompliancePO poCompliancePO : poCompliancePOS) {
                    if (poCompliancePO.getStatus() == 1 || poCompliancePO.getStatus() == 3) {
                        haveApply = true;
                        break;
                    }
                }
                if (haveApply) {
                    List<PoTypeExcludePO> poTypeExcludePOS = this.poTypeExcludeMapper.queryExId(pTid);
                    for (PoTypeExcludePO poTypeExcludePO : poTypeExcludePOS) {
                        //logger.error("poTypeExcludePO:{}", poTypeExcludePO);
                        if (oneApplyTypeToPolicy.containsKey(poTypeExcludePO.getPTid2())) {

                            List<PoCompliancePO> exclude = oneApplyTypeToPolicy.get(poTypeExcludePO.getPTid2());
                            //logger.error("exclude:{}", exclude);
                            if (exclude != null && exclude.size() > 0) {
                                for (PoCompliancePO poCompliancePO : exclude) {
                                    if (poCompliancePO.getStatus() != 1 && poCompliancePO.getStatus() != 3) {
                                        poCompliancePO.setStatus((byte) 10);
                                        this.poComplianceMapper.updateByPrimaryKey(poCompliancePO);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return true;
    }


    /**
     * 更新人才政策匹配表
     *
     * @param talentId
     * @param policyId
     * @return
     */
    @Override
    public int updatePOCompliance(Long talentId, Long policyId, byte status) {
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
                poCompliancePO.setStatus(status);
                this.poComplianceMapper.updateByPrimaryKey(poCompliancePO);
            }
        }

        return 0;
    }

    /**
     * 删除政策统计信息
     * @return
     */
    @Override
    public int deletePolicy(){
        this.poStatisticsMapper.deleteAll();
        return 0;
    }

}
