package com.talentcard.web.vo;

import com.talentcard.common.pojo.ScenicPO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: xiahui
 * @date: Created in 2020/5/9 16:26
 * @description: 景区
 * @version: 1.0
 */
@Data
public class ScenicVO implements Serializable {
    private static final long SerialVersionUID = 1L;

    /**
     * 景区ID
     */
    private Long scenicId;
    /**
     * 景区名称
     */
    private String name;
    /**
     * 入园限制
     */
    private String limit;
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
    public static List<ScenicVO> convert(List<ScenicPO> pos) {
        List<ScenicVO> vos = new ArrayList<>();
        for (ScenicPO po : pos) {
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
    public static ScenicVO convert(ScenicPO po) {
        ScenicVO vo = new ScenicVO();
        vo.setScenicId(po.getScenicId());
        vo.setName(po.getName());
        vo.setLimit(null);
        vo.setStatus(po.getStatus());
        vo.setCtime(po.getCreateTime());
        vo.setQrCode(po.getQrCode());
        return vo;
    }
}
