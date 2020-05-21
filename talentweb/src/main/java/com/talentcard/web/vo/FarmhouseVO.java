package com.talentcard.web.vo;

import com.talentcard.common.pojo.FarmhousePO;
import com.talentcard.common.config.FilePathConfig;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: xiahui
 * @date: Created in 2020/5/11 18:54
 * @description: 农家乐
 * @version: 1.0
 */
@Data
public class FarmhouseVO implements Serializable {
    private static final long SerialVersionUID = 1L;

    /**
     * 农家乐ID
     */
    private Long farmhouseId;
    /**
     * 农家乐名称
     */
    private String name;
    /**
     * 优惠折扣
     */
    private Double discount;
    /**
     * 状态
     */
    private Byte status;
    /**
     * 创建时间
     */
    private Date ctime;
    /**
     * 二维码
     */
    private String qrCode;



    /**
     * pos 转 vos
     *
     * @param pos
     * @return
     */
    public static List<FarmhouseVO> convert(List<FarmhousePO> pos) {
        List<FarmhouseVO> vos = new ArrayList<>();
        for (FarmhousePO po : pos) {
            vos.add(convert(po));
        }
        return vos;
    }

    /**
     * po 转 vo
     *
     * @param po
     * @return
     */
    public static FarmhouseVO convert(FarmhousePO po) {
        FarmhouseVO vo = new FarmhouseVO();
        vo.setFarmhouseId(po.getFarmhouseId());
        vo.setName(po.getName());
        vo.setDiscount(po.getDiscount());
        vo.setStatus(po.getStatus());
        vo.setCtime(po.getCreateTime());
        if (null != po.getQrCode()) {
            vo.setQrCode(FilePathConfig.getStaticPublicBasePath() + po.getQrCode());
        }
        return vo;
    }
}
