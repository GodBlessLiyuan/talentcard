package com.talentcard.web.utils;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-06-02 16:20
 * @description
 */
@Data
@Component
public class BatchCertificateUtil {
    /**
     * 构造方法执行后的初始化
     */
    @PostConstruct
    public void BatchCertificateUtilInitialize() {
        talentHonourMap.put("1", "企业高管");
        talentHonourMap.put("2", "文化名家");
        talentHonourMap.put("3", "非遗传承人");
        talentHonourMap.put("4", "寓外乡贤");
        talentHonourMap.put("5", "乡土人才");
        talentHonourMap.put("6", "无");

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

    private static HashMap<String, String> talentHonourMap = new HashMap<>();
    private static HashMap<String, String> talentCategoryMap = new HashMap<>();

    /**
     * 将人才荣誉数字转化为字符串
     *
     * @param talentHonour
     * @return
     */
    public static String convertTalentHonour(Long talentHonour) {
        String talentHonourResult = "";
        if (talentHonour != null) {
            talentHonourResult = talentHonourMap.get(talentHonour.toString());
            if (talentHonourResult == null) {
                talentHonourResult = "";
            }
        }
        return talentHonourResult;
    }

    /**
     * 将人才类别数字转化为字符串
     *
     * @param talentCategoryString
     * @return
     */
    public static String convertTalentCategory(String talentCategoryString) {
        String talentCategoryResult = "";
        String talentCategory = "";
        if (talentCategoryString != null && !talentCategoryString.equals("")) {
            String[] talentCategoryArray = talentCategoryString.split(",");
            Integer length = talentCategoryArray.length;
            for (int i = 0; i < length; i++) {
                talentCategory = talentCategoryMap.get(talentCategoryArray[i]);
                if (talentCategory != null) {
                    talentCategoryResult = talentCategoryResult + talentCategory + ";";
                }
            }
        }
        return talentCategoryResult;
    }
}
