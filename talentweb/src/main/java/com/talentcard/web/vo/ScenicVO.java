package com.talentcard.web.vo;

import com.talentcard.common.pojo.ScenicPO;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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
@Component
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


    private static String publicPath;

    @Value("${file.publicPath}")
    private void setPublicPath(String publicPath) {
        ScenicVO.publicPath = publicPath;
    }

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
        String limit = po.getRate() +
                (po.getUnit() == 1 ? "年" : "月") +
                "/" +
                po.getTimes() +
                "次";
        vo.setLimit(limit);
        vo.setStatus(po.getStatus());
        vo.setCtime(po.getCreateTime());
        if (null != po.getQrCode()) {
            vo.setQrCode(publicPath + po.getQrCode());
        }
        return vo;
    }
}
