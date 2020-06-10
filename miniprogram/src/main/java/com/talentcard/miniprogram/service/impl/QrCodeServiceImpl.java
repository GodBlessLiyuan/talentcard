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
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
    public ResultVO create(String openId) {
        String sb = openId +
                RandomUtil.getRandomString(8) +
                DateUtil.date2Str(new Date(), DateUtil.YMD_HMS);
        String qrCodeUrl = DigestUtils.md5DigestAsHex(sb.getBytes());
        redisTemplate.opsForValue().set(qrCodeUrl, openId, 10, TimeUnit.MINUTES);

        try {
            BufferedImage image = QrCodeUtil.createImage(qrCodeUrl, null, true);
            return new ResultVO<>(1000, BufferedImageToBase64(image));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResultVO<>(1000);
    }


    /**
     * BufferedImage 编码转换为 base64
     *
     * @param bufferedImage
     * @return
     */
    private static String BufferedImageToBase64(BufferedImage bufferedImage) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();//io流
        try {
            ImageIO.write(bufferedImage, "png", baos);//写入流中
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] bytes = baos.toByteArray();//转换成字节
        BASE64Encoder encoder = new BASE64Encoder();
        String png_base64 = encoder.encodeBuffer(bytes).trim();//转换成base64串
        png_base64 = png_base64.replaceAll("\n", "").replaceAll("\r", "");//删除 \r\n
        return "data:image/jpg;base64," + png_base64;
    }
}
