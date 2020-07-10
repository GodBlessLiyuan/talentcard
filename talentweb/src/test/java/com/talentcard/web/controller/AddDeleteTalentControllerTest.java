package com.talentcard.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.talentcard.common.dto.EducationDTO;
import com.talentcard.common.utils.StringToObjUtil;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.BaseTest;
import com.talentcard.web.WebApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author: velve
 * @date: Created in 2020/7/10 20:59
 * @description: TODO
 * @version: 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
@AutoConfigureMockMvc
public class AddDeleteTalentControllerTest extends BaseTest {

    @Test
    public void addEducation() throws Exception {

        EducationDTO educationDTO = JSONObject.parseObject("{\n" +
                "    \"education\":\"1\",\n" +
                "    \"school\":\"新增学历测试1\",\n" +
                "    \"firstClass\":\"1\",\n" +
                "    \"major\":\"新增学历测试1\",\n" +
                "    \"educPicture\":\"新增学历测试1\",\n" +
                "    \"openId\":\"oQetQ1ULYaj1Cg5UwNGbQ2hEGIQQ\",\n" +
                "    \"graduateTime\":\"新增学历测试1\"\n" +
                "}", EducationDTO.class);

//        session.setAttribute("userId",1L);
        MvcResult actions = mockMvc.perform(MockMvcRequestBuilders.post("/addDeleteTalent/addEducation").
                contentType(MediaType.APPLICATION_JSON_UTF8).
                content(JSONObject.toJSONString(educationDTO))).
                andDo(MockMvcResultHandlers.print()).andReturn();
        ResultVO resultVO = StringToObjUtil.strToObj(actions.getResponse().getContentAsString(), ResultVO.class);
        assertNotNull(resultVO);
        assertEquals(Integer.valueOf(1000), resultVO.getStatus());
    }

    @Test
    public void addProfQuality() {
    }

    @Test
    public void addProfTitle() {
    }

    @Test
    public void addTalentHonour() {
    }

    @Test
    public void deleteEducation() {
    }

    @Test
    public void deleteProfQuality() {
    }

    @Test
    public void deleteProfTitle() {
    }

    @Test
    public void deleteTalentHonour() {
    }
}