package com.talentcard.common.bo;

import com.talentcard.common.pojo.AuthenticationPO;
import com.talentcard.common.pojo.UserPO;
import lombok.Data;

import java.util.List;

/**
 * @author ChenXU
 * @version 1.0
 * @date Created in 2020/03/25 14:22
 * @description
 */
@Data
public class UserBO extends UserPO {
    List<AuthenticationPO> userDetail;
}
