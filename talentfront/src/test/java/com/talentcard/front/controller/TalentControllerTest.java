package com.talentcard.front.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.talentcard.common.pojo.TalentPO;
import com.talentcard.common.utils.StringToObjUtil;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.front.FrontApplication;
import com.talentcard.front.utils.AccessTokenUtil;
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
            ResultVO<JSONObject> resultVO = super.moclMvcPostUrlParams(url, map);
            assertNotNull(resultVO);
            assertEquals(Integer.valueOf(1000),resultVO.getStatus());
            assertNotNull(resultVO.getData());
            assertEquals("000000000",resultVO.getData().getString("code"));
        }

        {
            //领取高级卡
            String openId = "oQetQ1ULYaj1Cg5UwNGbQ2hEGIQQ";
            map.put("openId",openId);
            ResultVO<JSONObject> resultVO = super.moclMvcPostUrlParams(url, map);
            assertNotNull(resultVO);
            assertEquals(Integer.valueOf(1000),resultVO.getStatus());
            assertNotNull(resultVO.getData());
            assertEquals("C010000001",resultVO.getData().getString("code"));
            //注册过
            assertEquals(Integer.valueOf(1),resultVO.getData().getInteger("status"));
            //不用换卡
            assertEquals(Integer.valueOf(2),resultVO.getData().getInteger("ifChangeCard"));
            //未认证
            assertEquals(Integer.valueOf(1),resultVO.getData().getInteger("ifCertificate"));
        }
        {
            //验证注册未注册未领取卡的用户
            String openId = "oQetQ1SMJgI2-lfQ7Yvm6r2KjOqY";
            map.put("openId",openId);
            ResultVO<JSONObject> resultVO = super.moclMvcPostUrlParams(url, map);
            assertNotNull(resultVO);
            assertEquals(Integer.valueOf(1000),resultVO.getStatus());
            assertNotNull(resultVO.getData());
            assertEquals("010000002",resultVO.getData().getString("code"));
            //注册过
            assertEquals(Integer.valueOf(1),resultVO.getData().getInteger("status"));
            //不用换卡
            assertEquals(Integer.valueOf(2),resultVO.getData().getInteger("ifChangeCard"));
            //未认证
            assertEquals(Integer.valueOf(2),resultVO.getData().getInteger("ifCertificate"));
            //未认证中
            assertEquals(Integer.valueOf(2),resultVO.getData().getInteger("ifInAudit"));
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

    //包含文件上传
    @Test
    public void identification() throws Exception {

        {
            JSONObject jsonObject=StringToObjUtil.strToObj("",JSONObject.class);
            Set<String> strings = jsonObject.keySet();
            for(String key:jsonObject.keySet()){
                String value= (String) jsonObject.get(key);
            }
            //	application/x-png或者image/png都可以；openId：gaojiyonghu
            MockMultipartFile mockMultipartFile = new MockMultipartFile("educPicture", "图标.png","image/png",new FileInputStream("src\\test\\images\\share1.png"));
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.fileUpload("/talent/identification").
                    file(mockMultipartFile).
                    param("openId", "oQetQ1Qe4bIp5h7mmsmNOwVXAn3U").
                    param("education", String.valueOf(1)).//num类型，学历
                    param("school", "北邮大学").//学校
                    param("firstClass", "0").//是否为双一流
                    param("major", "软件工程").
                    param("graduateTime", "2020:1:30").//专业
                    param("profTitleCategory", String.valueOf(1)).  //职称类别
                    param("profQualityCategory", "1"). //职业资格 num
                    param("honourId", "1") //人才荣誉
            ).andDo(MockMvcResultHandlers.print()).andExpect(status().isOk()).andReturn();
            ResultVO resultVO = StringToObjUtil.strToObj(mvcResult.getResponse().getContentAsString(), ResultVO.class);//list
            assertNotNull(resultVO);
            assertEquals(new Integer(1000), resultVO.getStatus());
        }
    }

    @Test
    public void findInfo() throws Exception {
        String accessToken = AccessTokenUtil.getAccessToken();
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/talent/findInfo").
                param("openId","gaojiyonghu")).
                andDo(MockMvcResultHandlers.print()).andExpect(status().isOk()).andReturn();
        ResultVO<JSONObject> resultVO = StringToObjUtil.strToObj(mvcResult.getResponse().getContentAsString(), ResultVO.class);
        assertNotNull(resultVO);
        assertEquals(new Integer(1000),resultVO.getStatus());
    }
    //代码内容和updateUnionId一样，不用测试
    @Test
    public void fullUnion(){
    }

    //测试token，
    @Test
    public void updateUnionId() throws Exception {
        {
            //token寻找在talentController的fullUnion
            String accessToken = AccessTokenUtil.getAccessToken();
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/talent/updateUnionId").
                    param("token", accessToken).
                    param("openId", "oQetQ1ULYaj1Cg5UwNGbQ2hEGIQQ")).//随便找的一个
                    andDo(MockMvcResultHandlers.print()).andExpect(status().isOk()).andReturn();
            ResultVO<JSONObject> resultVO = StringToObjUtil.strToObj(mvcResult.getResponse().getContentAsString(), ResultVO.class);
            assertNotNull(resultVO);
            assertEquals(new Integer(1000),resultVO.getStatus());
        }
    }

}