package com.talentcard.front.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.talentcard.common.pojo.TalentPO;
import com.talentcard.common.utils.StringToObjUtil;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.front.FrontApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.awt.*;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author: velve
 * @date: Created in 2020/7/9 17:30
 * @description: TODO
 * @version: 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FrontApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
@AutoConfigureMockMvc
public class TalentControllerTest extends BaseTest{


    @Test
    public void register() {
    }


    @Test
    public void findStatus() throws Exception {
        //验证游客身份
        String openId = "fasdjflsdjf";
        MvcResult actions = mockMvc.perform(MockMvcRequestBuilders.post("/talent/findStatus").param("openId",openId)).
                andDo(MockMvcResultHandlers.print()).andExpect(status().isOk()).andReturn();
        ResultVO<JSONObject> resultVO = StringToObjUtil.strToObj(actions.getResponse().getContentAsString(),ResultVO.class);
        assertNotNull(resultVO);
        assertEquals(Integer.valueOf(1000),resultVO.getStatus());
        assertNotNull(resultVO.getData());
        assertEquals("000000000",resultVO.getData().getString("code"));


        //验证注册未注册未领取卡的用户

        //验证注册领取基础卡用户

    }

    @Test
    public void sms() {
    }

    @Test
    public void identification() {
    }

    @Test
    public void findInfo() {
    }

}