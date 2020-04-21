package com.talentcard.front.dto;

import java.util.Map;

/**
 * @author: jiangenyong
 * @date: Created in 2020-04-21 9:23
 * @description: TODO
 * @version: 1.0
 */
public class WeChatTemDto {

    private String touser;

    private String template_id;

    private String url;

    private Map<String, TemplateDataDto> data;

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, TemplateDataDto> getData() {
        return data;
    }

    public void setData(Map<String, TemplateDataDto> data) {
        this.data = data;
    }
}
