package com.talentcard.web.vo;

import com.talentcard.common.mapper.TalentMapper;
import com.talentcard.common.pojo.TalentPO;
import com.talentcard.common.pojo.UserFeedbackPO;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description:
 * @author: liyuan
 * @data 2020-07-14 20:03
 */
@Data
@Component
public class UserFeedbackVO {
    private Byte pageType;
    private Byte pageItem;
    private Byte relateItem;
    private String proDescribe;
    private Date submitDate;
    private String name;
}
