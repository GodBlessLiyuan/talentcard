package com.talentcard.front.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.talentcard.common.pojo.TalentPO;
import com.talentcard.common.utils.StringToObjUtil;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.front.FrontApplication;
import com.talentcard.front.utils.AccessTokenUtil;
import org.apache.poi.ss.formula.functions.T;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.awt.*;
import java.io.FileInputStream;
import java.util.*;
import java.util.List;

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
    public void register() throws Exception {
        JSONObject jsonObject = StringToObjUtil.strToObj("{\n" +
                "    \"openId\": \"oQetQ1YfCzxAX56_870M1eTTnInM\",\n" +
                "    \"verifyCode\": \"123\",\n" +
                "    \"name\": \"钢铁侠\",\n" +
                "    \"sex\": 1,\n" +
                "    \"passport\": \"123456\",\n" +
                "    \"driverCard\": \"bbbb\",\n" +
                "    \"workUnit\": \"召唤师峡谷\",\n" +
                "    \"workLocationType\": 1,\n" +
                "    \"workLocation\": \"阿斯加德\",\n" +
                "    \"political\": 2,\n" +
                "    \"industry\": 8,\n" +
                "    \"industrySecond\": 1,\n" +
                "    \"phone\": \"123\",\n" +
                "    \"education\": 5,\n" +
                "    \"school\": \"战争学院\",\n" +
                "    \"firstClass\": 1,\n" +
                "    \"major\": \"上单霸主\",\n" +
                "    \"profTitleCategory\": 0,\n" +
                "    \"profTitleInfo\": \"\",\n" +
                "    \"profQualityCategory\": 0,\n" +
                "    \"profQualityInfo\": \"\",\n" +
                "    \"honourId\": 10\n" +
                "}", JSONObject.class);
        String url="/talent/register";
        {
            //验证护照
            jsonObject.put("cardType", 2);
            ResultVO resultVO = super.mockMvcPostUrlContent(url, jsonObject.toJSONString());
            assertNotNull(resultVO);
            assertEquals(new Integer(1000), resultVO.getStatus());
        }
        {
            //验证驾照，（没问题，至少sql没问题）
            jsonObject.put("openId", "oQetQ1VLVPb-KOFFvMbuVMSWuuAI");//更改微信号，不然微信号存在了
            jsonObject.put("cardType", 3);//驾照
            jsonObject.put("driverCard", "222222222222");
            ResultVO resultVO = mockMvcPostUrlContent(url, jsonObject.toJSONString());
            assertNotNull(resultVO);
//            assertEquals(new Integer(1000), resultVO.getStatus());
        }
        {
            //验证身份证，没问题（至少sql没问题）
            jsonObject.put("openId", "oQetQ1RhuOojfNuWM-gc38Nk-PCQ");//更改微信号，不然微信号存在了
            jsonObject.put("cardType", 1);//身份证
            jsonObject.put("idCard", "330821199807125253");
            ResultVO resultVO = mockMvcPostUrlContent(url, jsonObject.toJSONString());
            assertNotNull(resultVO);
            assertEquals(new Integer(1000), resultVO.getStatus());
        }
    }


    @Test
    public void findStatus() throws Exception {
        Map<String,String> map=new HashMap<>();
        String url="/talent/findStatus";
        {
            //验证游客身份
            String openId = "fasdjflsdjf";
            map.put("openId",openId);//键值对
            ResultVO resultVO = super.mockMvcPostUrlParams(url, map);
            assertNotNull(resultVO);
            assertEquals(Integer.valueOf(1000),resultVO.getStatus());
            assertNotNull(resultVO.getData());
            assertEquals("000000000",((JSONObject)resultVO.getData()).getString("code"));
        }
        {
            //领取高级卡
            String openId = "oQetQ1ULYaj1Cg5UwNGbQ2hEGIQQ";
            map.put("openId",openId);
            ResultVO resultVO = super.mockMvcPostUrlParams(url, map);
            assertNotNull(resultVO);
            assertEquals(Integer.valueOf(1000),resultVO.getStatus());
            assertNotNull(resultVO.getData());
            JSONObject jsonObject= (JSONObject) resultVO.getData();
            assertEquals("C010000001",jsonObject.getString("code"));
            //注册过
            assertEquals(Integer.valueOf(1),jsonObject.getInteger("status"));
            //不用换卡
            assertEquals(Integer.valueOf(2),jsonObject.getInteger("ifChangeCard"));
            //未认证
            assertEquals(Integer.valueOf(1),jsonObject.getInteger("ifCertificate"));
        }
        {
            //验证注册未注册未领取卡的用户
            String openId = "oQetQ1SMJgI2-lfQ7Yvm6r2KjOqY";
            map.put("openId",openId);
            ResultVO resultVO = super.mockMvcPostUrlParams(url, map);
            assertNotNull(resultVO);
            assertEquals(Integer.valueOf(1000),resultVO.getStatus());
            assertNotNull(resultVO.getData());
            JSONObject jsonObject= (JSONObject) resultVO.getData();
            assertEquals("010000002",jsonObject.getString("code"));
            //注册过
            assertEquals(Integer.valueOf(1),jsonObject.getInteger("status"));
            //不用换卡
            assertEquals(Integer.valueOf(2),jsonObject.getInteger("ifChangeCard"));
            //未认证
            assertEquals(Integer.valueOf(2),jsonObject.getInteger("ifCertificate"));
            //未认证中
            assertEquals(Integer.valueOf(2),jsonObject.getInteger("ifInAudit"));
        }
    }

    //验证码：230554，测试无误
    @Test
    public void sms() {
//        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/talent/sms").param("phone", "17816110072")).
//                andDo(MockMvcResultHandlers.print()).andExpect(status().isOk()).andReturn();
//        ResultVO resultVO = StringToObjUtil.strToObj(mvcResult.getResponse().getContentAsString(), ResultVO.class);//list
//        assertNotNull(resultVO);
//        assertEquals(new Integer(1000), resultVO.getStatus());
    }

    /**
     * 测试出错
     * */
    @Test
    public void identification() throws Exception {
        {
            //	application/x-png或者image/png都可以；openId：gaojiyonghu
            String jsonObject="{\n" +
                    "    \"openId\": \"gaojiyonghu\",\n" +
                    "    \"education\": \"1\",\n" +
                    "    \"school\": \"原味鸡\",\n" +
                    "    \"firstClass\": 1,\n" +
                    "    \"major\": 3,\n" +
                    "    \"profQualityCategory\": \"1\",\n" +
                    "    \"profQualityInfo\": \"1\",\n" +
                    "    \"profTitleCategory\": \"1\",\n" +
                    "    \"profTitleInfo\": \"召唤师峡谷\",\n" +
                    "    \"honourId\": 2,\n" +
                    "    \"graduateTime\": \"2020-02-35\",\n" +
                    "    \"educPicture\": \"2\",\n" +
                    "    \"educPicture2\": \"8\",\n" +
                    "    \"educPicture3\": \"1\",\n" +
                    "    \"profTitlePicture\": \"123\",\n" +
                    "    \"profTitlePicture2\": \"5\",\n" +
                    "    \"profTitlePicture3\": \"战争学院\",\n" +
                    "    \"profQualityPicture\": \"1\",\n" +
                    "    \"profQualityPicture2\": \"上单霸主\",\n" +
                    "    \"profQualityPicture3\": \"0\",\n" +
                    "    \"talentHonourPicture\": \"111\",\n" +
                    "    \"talentHonourPicture2\": \"0\",\n" +
                    "    \"talentHonourPicture3\": \"111\",\n" +
                    "    \"fullTime\":1\n" +
                    "}";
            String url="/talent/identification";
            ResultVO resultVO = super.mockMvcPostUrlContent(url, jsonObject);//list
            assertNotNull(resultVO);
            assertEquals(new Integer(1000), resultVO.getStatus());//2701；
            /***
             * 这数据没法测试了
             *       ActivcateBO oldCard = talentMapper.activate(openId, (byte) 2, (byte) 1);
             *             if (oldCard == null) {
             *                 return new ResultVO(2701);
             *             }
             * */
        }
    }
    /**
     * 该数据库表和数据：Cause: org.h2.jdbc.JdbcSQLException: Column "e.educ_picture2" not found; SQL statement:
     * */
    @Test
    public void findInfo() throws Exception {
        String url="/talent/findInfo";
        Map<String,String> map=new HashMap<>();
        map.put("openId","gaojiyonghu");
        ResultVO<T> resultVO = super.mockMvcPostUrlParams(url, map);
        assertNotNull(resultVO);
        assertEquals(new Integer(1000),resultVO.getStatus());
    }

    //测试token，
    @Test
    public void updateUnionId() throws Exception {
        {
            //token寻找在talentController的fullUnion
            String accessToken = AccessTokenUtil.getAccessToken();
            Map<String,String> map=new HashMap<>();
            map.put("openId", "oQetQ1ULYaj1Cg5UwNGbQ2hEGIQQ");
            map.put("token", accessToken);
            ResultVO<T> resultVO = super.mockMvcPostUrlParams("/talent/updateUnionId", map);
            assertNotNull(resultVO);
            assertEquals(new Integer(1000),resultVO.getStatus());
        }
    }

}