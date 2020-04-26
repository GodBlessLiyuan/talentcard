package com.talentcard.front.utils;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-04-26 17:56
 * @description
 */
@Component
public class FrontParameterUtil {
    private static String indexUrl;

    public static String getIndexUrl() {
        return indexUrl;
    }

    @Value("${project.indexUrl}")
    private void setIndexUrl(String indexUrl) {
        FrontParameterUtil.indexUrl = indexUrl;
    }
}
