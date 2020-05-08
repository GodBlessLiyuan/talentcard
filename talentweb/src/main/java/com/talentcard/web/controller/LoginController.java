package com.talentcard.web.controller;

import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.ILoginService;
import com.talentcard.web.utils.VerifyUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Encoder;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author: jiangzhaojie
 * @date: Created in 15:36 2020/4/9
 * @version: 1.0.0
 * @description:
 */
@RestController
@RequestMapping("login")
public class LoginController {

    @Resource
    ILoginService loginService;

    public static final String VERIFY_ID = "verifyKeyLogo";
    /**
     * 用户登录验证
     */
    @RequestMapping("checkLogin")
    public ResultVO login(HttpSession session,
                          HttpServletResponse response,
                          @RequestParam(value = "username", required = false) String username,
                          @RequestParam(value = "password", required = false) String password,
                          @RequestParam(value = "checkCode",required = false) String checkCode) {
        return loginService.login(session,response,username,password,checkCode);
    }


    /**
     * 用户登出
     * @param request
     * @return
     */
    @RequestMapping("logOut")
    public ResultVO quit(HttpServletRequest request) {
        return loginService.quit(request);
    }

    /**
     * 在用户输入验证码之前将验证码写进session
     * @param session
     * @param response
     * @throws IOException
     */
    @RequestMapping("/getCodeImg")
    public ResultVO getCodeImg(HttpSession session, HttpServletResponse response) throws IOException {
        //服务器通知浏览器不要缓存
        response.setHeader("pragma", "no-cache");
        response.setHeader("cache-control", "no-cache");
        response.setHeader("expires", "0");
        String png_base64 = null;
        try {
            Object[] objs = VerifyUtil.createImage();
            String code = objs[0].toString();
            // 将验证码code存入session
            session.setAttribute(VERIFY_ID, code);
            BufferedImage image = (BufferedImage) objs[1];
            response.setContentType("image/png");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            //输出验证码图片文件流
            ImageIO.write(image, "png", baos);
            byte[] bytes = baos.toByteArray();
            BASE64Encoder encoder = new BASE64Encoder();
            png_base64 = encoder.encodeBuffer(bytes).trim();
            png_base64 = png_base64.replaceAll("\n", "").replaceAll("\r", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResultVO<>(1000, png_base64);
    }
}
