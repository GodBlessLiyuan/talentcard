package com.talentcard.common.utils;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author: velve
 * @date: Created in 2020/9/5 16:55
 * @description: TODO
 * @version: 1.0
 */
public class StringSortUtil {

    /**
     * 将allList排除旧的oldList，添加上新的newList
     *
     * @param allList
     * @param oldList
     * @param newList
     * @return
     */
    public static String sort(String allList, String oldList, String newList) {

        String s_allList[] = allList != null ? allList.split(",") : new String[0];
        String s_oldList[] = oldList != null ? oldList.split(",") : new String[0];
        String s_newList[] = newList != null ? newList.split(",") : new String[0];

        List<String> datas = new ArrayList<>();
        for (String key : s_allList) {
            if (!StringUtils.isEmpty(key)) {
                if (!datas.contains(key)) {
                    datas.add(key);
                }
            }
        }
        List<String> removeList = new ArrayList<>(s_oldList.length);
        for (String key : s_oldList) {
            if (!StringUtils.isEmpty(key)) {
                if (!removeList.contains(key)) {
                    removeList.add(key);
                }
            }
        }
        datas.removeAll(removeList);

        for (String key : s_newList) {
            if (!StringUtils.isEmpty(key)) {
                if (!datas.contains(key)) {
                    datas.add(key);
                }
            }
        }

        Collections.sort(datas, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                try {
                    if (!StringUtils.isEmpty(o1) && !StringUtils.isEmpty(o2)) {
                        Integer i1 = Integer.valueOf(o1);
                        Integer i2 = Integer.valueOf(o2);
                        return i1 > i2 ? 1 : -1;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });

        String[] newArray = datas.toArray(new String[1]);
        return String.join(",", newArray);
    }

    public static void main(String args[]) {
        System.out.println(sort("", "1,2,3", "4,2"));
    }
}
