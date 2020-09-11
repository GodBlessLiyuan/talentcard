package com.talentcard.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.talentcard.common.config.FilePathConfig;
import com.talentcard.common.constant.EditTalentRecordConstant;
import com.talentcard.common.mapper.*;
import com.talentcard.common.pojo.*;
import com.talentcard.common.utils.DateUtil;
import com.talentcard.common.utils.ExcelExportUtil;
import com.talentcard.common.utils.FileUtil;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.constant.OpsRecordMenuConstant;
import com.talentcard.web.service.*;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

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

    @Autowired
    private PolicyApplyMapper policyApplyMapper;

    @Autowired
    private PoComplianceMapper poComplianceMapper;
    @Value("${vbooster.token}")
    private String s_token;
    @Autowired
    private SocialSecurityMapper socialSecurityMapper;

    @Autowired
    private FilePathConfig filePathConfig;

    @Autowired
    private IEditTalentRecordService iEditTalentRecordService;

    @Autowired
    private ILogService logService;


    /**
     * 认证审批表 人才数据迁移
     *
     * @param
     * @return
     */
    @RequestMapping("certExamineRecord")
    public ResultVO certExamineRecord(@RequestParam(value = "token") String token) {
        if (!StringUtils.equals(s_token, token)) {
            return new ResultVO(2000);
        }
        return iDataMigrationService.certExamineRecord();
    }

    /**
     * 认证人才表 人才数据迁移
     *
     * @param
     * @return
     */
    @RequestMapping("certTalent")
    public ResultVO certTalent(@RequestParam(value = "token") String token) {
        if (!StringUtils.equals(s_token, token)) {
            return new ResultVO(2000);
        }
        return iDataMigrationService.certTalent();
    }

    /**
     * 认证审批表详情 数据迁移
     *
     * @param
     * @return
     */
    @RequestMapping("certApprovalDetail")
    public ResultVO certApprovalDetail(@RequestParam(value = "token") String token) {
        if (!StringUtils.equals(s_token, token)) {
            return new ResultVO(2000);
        }
        return iDataMigrationService.certApprovalDetail();
    }


    /**
     * 测试政策匹配最优策略
     *
     * @return
     */
    @RequestMapping("best")
    public ResultVO best(@RequestParam(value = "token") String token) {
        if (!StringUtils.equals(s_token, token)) {
            return new ResultVO(2000);
        }
        iBestPolicyToTalentService.asynBestPolicy(1L);
        return new ResultVO(1000, "success");
    }


    /**
     * 更新用户的标签属性
     *
     * @return
     */
    @RequestMapping("update11")
    public ResultVO update11(@RequestParam(value = "token") String token, @RequestParam(value = "talentId") String talentId) {
        if (!StringUtils.equals(s_token, token)) {
            return new ResultVO(2000);
        }
        this.iBestPolicyToTalentService.asynBestPolicyForTalent(Long.valueOf(talentId));
        return new ResultVO(1000, "success");
    }


    /**
     * 更新用户的标签属性
     *
     * @return
     */
    @RequestMapping("update")
    public ResultVO update(@RequestParam(value = "token") String token) {
        if (!StringUtils.equals(s_token, token)) {
            return new ResultVO(2000);
        }
        //iTalentInfoCertificationService.update((long) 2);
        this.iBestPolicyToTalentService.asynBestPolicy(1L);
        return new ResultVO(1000, "success");
    }

    /**
     * 把t_certification_info表中的数据转到t_talent_type表格中
     *
     * @return
     */
    @RequestMapping("cerToTalentTypeDB")
    public ResultVO cerToTalentTypeDB(@RequestParam(value = "token") String token) {
        if (!StringUtils.equals(s_token, token)) {
            return new ResultVO(2000);
        }

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
                        if (!po1.getCardId().equals(tal.getCardId())) {
                            po1.setCardId(tal.getCardId());
                            this.talentTypeMapper.updateByPrimaryKey(po1);
                        }
                    }
                } else {
                    TalentTypePO po1 = new TalentTypePO();
                    po1.setType((byte) 1);
                    po1.setTalentId(po.getTalentId());
                    po1.setCardId(tal.getCardId());
                    this.talentTypeMapper.insert(po1);
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
                            if (StringUtils.equals(e, "0")) {
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
                            if (StringUtils.equals(e, "0")) {
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
                            if (StringUtils.equals(e, "0")) {
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
                            if (StringUtils.equals(e, "0")) {
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
                            if (StringUtils.equals(e, "0")) {
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

    /**
     * 人才申请的符合条件的政策信息进行同步
     *
     * @param token
     * @return
     */
    @RequestMapping("policyApprovalToPoCompliance")
    public ResultVO policyApprovalToPoCompliance(@RequestParam(value = "token") String token) {
        if (!StringUtils.equals(s_token, token)) {
            return new ResultVO(2000);
        }

        List<PolicyApplyPO> policyApplyPOS = this.policyApplyMapper.selectAll();
        for (PolicyApplyPO po : policyApplyPOS) {

            Map<String, Object> map = new HashMap<>(2);
            map.put("talentId", po.getTalentId());
            map.put("policyId", po.getPolicyId());
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            map.put("year", year);

            List<PoCompliancePO> pos = this.poComplianceMapper.selectByPolicyTalent(map);
            if (pos != null && pos.size() > 0) {
                PoCompliancePO poCompliancePO = pos.get(0);
                poCompliancePO.setStatus(po.getStatus());
                if (poCompliancePO.getApplyTime() == null) {
                    poCompliancePO.setApplyTime(po.getCreateTime());
                }
                this.poComplianceMapper.updateByPrimaryKey(poCompliancePO);
            } else {
                PoCompliancePO poCompliancePO = new PoCompliancePO();
                poCompliancePO.setTalentId(po.getTalentId());
                poCompliancePO.setPolicyId(po.getPolicyId());
                poCompliancePO.setApplyTime(po.getCreateTime());
                poCompliancePO.setYear(year);
                poCompliancePO.setStatus(po.getStatus());

                this.poComplianceMapper.insert(poCompliancePO);
            }
        }
        return new ResultVO(1000);
    }


    @RequestMapping("updateTalentInfo")
    public void updateTalentInfo(HttpServletRequest req, @RequestParam(value = "token") String token,
                                 @RequestParam(value = "checktime") String checktime,
                                 HttpServletResponse res) {
        if (!StringUtils.equals(s_token, token)) {
            return;
        }
        MultipartFile file = ((MultipartHttpServletRequest) req).getFile("file");

        String directory = "/tmp/";
        String url = FileUtil.uploadFile
                (file, filePathConfig.getLocalBasePath(), filePathConfig.getProjectDir(),
                        directory, "xls");
        String filePath = filePathConfig.getLocalBasePath() + "/" + url;

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(new File(filePath));
            //解析excel
            POIFSFileSystem pSystem = new POIFSFileSystem(fis);
            //获取整个excel
            HSSFWorkbook hb = new HSSFWorkbook(pSystem);
            //获取第一个表单sheet
            HSSFSheet sheet = hb.getSheetAt(0);
            //获取第一行
            int firstrow = sheet.getFirstRowNum();
            //获取最后一行
            int lastrow = sheet.getLastRowNum();

            Map<Integer, List<String>> errors = new HashMap<>(3);
            //数据库中，未查询到用户
            errors.put(0, new ArrayList<>());
            //更新数据
            errors.put(1, new ArrayList<>());
            //未更新数据
            errors.put(2, new ArrayList<>());
            String[][] excelData = new String[lastrow + 2][10];

            int fixTotal = 0;
            //循环行数依次获取列数
            for (int i = 1; i < lastrow + 1; i++) {
                //获取哪一行i
                Row row = sheet.getRow(i);

                if (row != null) {
                    int firstcell = row.getFirstCellNum();
                    //创建一个集合，用处将每一行的每一列数据都存入集合中
                    for (int j = firstcell; j < 9; j++) {
                        try {
                            if (row.getCell(j) != null) {
                                Cell cell = row.getCell(j);
                                if(cell.getCellType()==CellType.STRING){
                                    excelData[i - 1][j] = row.getCell(j).getStringCellValue();
                                }else if(cell.getCellType()==CellType.NUMERIC){
                                    excelData[i - 1][j] = String.valueOf(cell.getNumericCellValue());
                                }else if(cell.getCellType()==CellType.FORMULA){
                                    excelData[i - 1][j] = String.valueOf(cell.getCellFormula());
                                }

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    //证件号
                    Cell card = row.getCell(3);
                    if (card == null) {
                        continue;
                    } else if (StringUtils.isEmpty(card.getStringCellValue())) {
                        continue;
                    }
                    TalentPO po = talentMapper.selectByIdCard(card.getStringCellValue());

                    if (po != null) {
                        SocialSecurityPO socialSecurityPO = this.socialSecurityMapper.selectByPrimaryKey(po.getTalentId());
                        if (socialSecurityPO == null) {
                            socialSecurityPO = new SocialSecurityPO();
                            socialSecurityPO.setTalentId(po.getTalentId());
                            socialSecurityPO.setCheckTime(DateUtil.str2Date(checktime, DateUtil.YMD));
                            socialSecurityPO.setOldWorkUnit(po.getWorkUnit());
                            socialSecurityPO.setSecuriyWorkUnit(po.getWorkUnit());
                            socialSecurityPO.setCreateTime(new Date());
                            socialSecurityMapper.insert(socialSecurityPO);
                        }
                        boolean needUpdate = false;

                        Cell securityWorkUnit = row.getCell(5);
                        if (securityWorkUnit != null && !StringUtils.isEmpty(securityWorkUnit.getStringCellValue())) {
                            if (!StringUtils.equals(socialSecurityPO.getSecuriyWorkUnit(), securityWorkUnit.getStringCellValue())) {
                                JSONObject object = new JSONObject();
                                object.put("workUnit", socialSecurityPO.getSecuriyWorkUnit());
                                String before = object.toJSONString();
                                socialSecurityPO.setSecuriyWorkUnit(securityWorkUnit.getStringCellValue());
                                needUpdate = true;
                                JSONObject afterObject = new JSONObject();
                                afterObject.put("workUnit", socialSecurityPO.getSecuriyWorkUnit());
                                String after = afterObject.toJSONString();
                                /**
                                 * 记录操作行为
                                 */
                                iEditTalentRecordService.addRecord((long) 1, po.getTalentId(), EditTalentRecordConstant.synchronization,
                                        EditTalentRecordConstant.security_social, before, after, "与社保信息不一致");
//                                logService.insertActionRecord(1, "system", OpsRecordMenuConstant.F_OtherService, OpsRecordMenuConstant.S_TalentActivity,
//                                        "同步\"%s\"的的社保信息", po.getName());
                                fixTotal++;

                            }

                        } else {
                            if (!StringUtils.isEmpty(socialSecurityPO.getSecuriyWorkUnit())) {
                                socialSecurityPO.setSecuriyWorkUnit(null);
                                needUpdate = true;
                            }
                        }

                        Cell time = row.getCell(6);
                        if (time != null && !StringUtils.isEmpty(time.getStringCellValue())) {
                            socialSecurityPO.setSecurityTime(DateUtil.str2Date(time.getStringCellValue(), DateUtil.YHM_NO));
                        } else {
                            socialSecurityPO.setSecurityTime(null);
                        }


                        Cell type = row.getCell(8);
                        if (type != null && !StringUtils.isEmpty(type.getStringCellValue())) {
                            if (StringUtils.equals(type.getStringCellValue(), "在职人员")) {
                                if (socialSecurityPO.getSocialType() != null && socialSecurityPO.getSocialType() != 1) {
                                    needUpdate = true;
                                }
                                socialSecurityPO.setSocialType((byte) 1);
                            } else if (StringUtils.equals(type.getStringCellValue(), "中断人员")) {
                                if (socialSecurityPO.getSocialType() != null && socialSecurityPO.getSocialType() != 2) {
                                    needUpdate = true;
                                }
                                socialSecurityPO.setSocialType((byte) 2);
                            } else {
                                if (socialSecurityPO.getSocialType() != null && socialSecurityPO.getSocialType() != 3) {
                                    needUpdate = true;
                                }
                                socialSecurityPO.setSocialType((byte) 3);
                            }
                        } else {
                            if (socialSecurityPO.getSocialType() != null && socialSecurityPO.getSocialType() != 3) {
                                needUpdate = true;
                            }
                            socialSecurityPO.setSocialType((byte) 3);
                        }

                        socialSecurityPO.setUpdateTime(new Date());

                        this.socialSecurityMapper.updateByPrimaryKey(socialSecurityPO);
                        if (needUpdate) {
                            excelData[i][9] = "更新数据";
                        }
                    } else {
                        excelData[i][9] = "未查到对应用户";
                    }
                }
            }
            fis.close();
            if (fixTotal <= 0) {
                logService.insertActionRecord(1, "system", OpsRecordMenuConstant.F_OtherService, OpsRecordMenuConstant.S_TalentActivity,
                        "修改%s个人才的现工作单位为参保单位", String.valueOf(fixTotal));
            }
            ExcelExportUtil.exportExcel("result_" + file.getOriginalFilename(), null,
                    new String[]{"序号", "姓名", "证件类型", "证件号码", "现工作单位", "参保单位", "本次参保开始时间", "单位性质", "人员类别", "执行操作"},
                    excelData, res);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return;
    }
}
