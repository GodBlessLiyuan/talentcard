package com.talentcard.miniprogram.controller;

import com.talentcard.common.vo.ResultVO;
import com.talentcard.miniprogram.service.IQrCodeService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @author: xiahui
 * @date: Created in 2020/6/3 15:48
 * @description: 二维码
 * @version: 1.0
 */
@RequestMapping("qrcode")
@RestController
public class QrCodeController {
    @Autowired
    private IQrCodeService qrCodeService;

    @RequestMapping("create")
    public ResultVO create(HttpServletResponse res, @Param("openId") String openId) {
        return qrCodeService.create(res, openId);
    }
}
