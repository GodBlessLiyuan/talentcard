package com.talentcard.web.vo;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description:
 * @author: liyuan
 * @data 2020-07-14 20:03
 */
@Data
public class UserFeedbackVO implements Serializable {
    private Byte pageType;
    private String relateItem;
    private String chooseItem;
    private String proDescribe;
    private Date submitDate;
    private String name;
}
