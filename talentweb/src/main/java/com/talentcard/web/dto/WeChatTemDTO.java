package com.talentcard.web.dto;

import java.util.Map;

/**
 * @author: jiangenyong
 * @date: Created in 2020-04-21 9:23
 * @description: TODO
 * @version: 1.0
 */
public class WeChatTemDTO {

    private String touser;

    private String template_id;

    private String url;

    private Map<String, TemplateDataDTO> data;

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

    public Map<String, TemplateDataDTO> getData() {
        return data;
    }

    public void setData(Map<String, TemplateDataDTO> data) {
        this.data = data;
    }
}
