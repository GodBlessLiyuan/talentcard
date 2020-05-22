package com.talentcard.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author: velve
 * @date: Created in 2020/5/20 19:48
 * @description: 文件路径基础配置类
 * @version: 1.0
 */
@Data
@ConfigurationProperties(prefix = "vbooster.file", ignoreUnknownFields = true)
public class FilePathConfig {

    /**
     * 本地访问根目录
     */
    private String localBasePath;

    /**
     * 网络访问根目录
     */
    private String publicBasePath;

    /**
     * 网络访问微信根目录
     */
    private String publicWxBasePath;
    /**
     * 项目目录
     */
    private String projectDir = "/talentcard/";

    /**
     * 附件目录
     */
    private String annexDir = "/annex/";

    /**
     * 景区图片目录
     */
    private String scenicDir = "/scenic/";
    /**
     * 农家乐图片目录
     */
    private String farmHouseDir = "/farmhouse/";
    /**
     * 农家乐和景区访问二维码目录名
     */
    private String qrCodeDir = "/qrcode/";
    /**
     * 微信会员卡背景图目录名
     */
    private String cardBackgroundDir = "/cardBackground/";

    /**
     * 上传学位证书
     */
    private String educationDir = "/education/";

    /**
     * 上传职称证书
     */
    private String profTitleDir = "/profTitle/";


    /**
     * 上传职业资格证书
     */
    private String profQualityDir = "/profQuality/";

    /**
     * 导出人才excel表格
     */
    private String excelDir = "/excel/";

    /**
     * 上传人才荣誉
     */
    private String talentHonourDir = "/talentHonourDir/";

    /**
     * 内部调用对外访问网络地址
     */
    private static String staticPublicBasePath;

    /**
     * 内部调用获取微信端访问路径的根目录
     * @return
     */
    private static String staticWxPublicBasePath;

    public static String getStaticPublicBasePath(){
        return staticPublicBasePath;
    }

    public String getPublicBasePath(){
        return this.publicBasePath;
    }

    public String getLocalBasePath() {
        return localBasePath;
    }

    public void setLocalBasePath(String localBasePath) {
        staticPublicBasePath = localBasePath;
        this.localBasePath = localBasePath;
    }

    public static String getStaticPublicWxBasePath() {
        return staticWxPublicBasePath;
    }

    public void setPublicWxBasePath(String publicWxBasePath) {
        staticWxPublicBasePath = publicWxBasePath;
        this.publicWxBasePath = publicWxBasePath;
    }
}
