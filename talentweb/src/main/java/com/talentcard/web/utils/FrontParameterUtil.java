package com.talentcard.web.utils;

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
    private static String publicPath;

    public static String getIndexUrl() {
        return indexUrl;
    }

    public static String getPublicPath() {
        return publicPath;
    }

    @Value("${project.indexUrl}")
    private void setIndexUrl(String indexUrl) {
        FrontParameterUtil.indexUrl = indexUrl;
    }


    @Value("${file.publicPath}")
    private void setPublicPath(String publicPath) {
        FrontParameterUtil.publicPath = publicPath;
    }
}
