package com.talentcard.common.bo;
import lombok.Data;
/**
 * @author ChenXU
 * @version 1.0
 * @date Created in 2020/04/20 14:37
 * @description 给激活用的BO
 */
@Data
public class ActivcateBO {
    private Long talentId;
    private Long certId;
    private Long ucId;
    private Long uciId;
    private String cardId;
    private String code;
}
