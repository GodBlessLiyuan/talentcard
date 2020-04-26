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
public class WebParameterUtil {
    private static String indexUrl;
    private static String certificateUrl;
    private static String myInfoNotCertificateUrl;
    private static String myRightUrl;
    private static String myInfoAlreadyCertificateUrl;
    private static String myApplicationUrl;

    public static String getIndexUrl() {
        return indexUrl;
    }

    public static String getCertificateUrl() {
        return certificateUrl;
    }

    public static String getMyInfoNotCertificateUrl() {
        return myInfoNotCertificateUrl;
    }

    public static String getMyRightUrl() {
        return myRightUrl;
    }

    public static String getMyInfoAlreadyCertificateUrl() {
        return myInfoAlreadyCertificateUrl;
    }

    public static String getMyApplicationUrl() {
        return myApplicationUrl;
    }

    @Value("${project.indexUrl}")
    private void setIndexUrl(String indexUrl) {
        WebParameterUtil.indexUrl = indexUrl;
    }

    @Value("${project.certificateUrl}")
    public void setCertificateUrl(String certificateUrl) {
        WebParameterUtil.certificateUrl = certificateUrl;
    }

    @Value("${project.myInfoNotCertificateUrl}")
    public void setMyInfoNotCertificateUrl(String myInfoNotCertificateUrl) {
        WebParameterUtil.myInfoNotCertificateUrl = myInfoNotCertificateUrl;
    }

    @Value("${project.myRightUrl}")
    public void setMyRightUrl(String myRightUrl) {
        WebParameterUtil.myRightUrl = myRightUrl;
    }

    @Value("${project.myInfoAlreadyCertificateUrl}")
    public void setMyInfoAlreadyCertificateUrl(String myInfoAlreadyCertificateUrl) {
        WebParameterUtil.myInfoAlreadyCertificateUrl = myInfoAlreadyCertificateUrl;
    }

    @Value("${project.myApplicationUrl}")
    public void setMyApplicationUrl(String myApplicationUrl) {
        WebParameterUtil.myApplicationUrl = myApplicationUrl;
    }

}
