package com.talentcard.miniprogram.service.impl;

import com.talentcard.common.utils.DateUtil;
import com.talentcard.common.utils.QrCodeUtil;
import com.talentcard.common.utils.RandomUtil;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.miniprogram.service.IQrCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author: xiahui
 * @date: Created in 2020/6/3 15:50
 * @description: 二维码
 * @version: 1.0
 */
@Service
public class QrCodeServiceImpl implements IQrCodeService {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public ResultVO create(HttpServletResponse res, String openId) {
        String sb = openId +
                RandomUtil.getRandomString(8) +
                DateUtil.date2Str(new Date(), DateUtil.YMD_HMS);
        String qrCodeUrl = DigestUtils.md5DigestAsHex(sb.getBytes());
        redisTemplate.opsForValue().set(qrCodeUrl, openId, 10, TimeUnit.MINUTES);

        try {
            BufferedImage image = QrCodeUtil.createImage(qrCodeUrl, null, true);
            ImageIO.write(image, QrCodeUtil.FORMAT, res.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResultVO<>(1000, qrCodeUrl);
    }
}
