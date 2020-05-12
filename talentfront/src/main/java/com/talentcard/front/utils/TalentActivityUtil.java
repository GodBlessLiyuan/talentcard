package com.talentcard.front.utils;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-05-11 19:41
 * @description
 */
@Component
public class TalentActivityUtil {
    public static ArrayList<Long> splitCategory(String talentCategory) {
        String[] categoryArray = talentCategory.split(",");
        ArrayList<Long> arrayList = new ArrayList<>();
        for (String category : categoryArray) {
            try {
                arrayList.add(Long.parseLong(category));
            } catch (NumberFormatException e) {
                throw new NumberFormatException("人才福利里，人才类别字符串转Long错误！");
            }
        }
        return arrayList;
    }

}
