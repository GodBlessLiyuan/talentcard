package com.talentcard.web.controller;

import com.talentcard.common.mapper.TalentCertificationInfoMapper;
import com.talentcard.common.mapper.TalentMapper;
import com.talentcard.common.mapper.TalentTypeMapper;
import com.talentcard.common.pojo.TalentCertificationInfoPO;
import com.talentcard.common.pojo.TalentPO;
import com.talentcard.common.pojo.TalentTypePO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.IBestPolicyToTalentService;
import com.talentcard.web.service.IDataMigrationService;
import com.talentcard.web.service.ITalentInfoCertificationService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-07-11 15:47
 * @description
 */
@RequestMapping("dataMigration")
@RestController
public class DataMigrationController {
    @Autowired
    IDataMigrationService iDataMigrationService;
    @Autowired
    IBestPolicyToTalentService iBestPolicyToTalentService;
    @Autowired
    ITalentInfoCertificationService iTalentInfoCertificationService;
    @Autowired
    private TalentCertificationInfoMapper talentCertificationInfoMapper;
    @Autowired
    private TalentTypeMapper talentTypeMapper;

    @Autowired
    private TalentMapper talentMapper;

    /**
     * 认证审批表 人才数据迁移
     *
     * @param
     * @return
     */
    @RequestMapping("certExamineRecord")
    public ResultVO certExamineRecord() {
        return iDataMigrationService.certExamineRecord();
    }

    /**
     * 认证人才表 人才数据迁移
     *
     * @param
     * @return
     */
    @RequestMapping("certTalent")
    public ResultVO certTalent() {
        return iDataMigrationService.certTalent();
    }

    /**
     * 认证审批表详情 数据迁移
     *
     * @param
     * @return
     */
    @RequestMapping("certApprovalDetail")
    public ResultVO certApprovalDetail() {
        return iDataMigrationService.certApprovalDetail();
    }


    /**
     * 测试政策匹配最优策略
     *
     * @return
     */
    @RequestMapping("best")
    public ResultVO best() {
        iBestPolicyToTalentService.asynBestPolicy();
        return new ResultVO(1000, "success");
    }


    /**
     * 更新用户的标签属性
     *
     * @return
     */
    @RequestMapping("update")
    public ResultVO update() {
        iTalentInfoCertificationService.update((long) 3);
        return new ResultVO(1000, "success");
    }

    /**
     * 把t_certification_info表中的数据转到t_talent_type表格中
     *
     * @return
     */
    @RequestMapping("cerToTalentTypeDB")
    public ResultVO cerToTalentTypeDB() {

        List<TalentCertificationInfoPO> pos = this.talentCertificationInfoMapper.selectAll();
        if (pos != null && pos.size() > 0) {
            for (TalentCertificationInfoPO po : pos) {

                List<TalentTypePO> list = this.talentTypeMapper.selectByTalentId(po.getTalentId());
                Map<Byte, List<TalentTypePO>> map = new HashMap<>(list.size());
                for (TalentTypePO p : list) {
                    if (map.containsKey(p.getType())) {
                        List<TalentTypePO> pos1 = map.get(p.getType());
                        pos1.add(p);
                    } else {
                        List<TalentTypePO> pos1 = new ArrayList<>(2);
                        pos1.add(p);
                        map.put(p.getType(), pos1);
                    }
                }

                //人才卡
                TalentPO tal = talentMapper.selectByPrimaryKey(po.getTalentId());
                List<TalentTypePO> ll = map.get((byte) 1);
                if (ll != null && ll.size() > 0) {

                    for (TalentTypePO po1 : ll) {
                        if(!po1.getCardId().equals(tal.getCardId())){
                            po1.setCardId(tal.getCardId());
                            this.talentTypeMapper.updateByPrimaryKey(po1);
                        }
                    }
                }

                //人才学历
                if (!StringUtils.isEmpty(po.getEducation())) {
                    String eds[] = po.getEducation().split(",");

                    List<TalentTypePO> l = map.get((byte) 3);
                    if (eds != null && eds.length > 0) {
                        for (String e : eds) {
                            if (StringUtils.isEmpty(e)) {
                                continue;
                            }
                            Integer integer = Integer.valueOf(e);
                            boolean needInsert = true;
                            if (l != null && l.size() > 0) {
                                for (TalentTypePO po1 : l) {
                                    if (integer.equals(po1.getEducationId())) {
                                        l.remove(po1);
                                        needInsert = false;
                                        break;
                                    }
                                }
                            }
                            if (needInsert) {
                                TalentTypePO po1 = new TalentTypePO();
                                po1.setType((byte) 3);
                                po1.setTalentId(po.getTalentId());
                                po1.setEducationId(Integer.valueOf(e));
                                this.talentTypeMapper.insert(po1);
                            }
                        }
                        /**
                         * 删除多余的数据
                         */
                        if (l != null && l.size() > 0) {
                            for (TalentTypePO po1 : l) {
                                this.talentTypeMapper.deleteByPrimaryKey(po1.getId());
                            }
                        }
                    }
                }

                //人才类别
                if (!StringUtils.isEmpty(po.getTalentCategory())) {
                    String cat[] = po.getTalentCategory().split(",");

                    List<TalentTypePO> l = map.get((byte) 2);
                    if (cat != null && cat.length > 0) {
                        for (String e : cat) {
                            if (StringUtils.isEmpty(e)) {
                                continue;
                            }
                            Long aLong = Long.valueOf(e);
                            boolean needInsert = true;
                            if (l != null && l.size() > 0) {
                                for (TalentTypePO po1 : l) {
                                    if (aLong.equals(po1.getCategoryId())) {
                                        l.remove(po1);
                                        needInsert = false;
                                        break;
                                    }
                                }
                            }
                            if (needInsert) {
                                TalentTypePO po1 = new TalentTypePO();
                                po1.setType((byte) 2);
                                po1.setTalentId(po.getTalentId());
                                po1.setCategoryId(aLong);
                                this.talentTypeMapper.insert(po1);
                            }
                        }
                        /**
                         * 删除多余的数据
                         */
                        if (l != null && l.size() > 0) {
                            for (TalentTypePO po1 : l) {
                                this.talentTypeMapper.deleteByPrimaryKey(po1.getId());
                            }
                        }
                    }
                }


                //职称类别
                if (!StringUtils.isEmpty(po.getPtCategory())) {
                    String title[] = po.getPtCategory().split(",");

                    List<TalentTypePO> l = map.get((byte) 4);
                    if (title != null && title.length > 0) {
                        for (String e : title) {
                            if (StringUtils.isEmpty(e)) {
                                continue;
                            }
                            Integer integer = Integer.valueOf(e);
                            boolean needInsert = true;
                            if (l != null && l.size() > 0) {
                                for (TalentTypePO po1 : l) {
                                    if (integer.equals(po1.getTitleId())) {
                                        l.remove(po1);
                                        needInsert = false;
                                        break;
                                    }
                                }
                            }
                            if (needInsert) {
                                TalentTypePO po1 = new TalentTypePO();
                                po1.setType((byte) 4);
                                po1.setTalentId(po.getTalentId());
                                po1.setTitleId(integer);
                                this.talentTypeMapper.insert(po1);
                            }
                        }
                        /**
                         * 删除多余的数据
                         */
                        if (l != null && l.size() > 0) {
                            for (TalentTypePO po1 : l) {
                                this.talentTypeMapper.deleteByPrimaryKey(po1.getId());
                            }
                        }
                    }
                }


                //职业资格
                if (!StringUtils.isEmpty(po.getPqCategory())) {
                    String qua[] = po.getPqCategory().split(",");

                    List<TalentTypePO> l = map.get((byte) 5);
                    if (qua != null && qua.length > 0) {
                        for (String e : qua) {
                            if (StringUtils.isEmpty(e)) {
                                continue;
                            }
                            Integer integer = Integer.valueOf(e);
                            boolean needInsert = true;
                            if (l != null && l.size() > 0) {
                                for (TalentTypePO po1 : l) {
                                    if (integer.equals(po1.getQuality())) {
                                        l.remove(po1);
                                        needInsert = false;
                                        break;
                                    }
                                }
                            }
                            if (needInsert) {
                                TalentTypePO po1 = new TalentTypePO();
                                po1.setType((byte) 5);
                                po1.setTalentId(po.getTalentId());
                                po1.setQuality(integer);
                                this.talentTypeMapper.insert(po1);
                            }
                        }
                        /**
                         * 删除多余的数据
                         */
                        if (l != null && l.size() > 0) {
                            for (TalentTypePO po1 : l) {
                                this.talentTypeMapper.deleteByPrimaryKey(po1.getId());
                            }
                        }
                    }
                }

                //荣誉
                if (!StringUtils.isEmpty(po.getHonourId())) {
                    String hon[] = po.getHonourId().split(",");

                    List<TalentTypePO> l = map.get((byte) 6);
                    if (hon != null && hon.length > 0) {
                        for (String e : hon) {
                            if (StringUtils.isEmpty(e)) {
                                continue;
                            }
                            Long aLong = Long.valueOf(e);
                            boolean needInsert = true;
                            if (l != null && l.size() > 0) {
                                for (TalentTypePO po1 : l) {
                                    if (aLong.equals(po1.getHonourId())) {
                                        l.remove(po1);
                                        needInsert = false;
                                        break;
                                    }
                                }
                            }
                            if (needInsert) {
                                TalentTypePO po1 = new TalentTypePO();
                                po1.setType((byte) 6);
                                po1.setTalentId(po.getTalentId());
                                po1.setHonourId(aLong);
                                this.talentTypeMapper.insert(po1);
                            }
                        }
                        /**
                         * 删除多余的数据
                         */
                        if (l != null && l.size() > 0) {
                            for (TalentTypePO po1 : l) {
                                this.talentTypeMapper.deleteByPrimaryKey(po1.getId());
                            }
                        }
                    }
                }
            }
        }
        return new ResultVO(1000, "success");
    }

}
