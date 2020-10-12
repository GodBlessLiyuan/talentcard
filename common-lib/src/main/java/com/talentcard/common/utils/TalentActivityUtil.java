package com.talentcard.common.utils;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;

/**
 * 拆分人才类别；学历；职称；职业资格；人才荣誉等
 * 从String变成List<Long>
 *
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-05-11 19:41
 * @description
 */
@Component
public class TalentActivityUtil {
    public static ArrayList<Long> splitCategory(String talentCategory) {
        if (!StringUtils.isEmpty(talentCategory)) {
            String[] categoryArray = talentCategory.split(",");
            ArrayList<Long> arrayList = new ArrayList<>(categoryArray.length);
            for (String talentCategoryString : categoryArray) {
                try {
                    arrayList.add(Long.parseLong(talentCategoryString));
                } catch (NumberFormatException e) {
                    throw new NumberFormatException("人才福利里，人才类别字符串转Long错误！");
                }
            }
            return arrayList;
        } else {
            return new ArrayList<>(0);
        }
    }

    public static ArrayList<Long> splitEducation(String education) {
        String[] educationArray = education.split(",");
        ArrayList<Long> arrayList = new ArrayList<>();
        for (String educationString : educationArray) {
            try {
                arrayList.add(Long.parseLong(educationString));
            } catch (NumberFormatException e) {
                throw new NumberFormatException("人才福利里，人才类别字符串转Long错误！");
            }
        }
        return arrayList;
    }

    public static ArrayList<Long> splitQuality(String quality) {
        String[] qualityArray = quality.split(",");
        ArrayList<Long> arrayList = new ArrayList<>();
        for (String qualityString : qualityArray) {
            try {
                arrayList.add(Long.parseLong(qualityString));
            } catch (NumberFormatException e) {
                throw new NumberFormatException("人才福利里，人才类别字符串转Long错误！");
            }
        }
        return arrayList;
    }

    public static ArrayList<Long> splitTitle(String title) {
        String[] titleArray = title.split(",");
        ArrayList<Long> arrayList = new ArrayList<>();
        for (String titleString : titleArray) {
            try {
                arrayList.add(Long.parseLong(titleString));
            } catch (NumberFormatException e) {
                throw new NumberFormatException("人才福利里，人才类别字符串转Long错误！");
            }
        }
        return arrayList;
    }

    public static ArrayList<Long> splitHonour(String honour) {
        String[] honourArray = honour.split(",");
        ArrayList<Long> arrayList = new ArrayList<>();
        for (String honourString : honourArray) {
            try {
                arrayList.add(Long.parseLong(honourString));
            } catch (NumberFormatException e) {
                throw new NumberFormatException("人才福利里，人才类别字符串转Long错误！");
            }
        }
        return arrayList;
    }

}
