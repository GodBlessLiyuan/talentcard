package com.talentcard.web.vo;

import com.talentcard.common.config.FilePathConfig;
import com.talentcard.common.pojo.BannerPO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author: xiahui
 * @date: Created in 2020/6/2 20:20
 * @description: banner 配置
 * @version: 1.0
 */
@Data
public class BannerVO implements Serializable {
    private static final long SerialVersionUID = 1L;

    private Long bid;
    private String name;
    private String picture;
    private String jump;
    private Byte type;
    private Byte status;
    private Date ctime;

    /**
     * pos 转 vos
     *
     * @param pos
     * @return
     */
    public static List<BannerVO> convert(List<BannerPO> pos) {
        List<BannerVO> vos = new ArrayList<>();

        for (BannerPO po : pos) {
            vos.add(convert(po));
        }

        return vos;
    }

    public static BannerVO convert(BannerPO po) {
        BannerVO vo = new BannerVO();

        vo.setBid(po.getBannerId());
        vo.setName(po.getName());
        vo.setPicture(FilePathConfig.getStaticPublicBasePath() + po.getPicture());
        vo.setJump(po.getJump());
        vo.setType(po.getType());
        vo.setStatus(po.getStatus());
        vo.setCtime(po.getCreateTime());

        return vo;
    }
}
