package com.talentcard.web.constant;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;

/**
 * 编辑人才所需要的属性对照表
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-07-17 09:21
 * @description
 */
@Data
@Component
public class EditTalentConstant {
    /**
     * 构造方法执行后的初始化
     */
    @PostConstruct
    public void EditTalentConstantInitialize() {
        //学历
        educationMap.put("1", "中专/高中及以下");
        educationMap.put("2", "大学专科");
        educationMap.put("3", "大学本科");
        educationMap.put("4", "硕士研究生");
        educationMap.put("5", "博士研究生");
        educationMap.put("6", "学校在读");

        //职称
        titleMap.put("1", "助理级");
        titleMap.put("2", "中级");
        titleMap.put("3", "副高级");
        titleMap.put("4", "正高级");

        //职业资格
        titleMap.put("1", "高级工");
        titleMap.put("2", "技师");
        titleMap.put("3", "高级技师");

        //人才荣誉
        honourMap.put("1", "企业高管");
        honourMap.put("2", "文化名家");
        honourMap.put("3", "非遗传承人");
        honourMap.put("4", "寓外乡贤");
        honourMap.put("5", "乡土人才");
        honourMap.put("6", "无");


        //人才类别
        talentCategoryMap.put("1", "基础人才");
        talentCategoryMap.put("2", "柔性人才");
        talentCategoryMap.put("3", "寓外人才");
        talentCategoryMap.put("4", "企业经营管理人才");
        talentCategoryMap.put("5", "高技能人才");
        talentCategoryMap.put("6", "文化社科人才");
        talentCategoryMap.put("7", "乡土人才");
        talentCategoryMap.put("8", "高学历人才");
        talentCategoryMap.put("9", "专技人才");
        talentCategoryMap.put("10", "高端人才");
    }

    private static HashMap<String, String> educationMap = new HashMap<>();
    private static HashMap<String, String> titleMap = new HashMap<>();
    private static HashMap<String, String> qualityMap = new HashMap<>();
    private static HashMap<String, String> honourMap = new HashMap<>();
    private static HashMap<String, String> talentCategoryMap = new HashMap<>();



}
