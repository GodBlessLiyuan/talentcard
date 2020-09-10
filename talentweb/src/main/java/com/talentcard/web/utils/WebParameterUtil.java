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
    private static String myBaseRightUrl;
    private static String mySeniorRightUrl;
    private static String myInfoAlreadyCertificateUrl;
    private static String myApplicationUrl;
    private static String freeBenefitsUrl;
    private static String appBrandPass;
    private static String appBrandUserName;
    private static String eventDetail;

    public static String getIndexUrl() {
        return indexUrl;
    }

    public static String getCertificateUrl() {
        return certificateUrl;
    }

    public static String getMyInfoNotCertificateUrl() {
        return myInfoNotCertificateUrl;
    }

    public static String getMyBaseRightUrl() {
        return myBaseRightUrl;
    }

    public static String getMySeniorRightUrl() {
        return mySeniorRightUrl;
    }

    public static String getMyInfoAlreadyCertificateUrl() {
        return myInfoAlreadyCertificateUrl;
    }

    public static String getMyApplicationUrl() {
        return myApplicationUrl;
    }

    public static String getFreeBenefitsUrl() {
        return freeBenefitsUrl;
    }

    public static String getAppBrandPass() {
        return appBrandPass;
    }

    public static String getAppBrandUserName() {
        return appBrandUserName;
    }

    public static String getEventDetail() {
        return eventDetail;
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

    @Value("${project.myBaseRightUrl}")
    public void setMyRightUrl(String myBaseRightUrl) {
        WebParameterUtil.myBaseRightUrl = myBaseRightUrl;
    }

    @Value("${project.mySeniorRightUrl}")
    public void mySeniorRightUrl(String mySeniorRightUrl) {
        WebParameterUtil.mySeniorRightUrl = mySeniorRightUrl;
    }

    @Value("${project.myInfoAlreadyCertificateUrl}")
    public void setMyInfoAlreadyCertificateUrl(String myInfoAlreadyCertificateUrl) {
        WebParameterUtil.myInfoAlreadyCertificateUrl = myInfoAlreadyCertificateUrl;
    }

    @Value("${project.myApplicationUrl}")
    public void setMyApplicationUrl(String myApplicationUrl) {
        WebParameterUtil.myApplicationUrl = myApplicationUrl;
    }

    @Value("${project.freeBenefitsUrl}")
    private void setFreeBenefitsUrl(String freeBenefitsUrl) {
        WebParameterUtil.freeBenefitsUrl = freeBenefitsUrl;
    }

    @Value("${project.appBrandPass}")
    private void setAppBrandPass(String appBrandPass) {
        WebParameterUtil.appBrandPass = appBrandPass;
    }

    @Value("${project.appBrandUserName}")
    private void setAppBrandUserName(String appBrandUserName) {
        WebParameterUtil.appBrandUserName = appBrandUserName;
    }

    @Value("${project.eventDetail}")
    private void setEventDetail(String eventDetail) {
        WebParameterUtil.eventDetail = eventDetail;
    }
}
