package com.talentcard.web.controller;

import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.ILoginService;
import com.talentcard.web.utils.VerifyUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.UUID;

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

    @RequestMapping("test")
    public ResultVO testcon(@RequestParam(value = "username", required = false) String username,
                                @RequestParam(value = "password", required = false) String password){
        return new ResultVO(1000,"hello");
    }

    /**
     * 在用户输入验证码之前将验证码写进session
     * @param session
     * @param response
     * @throws IOException
     */
    @RequestMapping("/getCodeImg")
    public void getCodeImg(HttpSession session, HttpServletResponse response) throws IOException {
        //服务器通知浏览器不要缓存
        response.setHeader("pragma","no-cache");
        response.setHeader("cache-control","no-cache");
        response.setHeader("expires","0");
        try{
            Object[] objs = VerifyUtil.createImage();
            String code = objs[0].toString();

            // 将验证码code存入session
            session.setAttribute(VERIFY_ID,code);
            BufferedImage image = (BufferedImage) objs[1];
            response.setContentType("image/png");
            OutputStream os = response.getOutputStream();
            //输出验证码图片文件流
            ImageIO.write(image, "png", os);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
