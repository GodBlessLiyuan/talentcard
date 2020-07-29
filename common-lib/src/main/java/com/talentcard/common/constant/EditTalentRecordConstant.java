package com.talentcard.common.constant;

import java.util.HashMap;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-07-09 09:24
 * @description
 */
public class EditTalentRecordConstant {
    /**
     * 操作种类
     */
    //新增
    public static final Byte addType = 1;
    //删除
    public static final Byte deleteType = 2;
    //修改
    public static final Byte editType = 3;

    /**
     * 操作对象内容
     */
    //学历
    public static final Byte educationContent = 1;
    //职称类别
    public static final Byte titleContent = 2;
    //职业资格
    public static final Byte qualityContent = 3;
    //人才荣誉
    public static final Byte honourContent = 4;
    //人才类别
    public static final Byte talentCategoryContent = 5;
    //基本信息
    public static final Byte basicInfoContent = 6;
    //人才卡
    public static final Byte talentCard = 7;


    public final static HashMap<Byte, String> operationTypeMap = new HashMap<>(3);
    public final static HashMap<Byte, String> operationContentMap = new HashMap<>(6);
    static {
        operationTypeMap.put(addType, "新增");
        operationTypeMap.put(deleteType, "删除");
        operationTypeMap.put(editType, "修改");

        operationContentMap.put(educationContent,"学历");
        operationContentMap.put(titleContent,"职称类型");
        operationContentMap.put(qualityContent,"职业资格");
        operationContentMap.put(honourContent,"人才荣誉");
        operationContentMap.put(talentCategoryContent,"人才类别");
        operationContentMap.put(basicInfoContent,"基本信息");
    }
}
