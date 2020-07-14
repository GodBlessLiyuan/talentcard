package com.talentcard.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.talentcard.common.bo.TalentBO;
import com.talentcard.common.dto.EducationDTO;
import com.talentcard.common.dto.ProfQualityDTO;
import com.talentcard.common.dto.ProfTitleDTO;
import com.talentcard.common.dto.TalentHonourDTO;
import com.talentcard.common.mapper.TalentMapper;
import com.talentcard.common.pojo.*;
import com.talentcard.common.utils.StringToObjUtil;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.BaseTest;
import com.talentcard.web.WebApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.util.List;

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

    //新增职业资格测试
    @Autowired
    private TalentMapper talentMapper;

    @Test
    public void addProfQuality() throws Exception {
        ProfQualityDTO profQualityDTO = new ProfQualityDTO();
        profQualityDTO.setCategory(1);
        profQualityDTO.setInfo("新增职业资格测试1");
        profQualityDTO.setPicture("新增职业资格测试1");
        profQualityDTO.setOpenId("gaojiyonghu");//根据数据库的数据，必须是这个号
        {
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/addDeleteTalent/addProfQuality").
                    contentType(MediaType.APPLICATION_JSON_UTF8).
                    content(JSONObject.toJSONString(profQualityDTO))).
                    andDo(MockMvcResultHandlers.print()).andExpect(status().isOk()).andReturn();
            ResultVO<JSONObject> resultVO = StringToObjUtil.strToObj(mvcResult.getResponse().getContentAsString(), ResultVO.class);
            assertNotNull(resultVO);
            assertEquals(Integer.valueOf(1000), resultVO.getStatus());
        }
        //重复提交
        {
            MvcResult actions = mockMvc.perform(MockMvcRequestBuilders.post("/addDeleteTalent/addProfQuality").
                    contentType(MediaType.APPLICATION_JSON_UTF8).
                    content(JSONObject.toJSONString(profQualityDTO))).
                    andDo(MockMvcResultHandlers.print()).andReturn();
            ResultVO resultVO = StringToObjUtil.strToObj(actions.getResponse().getContentAsString(), ResultVO.class);
            assertNotNull(resultVO);
            assertEquals(Integer.valueOf(2558), resultVO.getStatus());
        }
        {
            //提交之后查询结果
            MockHttpServletRequestBuilder findTalentCertificationDetail = MockMvcRequestBuilders.post("/editTalent/findTalentCertificationDetail")
                    .param("openId", profQualityDTO.getOpenId()).contentType(MediaType.APPLICATION_FORM_URLENCODED);//有@RequestParam注解优先对应,否则对应着表单的input标签的name属性的  值及value
            MvcResult actions = mockMvc.perform(findTalentCertificationDetail).
                    andDo(MockMvcResultHandlers.print()).andReturn();
            ResultVO<JSONObject> resultVO = StringToObjUtil.strToObj(actions.getResponse().getContentAsString(), ResultVO.class);
            assertNotNull(resultVO);
            assertEquals(Integer.valueOf(1000), resultVO.getStatus());
            assertNotNull(resultVO.getData());
            JSONObject data = resultVO.getData();
            TalentBO talentBO = StringToObjUtil.strToObj(data.getJSONObject("talentInfo").toJSONString(), TalentBO.class);
            assertNotNull(talentBO);
            List<ProfQualityPO> profQualityPOList = talentBO.getProfQualityPOList();
            ProfQualityPO profQualityPO = profQualityPOList.get(0);
            assertNotNull(profQualityPO);
            assertEquals(profQualityPO.getCategory(), profQualityDTO.getCategory());
            assertEquals(profQualityPO.getInfo(), profQualityDTO.getInfo());
            assertEquals(profQualityPO.getPicture(), profQualityDTO.getPicture());
            TalentPO talentPO = talentMapper.selectByPrimaryKey(profQualityPO.getTalentId());
            assertEquals(talentPO.getOpenId(), profQualityDTO.getOpenId());
        }
        {
            profQualityDTO.setCategory(2);//添加第二个
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/addDeleteTalent/addProfQuality").
                    contentType(MediaType.APPLICATION_JSON_UTF8).
                    content(JSONObject.toJSONString(profQualityDTO))).
                    andDo(MockMvcResultHandlers.print()).andExpect(status().isOk()).andReturn();
            ResultVO<JSONObject> resultVO = StringToObjUtil.strToObj(mvcResult.getResponse().getContentAsString(), ResultVO.class);
            assertNotNull(resultVO);
            assertEquals(Integer.valueOf(1000), resultVO.getStatus());
        }
        {
            //添加之后再次查询
            MockHttpServletRequestBuilder findTalentCertificationDetail = MockMvcRequestBuilders.post("/editTalent/findTalentCertificationDetail")
                    .param("openId", profQualityDTO.getOpenId()).contentType(MediaType.APPLICATION_FORM_URLENCODED);//有@RequestParam注解优先对应,否则对应着表单的input标签的name属性的  值及value
            MvcResult actions = mockMvc.perform(findTalentCertificationDetail).
                    andDo(MockMvcResultHandlers.print()).andReturn();
            ResultVO<JSONObject> resultVO = StringToObjUtil.strToObj(actions.getResponse().getContentAsString(), ResultVO.class);
            assertNotNull(resultVO);
            assertEquals(Integer.valueOf(1000), resultVO.getStatus());
            assertNotNull(resultVO.getData());
            JSONObject data = resultVO.getData();
            TalentBO talentBO = StringToObjUtil.strToObj(data.getJSONObject("talentInfo").toJSONString(), TalentBO.class);
            assertNotNull(talentBO);
            List<ProfQualityPO> profQualityPOList = talentBO.getProfQualityPOList();
            ProfQualityPO profQualityPO = profQualityPOList.get(1);
            assertNotNull(profQualityPO);
            assertEquals(profQualityPO.getCategory(), profQualityDTO.getCategory());
            assertEquals(profQualityPO.getInfo(), profQualityDTO.getInfo());
            assertEquals(profQualityPO.getPicture(), profQualityDTO.getPicture());
            TalentPO talentPO = talentMapper.selectByPrimaryKey(profQualityPO.getTalentId());
            assertEquals(talentPO.getOpenId(), profQualityDTO.getOpenId());
        }
    }
    //职业类别测试
    @Test
    public void addProfTitle() throws Exception {
        ProfTitleDTO profTitleDTO=new ProfTitleDTO();
        profTitleDTO.setCategory(1);
        profTitleDTO.setPicture("新增职称类别测试1");
        profTitleDTO.setInfo("新增职称类别测试1");
        profTitleDTO.setOpenId("gaojiyonghu");
        {
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/addDeleteTalent/addProfTitle").
                    contentType(MediaType.APPLICATION_JSON_UTF8).
                    content(JSONObject.toJSONString(profTitleDTO))).
                    andDo(MockMvcResultHandlers.print()).andExpect(status().isOk()).andReturn();
            ResultVO<JSONObject> resultVO = StringToObjUtil.strToObj(mvcResult.getResponse().getContentAsString(), ResultVO.class);
            assertNotNull(resultVO);
            assertEquals(Integer.valueOf(1000), resultVO.getStatus());
        }
        //重复提交
        {
            MvcResult actions = mockMvc.perform(MockMvcRequestBuilders.post("/addDeleteTalent/addProfTitle").
                    contentType(MediaType.APPLICATION_JSON_UTF8).
                    content(JSONObject.toJSONString(profTitleDTO))).
                    andDo(MockMvcResultHandlers.print()).andReturn();
            ResultVO resultVO = StringToObjUtil.strToObj(actions.getResponse().getContentAsString(), ResultVO.class);
            assertNotNull(resultVO);
            assertEquals(Integer.valueOf(2558), resultVO.getStatus());
        }
        {
            //提交之后查询结果
            MockHttpServletRequestBuilder findTalentCertificationDetail = MockMvcRequestBuilders.post("/editTalent/findTalentCertificationDetail")
                    .param("openId", profTitleDTO.getOpenId()).contentType(MediaType.APPLICATION_FORM_URLENCODED);//有@RequestParam注解优先对应,否则对应着表单的input标签的name属性的  值及value
            MvcResult actions = mockMvc.perform(findTalentCertificationDetail).
                    andDo(MockMvcResultHandlers.print()).andReturn();
            ResultVO<JSONObject> resultVO = StringToObjUtil.strToObj(actions.getResponse().getContentAsString(), ResultVO.class);
            assertNotNull(resultVO);
            assertEquals(Integer.valueOf(1000), resultVO.getStatus());
            assertNotNull(resultVO.getData());
            JSONObject data = resultVO.getData();
            TalentBO talentBO = StringToObjUtil.strToObj(data.getJSONObject("talentInfo").toJSONString(), TalentBO.class);
            assertNotNull(talentBO);
            List<ProfTitlePO> profTitlePOList = talentBO.getProfTitlePOList();
            ProfTitlePO profTitlePO = profTitlePOList.get(0);
            assertNotNull(profTitlePO);
            assertEquals(profTitlePO.getCategory(), profTitleDTO.getCategory());
            assertEquals(profTitlePO.getInfo(), profTitleDTO.getInfo());
            assertEquals(profTitlePO.getPicture(), profTitleDTO.getPicture());
            TalentPO talentPO = talentMapper.selectByPrimaryKey(profTitlePO.getTalentId());
            assertEquals(talentPO.getOpenId(), profTitleDTO.getOpenId());
        }
        {
            profTitleDTO.setCategory(2);//添加第二个
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/addDeleteTalent/addProfTitle").
                    contentType(MediaType.APPLICATION_JSON_UTF8).
                    content(JSONObject.toJSONString(profTitleDTO))).
                    andDo(MockMvcResultHandlers.print()).andExpect(status().isOk()).andReturn();
            ResultVO<JSONObject> resultVO = StringToObjUtil.strToObj(mvcResult.getResponse().getContentAsString(), ResultVO.class);
            assertNotNull(resultVO);
            assertEquals(Integer.valueOf(1000), resultVO.getStatus());
        }
        {
            //添加之后再次查询
            MockHttpServletRequestBuilder findTalentCertificationDetail = MockMvcRequestBuilders.post("/editTalent/findTalentCertificationDetail")
                    .param("openId", profTitleDTO.getOpenId()).contentType(MediaType.APPLICATION_FORM_URLENCODED);//有@RequestParam注解优先对应,否则对应着表单的input标签的name属性的  值及value
            MvcResult actions = mockMvc.perform(findTalentCertificationDetail).
                    andDo(MockMvcResultHandlers.print()).andReturn();
            ResultVO<JSONObject> resultVO = StringToObjUtil.strToObj(actions.getResponse().getContentAsString(), ResultVO.class);
            assertNotNull(resultVO);
            assertEquals(Integer.valueOf(1000), resultVO.getStatus());
            assertNotNull(resultVO.getData());
            JSONObject data = resultVO.getData();
            TalentBO talentBO = StringToObjUtil.strToObj(data.getJSONObject("talentInfo").toJSONString(), TalentBO.class);
            assertNotNull(talentBO);
            List<ProfTitlePO> profTitlePOList = talentBO.getProfTitlePOList();
            ProfTitlePO profTitlePO = profTitlePOList.get(1);
            assertNotNull(profTitlePO);
            assertEquals(profTitlePO.getCategory(), profTitleDTO.getCategory());
            assertEquals(profTitlePO.getInfo(), profTitleDTO.getInfo());
            assertEquals(profTitlePO.getPicture(), profTitleDTO.getPicture());
            TalentPO talentPO = talentMapper.selectByPrimaryKey(profTitlePO.getTalentId());
            assertEquals(talentPO.getOpenId(), profTitleDTO.getOpenId());
        }
    }

    @Test
    public void addTalentHonour() throws Exception {
        TalentHonourDTO talentHonourDTO=new TalentHonourDTO();
        talentHonourDTO.setHonourId(1L);
        talentHonourDTO.setHonourPicture("编辑人才荣誉测试1");
        talentHonourDTO.setOpenId("gaojiyonghu");
        talentHonourDTO.setInfo("编辑人才荣誉测试1");
        //提交，重复提交，查询第一条记录；提交第二条记录，查询第二条记录
        {
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/addDeleteTalent/addTalentHonour").
                    contentType(MediaType.APPLICATION_JSON_UTF8).
                    content(JSONObject.toJSONString(talentHonourDTO))).
                    andDo(MockMvcResultHandlers.print()).andExpect(status().isOk()).andReturn();
            ResultVO<JSONObject> resultVO = StringToObjUtil.strToObj(mvcResult.getResponse().getContentAsString(), ResultVO.class);
            assertNotNull(resultVO);
            assertEquals(Integer.valueOf(1000), resultVO.getStatus());
        }
        //重复提交
        {
            MvcResult actions = mockMvc.perform(MockMvcRequestBuilders.post("/addDeleteTalent/addTalentHonour").
                    contentType(MediaType.APPLICATION_JSON_UTF8).
                    content(JSONObject.toJSONString(talentHonourDTO))).
                    andDo(MockMvcResultHandlers.print()).andReturn();
            ResultVO resultVO = StringToObjUtil.strToObj(actions.getResponse().getContentAsString(), ResultVO.class);
            assertNotNull(resultVO);
            assertEquals(Integer.valueOf(2558), resultVO.getStatus());
        }
        {
            //提交之后查询结果
            MockHttpServletRequestBuilder findTalentCertificationDetail = MockMvcRequestBuilders.post("/editTalent/findTalentCertificationDetail")
                    .param("openId", talentHonourDTO.getOpenId()).contentType(MediaType.APPLICATION_FORM_URLENCODED);//有@RequestParam注解优先对应,否则对应着表单的input标签的name属性的  值及value
            MvcResult actions = mockMvc.perform(findTalentCertificationDetail).
                    andDo(MockMvcResultHandlers.print()).andReturn();
            ResultVO<JSONObject> resultVO = StringToObjUtil.strToObj(actions.getResponse().getContentAsString(), ResultVO.class);
            assertNotNull(resultVO);
            assertEquals(Integer.valueOf(1000), resultVO.getStatus());
            assertNotNull(resultVO.getData());
            JSONObject data = resultVO.getData();
            TalentBO talentBO = StringToObjUtil.strToObj(data.getJSONObject("talentInfo").toJSONString(), TalentBO.class);
            assertNotNull(talentBO);
            List<TalentHonourPO> talentHonourPOList = talentBO.getTalentHonourPOList();
            TalentHonourPO talentHonourPO = talentHonourPOList.get(0);
            assertNotNull(talentHonourPO);
            assertEquals(talentHonourPO.getHonourId(), talentHonourDTO.getHonourId());
            assertEquals(talentHonourPO.getInfo(), talentHonourDTO.getInfo());
            assertEquals(talentHonourPO.getHonourPicture(), talentHonourDTO.getHonourPicture());
            TalentPO talentPO = talentMapper.selectByPrimaryKey(talentHonourPO.getTalentId());
            assertEquals(talentPO.getOpenId(), talentHonourDTO.getOpenId());
        }
        {
            talentHonourDTO.setHonourId(2L);//添加第二个
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/addDeleteTalent/addTalentHonour").
                    contentType(MediaType.APPLICATION_JSON_UTF8).
                    content(JSONObject.toJSONString(talentHonourDTO))).
                    andDo(MockMvcResultHandlers.print()).andExpect(status().isOk()).andReturn();
            ResultVO<JSONObject> resultVO = StringToObjUtil.strToObj(mvcResult.getResponse().getContentAsString(), ResultVO.class);
            assertNotNull(resultVO);
            assertEquals(Integer.valueOf(1000), resultVO.getStatus());
        }
        {
            //添加之后再次查询
            MockHttpServletRequestBuilder findTalentCertificationDetail = MockMvcRequestBuilders.post("/editTalent/findTalentCertificationDetail")
                    .param("openId", talentHonourDTO.getOpenId()).contentType(MediaType.APPLICATION_FORM_URLENCODED);//有@RequestParam注解优先对应,否则对应着表单的input标签的name属性的  值及value
            MvcResult actions = mockMvc.perform(findTalentCertificationDetail).
                    andDo(MockMvcResultHandlers.print()).andReturn();
            ResultVO<JSONObject> resultVO = StringToObjUtil.strToObj(actions.getResponse().getContentAsString(), ResultVO.class);
            assertNotNull(resultVO);
            assertEquals(Integer.valueOf(1000), resultVO.getStatus());
            assertNotNull(resultVO.getData());
            JSONObject data = resultVO.getData();
            TalentBO talentBO = StringToObjUtil.strToObj(data.getJSONObject("talentInfo").toJSONString(), TalentBO.class);
            assertNotNull(talentBO);
            List<TalentHonourPO> talentHonourPOList = talentBO.getTalentHonourPOList();
            TalentHonourPO talentHonourPO = talentHonourPOList.get(1);
            assertNotNull(talentHonourPO);
            assertEquals(talentHonourPO.getHonourId(), talentHonourDTO.getHonourId());
            assertEquals(talentHonourPO.getInfo(), talentHonourDTO.getInfo());
            assertEquals(talentHonourPO.getHonourPicture(), talentHonourDTO.getHonourPicture());
            TalentPO talentPO = talentMapper.selectByPrimaryKey(talentHonourPO.getTalentId());
            assertEquals(talentPO.getOpenId(), talentHonourDTO.getOpenId());
        }
    }

    @Test
    public void deleteEducation() throws Exception {
        //添加教育（查询之后）删除即可
        EducationDTO educationDTO = new EducationDTO();
        educationDTO.setEducation(1);
        educationDTO.setSchool("新增学历测试1");
        educationDTO.setFirstClass((byte) 1);
        educationDTO.setMajor("新增学历测试1");
        educationDTO.setEducPicture("picture");
        educationDTO.setOpenId("gaojiyonghu");
        educationDTO.setGraduateTime("新增学历测试1");
        EducationPO newEducation =null;
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
            //查询结果
            MockHttpServletRequestBuilder findTalentCertificationDetail = MockMvcRequestBuilders.post("/editTalent/findTalentCertificationDetail")
                    .param("openId", educationDTO.getOpenId()).contentType(MediaType.APPLICATION_FORM_URLENCODED);//有@RequestParam注解优先对应,否则对应着表单的input标签的name属性的  值及value
            MvcResult actions = mockMvc.perform(findTalentCertificationDetail).
                    andDo(MockMvcResultHandlers.print()).andReturn();
            ResultVO<JSONObject> resultVO = StringToObjUtil.strToObj(actions.getResponse().getContentAsString(), ResultVO.class);
            assertNotNull(resultVO);
            assertEquals(Integer.valueOf(1000), resultVO.getStatus());
            assertNotNull(resultVO.getData());
            JSONObject data = resultVO.getData();
            TalentBO talentBO = StringToObjUtil.strToObj(data.getJSONObject("talentInfo").toJSONString(), TalentBO.class);
            assertNotNull(talentBO);
            newEducation = talentBO.getEducationPOList().get(0);
            assertNotNull(newEducation);
            assertEquals(newEducation.getEducation(), educationDTO.getEducation());
            assertEquals(newEducation.getEducPicture(), educationDTO.getEducPicture());
            assertEquals(newEducation.getFirstClass(), educationDTO.getFirstClass());
            assertEquals(newEducation.getMajor(), educationDTO.getMajor());
            assertEquals(newEducation.getSchool(), educationDTO.getSchool());
            assertEquals(newEducation.getGraduateTime(), educationDTO.getGraduateTime());
        }
        {
            MvcResult actions = mockMvc.perform(MockMvcRequestBuilders.post("/addDeleteTalent/deleteEducation").
                    contentType(MediaType.APPLICATION_JSON_UTF8).
                    param("openId","gaojiyonghu").
                    param("educId",newEducation.getEducId()+"")).//string类型的
                    andDo(MockMvcResultHandlers.print()).andReturn();
            ResultVO resultVO = StringToObjUtil.strToObj(actions.getResponse().getContentAsString(), ResultVO.class);
            assertNotNull(resultVO);
            assertEquals(Integer.valueOf(1000), resultVO.getStatus());
        }
    }

    @Test
    public void deleteProfQuality() throws Exception {
        ProfQualityDTO profQualityDTO = new ProfQualityDTO();
        profQualityDTO.setCategory(1);
        profQualityDTO.setInfo("新增职业资格测试1");
        profQualityDTO.setPicture("新增职业资格测试1");
        profQualityDTO.setOpenId("gaojiyonghu");//根据数据库的数据，必须是这个号
        {
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/addDeleteTalent/addProfQuality").
                    contentType(MediaType.APPLICATION_JSON_UTF8).
                    content(JSONObject.toJSONString(profQualityDTO))).
                    andDo(MockMvcResultHandlers.print()).andExpect(status().isOk()).andReturn();
            ResultVO<JSONObject> resultVO = StringToObjUtil.strToObj(mvcResult.getResponse().getContentAsString(), ResultVO.class);
            assertNotNull(resultVO);
            assertEquals(Integer.valueOf(1000), resultVO.getStatus());
        }
        ProfQualityPO profQualityPO=null;
        {
            //提交之后查询结果
            MockHttpServletRequestBuilder findTalentCertificationDetail = MockMvcRequestBuilders.post("/editTalent/findTalentCertificationDetail")
                    .param("openId", profQualityDTO.getOpenId()).contentType(MediaType.APPLICATION_FORM_URLENCODED);//有@RequestParam注解优先对应,否则对应着表单的input标签的name属性的  值及value
            MvcResult actions = mockMvc.perform(findTalentCertificationDetail).
                    andDo(MockMvcResultHandlers.print()).andReturn();
            ResultVO<JSONObject> resultVO = StringToObjUtil.strToObj(actions.getResponse().getContentAsString(), ResultVO.class);
            assertNotNull(resultVO);
            assertEquals(Integer.valueOf(1000), resultVO.getStatus());
            assertNotNull(resultVO.getData());
            JSONObject data = resultVO.getData();
            TalentBO talentBO = StringToObjUtil.strToObj(data.getJSONObject("talentInfo").toJSONString(), TalentBO.class);
            assertNotNull(talentBO);
            List<ProfQualityPO> profQualityPOList = talentBO.getProfQualityPOList();
            profQualityPO = profQualityPOList.get(0);
            assertNotNull(profQualityPO);
            assertEquals(profQualityPO.getCategory(), profQualityDTO.getCategory());
            assertEquals(profQualityPO.getInfo(), profQualityDTO.getInfo());
            assertEquals(profQualityPO.getPicture(), profQualityDTO.getPicture());
            TalentPO talentPO = talentMapper.selectByPrimaryKey(profQualityPO.getTalentId());
            assertEquals(talentPO.getOpenId(), profQualityDTO.getOpenId());
        }
        {
            MvcResult actions = mockMvc.perform(MockMvcRequestBuilders.post("/addDeleteTalent/deleteProfQuality").
                    contentType(MediaType.APPLICATION_JSON_UTF8).
                    param("openId","gaojiyonghu").
                    param("pqId",profQualityPO.getPqId()+"")).//string类型的
                    andDo(MockMvcResultHandlers.print()).andReturn();
            ResultVO resultVO = StringToObjUtil.strToObj(actions.getResponse().getContentAsString(), ResultVO.class);
            assertNotNull(resultVO);
            assertEquals(Integer.valueOf(1000), resultVO.getStatus());
        }
    }

    @Test
    public void deleteProfTitle() throws Exception {
        ProfTitleDTO profTitleDTO=new ProfTitleDTO();
        profTitleDTO.setCategory(1);
        profTitleDTO.setPicture("新增职称类别测试1");
        profTitleDTO.setInfo("新增职称类别测试1");
        profTitleDTO.setOpenId("gaojiyonghu");
        {
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/addDeleteTalent/addProfTitle").
                    contentType(MediaType.APPLICATION_JSON_UTF8).
                    content(JSONObject.toJSONString(profTitleDTO))).
                    andDo(MockMvcResultHandlers.print()).andExpect(status().isOk()).andReturn();
            ResultVO<JSONObject> resultVO = StringToObjUtil.strToObj(mvcResult.getResponse().getContentAsString(), ResultVO.class);
            assertNotNull(resultVO);
            assertEquals(Integer.valueOf(1000), resultVO.getStatus());
        }
        ProfTitlePO profTitlePO=null;
        {
            //提交之后查询结果
            MockHttpServletRequestBuilder findTalentCertificationDetail = MockMvcRequestBuilders.post("/editTalent/findTalentCertificationDetail")
                    .param("openId", profTitleDTO.getOpenId()).contentType(MediaType.APPLICATION_FORM_URLENCODED);//有@RequestParam注解优先对应,否则对应着表单的input标签的name属性的  值及value
            MvcResult actions = mockMvc.perform(findTalentCertificationDetail).
                    andDo(MockMvcResultHandlers.print()).andReturn();
            ResultVO<JSONObject> resultVO = StringToObjUtil.strToObj(actions.getResponse().getContentAsString(), ResultVO.class);
            assertNotNull(resultVO);
            assertEquals(Integer.valueOf(1000), resultVO.getStatus());
            assertNotNull(resultVO.getData());
            JSONObject data = resultVO.getData();
            TalentBO talentBO = StringToObjUtil.strToObj(data.getJSONObject("talentInfo").toJSONString(), TalentBO.class);
            assertNotNull(talentBO);
            List<ProfTitlePO> profTitlePOList = talentBO.getProfTitlePOList();
            profTitlePO = profTitlePOList.get(0);
            assertNotNull(profTitlePO);
            assertEquals(profTitlePO.getCategory(), profTitleDTO.getCategory());
            assertEquals(profTitlePO.getInfo(), profTitleDTO.getInfo());
            assertEquals(profTitlePO.getPicture(), profTitleDTO.getPicture());
            TalentPO talentPO = talentMapper.selectByPrimaryKey(profTitlePO.getTalentId());
            assertEquals(talentPO.getOpenId(), profTitleDTO.getOpenId());
        }
        {
            MvcResult actions = mockMvc.perform(MockMvcRequestBuilders.post("/addDeleteTalent/deleteProfTitle").
                    contentType(MediaType.APPLICATION_JSON_UTF8).
                    param("openId","gaojiyonghu").
                    param("ptId",profTitlePO.getPtId()+"")).//string类型的
                    andDo(MockMvcResultHandlers.print()).andReturn();
            ResultVO resultVO = StringToObjUtil.strToObj(actions.getResponse().getContentAsString(), ResultVO.class);
            assertNotNull(resultVO);
            assertEquals(Integer.valueOf(1000), resultVO.getStatus());
        }
    }

    @Test
    public void deleteTalentHonour() throws Exception {
        TalentHonourDTO talentHonourDTO=new TalentHonourDTO();
        talentHonourDTO.setHonourId(1L);
        talentHonourDTO.setHonourPicture("编辑人才荣誉测试1");
        talentHonourDTO.setOpenId("gaojiyonghu");
        talentHonourDTO.setInfo("编辑人才荣誉测试1");
        {
            MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/addDeleteTalent/addTalentHonour").
                    contentType(MediaType.APPLICATION_JSON_UTF8).
                    content(JSONObject.toJSONString(talentHonourDTO))).
                    andDo(MockMvcResultHandlers.print()).andExpect(status().isOk()).andReturn();
            ResultVO<JSONObject> resultVO = StringToObjUtil.strToObj(mvcResult.getResponse().getContentAsString(), ResultVO.class);
            assertNotNull(resultVO);
            assertEquals(Integer.valueOf(1000), resultVO.getStatus());
        }
        TalentHonourPO talentHonourPO=null;
        {
            //提交之后查询结果
            MockHttpServletRequestBuilder findTalentCertificationDetail = MockMvcRequestBuilders.post("/editTalent/findTalentCertificationDetail")
                    .param("openId", talentHonourDTO.getOpenId()).contentType(MediaType.APPLICATION_FORM_URLENCODED);//有@RequestParam注解优先对应,否则对应着表单的input标签的name属性的  值及value
            MvcResult actions = mockMvc.perform(findTalentCertificationDetail).
                    andDo(MockMvcResultHandlers.print()).andReturn();
            ResultVO<JSONObject> resultVO = StringToObjUtil.strToObj(actions.getResponse().getContentAsString(), ResultVO.class);
            assertNotNull(resultVO);
            assertEquals(Integer.valueOf(1000), resultVO.getStatus());
            assertNotNull(resultVO.getData());
            JSONObject data = resultVO.getData();
            TalentBO talentBO = StringToObjUtil.strToObj(data.getJSONObject("talentInfo").toJSONString(), TalentBO.class);
            assertNotNull(talentBO);
            List<TalentHonourPO> talentHonourPOList = talentBO.getTalentHonourPOList();
            talentHonourPO = talentHonourPOList.get(0);
            assertNotNull(talentHonourPO);
            assertEquals(talentHonourPO.getHonourId(), talentHonourDTO.getHonourId());
            assertEquals(talentHonourPO.getInfo(), talentHonourDTO.getInfo());
            assertEquals(talentHonourPO.getHonourPicture(), talentHonourDTO.getHonourPicture());
            TalentPO talentPO = talentMapper.selectByPrimaryKey(talentHonourPO.getTalentId());
            assertEquals(talentPO.getOpenId(), talentHonourDTO.getOpenId());
        }
        {
            MvcResult actions = mockMvc.perform(MockMvcRequestBuilders.post("/addDeleteTalent/deleteTalentHonour").
                    contentType(MediaType.APPLICATION_JSON_UTF8).
                    param("openId","gaojiyonghu").
                    param("thId",talentHonourPO.getThId()+"")).//string类型的
                    andDo(MockMvcResultHandlers.print()).andReturn();
            ResultVO resultVO = StringToObjUtil.strToObj(actions.getResponse().getContentAsString(), ResultVO.class);
            assertNotNull(resultVO);
            assertEquals(Integer.valueOf(1000), resultVO.getStatus());
        }
    }
}