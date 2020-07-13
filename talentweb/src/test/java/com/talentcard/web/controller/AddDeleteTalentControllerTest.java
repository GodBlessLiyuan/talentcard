package com.talentcard.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.talentcard.common.bo.TalentBO;
import com.talentcard.common.dto.EducationDTO;
import com.talentcard.common.pojo.EducationPO;
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
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
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

        EducationDTO educationDTO = new EducationDTO();
        educationDTO.setEducation(1);
        educationDTO.setSchool("新增学历测试1");
        educationDTO.setFirstClass((byte)1);
        educationDTO.setMajor("新增学历测试1");
        educationDTO.setEducPicture("picture");
        educationDTO.setOpenId("gaojiyonghu");
        educationDTO.setGraduateTime("setGraduateTime");

        {
            MvcResult actions = mockMvc.perform(MockMvcRequestBuilders.post("/addDeleteTalent/addEducation").
                    contentType(MediaType.APPLICATION_JSON_UTF8).
                    content(JSONObject.toJSONString(educationDTO))).
                    andDo(MockMvcResultHandlers.print()).andReturn();
            ResultVO resultVO = StringToObjUtil.strToObj(actions.getResponse().getContentAsString(), ResultVO.class);
            assertNotNull(resultVO);
            assertEquals(Integer.valueOf(1000), resultVO.getStatus());
        }


        {
            //重复提交
            MvcResult actions = mockMvc.perform(MockMvcRequestBuilders.post("/addDeleteTalent/addEducation").
                    contentType(MediaType.APPLICATION_JSON_UTF8).
                    content(JSONObject.toJSONString(educationDTO))).
                    andDo(MockMvcResultHandlers.print()).andReturn();
            ResultVO resultVO = StringToObjUtil.strToObj(actions.getResponse().getContentAsString(), ResultVO.class);
            assertNotNull(resultVO);
            assertEquals(Integer.valueOf(2558), resultVO.getStatus());
        }

        {
            //查询结果
            MockHttpServletRequestBuilder findTalentCertificationDetail = MockMvcRequestBuilders.post("/editTalent/findTalentCertificationDetail")
                    .param("openId",educationDTO.getOpenId()).contentType(MediaType.APPLICATION_FORM_URLENCODED);//有@RequestParam注解优先对应,否则对应着表单的input标签的name属性的  值及value
            MvcResult actions = mockMvc.perform(findTalentCertificationDetail).
                    andDo(MockMvcResultHandlers.print()).andReturn();
            ResultVO<JSONObject> resultVO = StringToObjUtil.strToObj(actions.getResponse().getContentAsString(), ResultVO.class);
            assertNotNull(resultVO);
            assertEquals(Integer.valueOf(1000), resultVO.getStatus());
            assertNotNull(resultVO.getData());
            JSONObject data = resultVO.getData();
            TalentBO talentBO = StringToObjUtil.strToObj(data.getJSONObject("talentInfo").toJSONString(),TalentBO.class);
            assertNotNull(talentBO);
            EducationPO newEducation = talentBO.getEducationPOList().get(0);
            assertEquals(newEducation.getEducation(),educationDTO.getEducation());
            assertEquals(newEducation.getEducPicture(), educationDTO.getEducPicture());
            assertEquals(newEducation.getFirstClass(), educationDTO.getFirstClass());
            assertEquals(newEducation.getMajor(), educationDTO.getMajor());
            assertEquals(newEducation.getSchool(), educationDTO.getSchool());
            assertEquals(newEducation.getGraduateTime(), educationDTO.getGraduateTime());
        }


        {
            //添加第二个
            educationDTO.setEducation(2);
            MvcResult actions = mockMvc.perform(MockMvcRequestBuilders.post("/addDeleteTalent/addEducation").
                    contentType(MediaType.APPLICATION_JSON_UTF8).
                    content(JSONObject.toJSONString(educationDTO))).
                    andDo(MockMvcResultHandlers.print()).andReturn();
            ResultVO resultVO = StringToObjUtil.strToObj(actions.getResponse().getContentAsString(), ResultVO.class);
            assertNotNull(resultVO);
            assertEquals(Integer.valueOf(1000), resultVO.getStatus());
        }

        {
            //查询结果
            MockHttpServletRequestBuilder findTalentCertificationDetail = MockMvcRequestBuilders.post("/editTalent/findTalentCertificationDetail")
                    .param("openId",educationDTO.getOpenId()).contentType(MediaType.APPLICATION_FORM_URLENCODED);//有@RequestParam注解优先对应,否则对应着表单的input标签的name属性的  值及value
            MvcResult actions = mockMvc.perform(findTalentCertificationDetail).
                    andDo(MockMvcResultHandlers.print()).andReturn();
            ResultVO<JSONObject> resultVO = StringToObjUtil.strToObj(actions.getResponse().getContentAsString(), ResultVO.class);
            assertNotNull(resultVO);
            assertEquals(Integer.valueOf(1000), resultVO.getStatus());
            assertNotNull(resultVO.getData());
            JSONObject data = resultVO.getData();
            TalentBO talentBO = StringToObjUtil.strToObj(data.getJSONObject("talentInfo").toJSONString(),TalentBO.class);
            assertNotNull(talentBO);
            EducationPO newEducation = talentBO.getEducationPOList().get(1);
            assertEquals(newEducation.getEducation(),educationDTO.getEducation());
            assertEquals(newEducation.getEducPicture(), educationDTO.getEducPicture());
            assertEquals(newEducation.getFirstClass(), educationDTO.getFirstClass());
            assertEquals(newEducation.getMajor(), educationDTO.getMajor());
            assertEquals(newEducation.getSchool(), educationDTO.getSchool());
            assertEquals(newEducation.getGraduateTime(), educationDTO.getGraduateTime());
        }


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