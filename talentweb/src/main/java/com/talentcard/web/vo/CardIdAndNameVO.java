package com.talentcard.web.vo;

import com.talentcard.common.pojo.CardPO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: jiangzhaojie
 * @date: Created in 15:03 2020/4/28
 * @version: 1.0.0
 * @description: 提取出card表中的cardId和其相对应的名字
 */
@Data
public class CardIdAndNameVO {
    private Long cardId;
    private String cardName;

    public static List<CardIdAndNameVO> convert(List<CardPO> pos) {
        List<CardIdAndNameVO> vos = new ArrayList<>();
        for (CardPO po : pos) {
            CardIdAndNameVO vo = new CardIdAndNameVO();
            vo.setCardId(po.getCardId());
            vo.setCardName(po.getName()+po.getInitialWord()+po.getInitialNum());
            vos.add(vo);
        }
        return vos;
    }
}
