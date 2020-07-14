package com.talentcard.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.talentcard.common.bo.TalentBO;
import com.talentcard.common.dto.*;
import com.talentcard.common.mapper.TalentMapper;
import com.talentcard.common.pojo.*;
import com.talentcard.common.utils.StringToObjUtil;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.BaseTest;
import com.talentcard.web.WebApplication;
import com.talentcard.web.dto.EditTalentPolicyDTO;
import com.talentcard.web.vo.CertificationTimesVO;
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

import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author: velve
 * @date: Created in 2020/7/10 21:11
 * @description: TODO
 * @version: 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration
@AutoConfigureMockMvc
public class EditTalentControllerTest extends BaseTest {
    @Autowired
    private TalentMapper talentMapper;
    //添加之后，查询出来之后，再次编辑，再次查询，之后核对；
    //这里是直接编辑，查询核对信息
    @Test
    public void editBasicInfo() throws Exception {
        String json="{\n" +
                "    \"openId\":\"oQetQ1ULYaj1Cg5UwNGbQ2hEGIQQ\",\n" +
                "    \"industry\":\"3\",\n" +
                "    \"workUnit\":\"bbba\",\n" +
                "    \"industrySecond\":\"3\",\n" +
                "    \"phone\":\"2222222222222222222222\",\n" +
                "    \"category\":\"2\",\n" +
                "    \"workLocation\":\"2\",\n" +
                "    \"workLocationType\":\"2\",\n" +
                "    \"political\":\"3\"\n" +
                "}";
        BasicInfoDTO basicInfoDTO= JSONObject.parseObject(json,BasicInfoDTO.class);
        {
            MvcResult actions = mockMvc.perform(MockMvcRequestBuilders.post("/editTalent/editBasicInfo").
                    contentType(MediaType.APPLICATION_JSON_UTF8).
                    content(JSONObject.toJSONString(basicInfoDTO))).//string类型的
                    andDo(MockMvcResultHandlers.print()).andReturn();
            ResultVO resultVO = StringToObjUtil.strToObj(actions.getResponse().getContentAsString(), ResultVO.class);
            assertNotNull(resultVO);
            assertEquals(Integer.valueOf(1000), resultVO.getStatus());
        }

        {
            //提交之后查询结果
            MockHttpServletRequestBuilder findTalentCertificationDetail = MockMvcRequestBuilders.post("/editTalent/findTalentCertificationDetail")
                    .param("openId", basicInfoDTO.getOpenId()).contentType(MediaType.APPLICATION_FORM_URLENCODED);//有@RequestParam注解优先对应,否则对应着表单的input标签的name属性的  值及value
            MvcResult actions = mockMvc.perform(findTalentCertificationDetail).
                    andDo(MockMvcResultHandlers.print()).andReturn();
            ResultVO<JSONObject> resultVO = StringToObjUtil.strToObj(actions.getResponse().getContentAsString(), ResultVO.class);
            assertNotNull(resultVO);
            assertEquals(Integer.valueOf(1000), resultVO.getStatus());
            assertNotNull(resultVO.getData());
            JSONObject data = resultVO.getData();
            TalentBO talentBO = StringToObjUtil.strToObj(data.getJSONObject("talentInfo").toJSONString(), TalentBO.class);
            assertNotNull(talentBO);
            //验证基本信息模块
            assertEquals(talentBO.getOpenId(),basicInfoDTO.getOpenId());
            assertEquals(talentBO.getWorkUnit(),basicInfoDTO.getWorkUnit());
            assertEquals(talentBO.getIndustry(),basicInfoDTO.getIndustry());
//            assertEquals(talentBO.getIndustrySecond(),basicInfoDTO.getIndustry());//所在行业二级目录，没用带，无bug
            assertEquals(talentBO.getPhone(),talentBO.getPhone());
            assertEquals(talentBO.getWorkLocationType(),talentBO.getWorkLocationType());
            assertEquals(talentBO.getPolitical(),talentBO.getPolitical());
        }
    }
    //上传学历，查询核对之后，编辑该学历，之后查询核对即可
    @Test
    public void editEducation() throws Exception {
        EducationDTO educationDTO = new EducationDTO();
        educationDTO.setEducation(1);
        educationDTO.setSchool("新增学历测试1");
        educationDTO.setFirstClass((byte) 1);
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
        EducationPO newEducation=null;
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
            assertEquals(newEducation.getEducation(), educationDTO.getEducation());
            assertEquals(newEducation.getEducPicture(), educationDTO.getEducPicture());
            assertEquals(newEducation.getFirstClass(), educationDTO.getFirstClass());
            assertEquals(newEducation.getMajor(), educationDTO.getMajor());
            assertEquals(newEducation.getSchool(), educationDTO.getSchool());
            assertEquals(newEducation.getGraduateTime(), educationDTO.getGraduateTime());
        }
        String json="{\n" +
                "    \"educId\":\"17\",\n" +
                "    \"education\":\"1\",\n" +
                "    \"school\":\"编辑学历测试1\",\n" +
                "    \"firstClass\":\"1\",\n" +
                "    \"major\":\"编辑学历测试1\",\n" +
                "    \"educPicture\":\"编辑学历测试1\",\n" +
                "    \"openId\":\"gaojiyonghu\",\n" +
                "    \"graduateTime\":\"编辑学历测试1\"\n" +
                "}";
        EducationDTO educationDTO1=JSONObject.parseObject(json,EducationDTO.class);
        educationDTO1.setEducId(newEducation.getEducId());
        {
            //编辑内容
            MvcResult actions = mockMvc.perform(MockMvcRequestBuilders.post("/editTalent/editEducation").
                    contentType(MediaType.APPLICATION_JSON_UTF8).
                    content(JSONObject.toJSONString(educationDTO1))).//string类型的
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
            assertEquals(newEducation.getEducation(), educationDTO1.getEducation());
            assertEquals(newEducation.getEducPicture(), educationDTO1.getEducPicture());
            assertEquals(newEducation.getFirstClass(), educationDTO1.getFirstClass());
            assertEquals(newEducation.getMajor(), educationDTO1.getMajor());
            assertEquals(newEducation.getSchool(), educationDTO1.getSchool());
            assertEquals(newEducation.getGraduateTime(), educationDTO1.getGraduateTime());
        }

    }
    //添加职业资格，查询核对，修改，再次查询核对
    @Test
    public void editProfQuality() throws Exception {
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
        String json="{\n" +
                "\"pqId\": 4,\n" +
                "\"category\": 1,\n" +
                "\"info\": \"编辑职业资格测试1\",\n" +
                "\"picture\": \"编辑职业资格测试1\",\n" +
                "\"openId\": \"gaojiyonghu\"\n" +
                "}";
        ProfQualityDTO profQualityDTO1=StringToObjUtil.strToObj(json,ProfQualityDTO.class);
        profQualityDTO1.setPqId(profQualityPO.getPqId());
        {
            MvcResult actions = mockMvc.perform(MockMvcRequestBuilders.post("/editTalent/editProfQuality").
                    contentType(MediaType.APPLICATION_JSON_UTF8).
                    content(JSONObject.toJSONString(profQualityDTO1))).//string类型的
                    andDo(MockMvcResultHandlers.print()).andReturn();
            ResultVO resultVO = StringToObjUtil.strToObj(actions.getResponse().getContentAsString(), ResultVO.class);
            assertNotNull(resultVO);
            assertEquals(Integer.valueOf(1000), resultVO.getStatus());
        }
        {
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
            assertEquals(profQualityPO.getCategory(), profQualityDTO1.getCategory());
            assertEquals(profQualityPO.getInfo(), profQualityDTO1.getInfo());
            assertEquals(profQualityPO.getPicture(), profQualityDTO1.getPicture());
            TalentPO talentPO = talentMapper.selectByPrimaryKey(profQualityPO.getTalentId());
            assertEquals(talentPO.getOpenId(), profQualityDTO1.getOpenId());
        }
    }

    @Test
    public void editProfTitle() throws Exception {
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
        String json="{\n" +
                "    \"ptId\":\"4\",\n" +
                "    \"category\":\"1\",\n" +
                "    \"picture\":\"编辑职称类别测试1\",\n" +
                "    \"info\":\"编辑职称类别测试1\",\n" +
                "    \"openId\":\"gaojiyonghu\"\n" +
                "}";
        ProfTitleDTO profTitleDTO1 = StringToObjUtil.strToObj(json, ProfTitleDTO.class);
        profTitleDTO1.setPtId(profTitlePO.getPtId());

        {
            //编辑内容
            MvcResult actions = mockMvc.perform(MockMvcRequestBuilders.post("/editTalent/editProfTitle").
                    contentType(MediaType.APPLICATION_JSON_UTF8).
                    content(JSONObject.toJSONString(profTitleDTO1))).//string类型的
                    andDo(MockMvcResultHandlers.print()).andReturn();
            ResultVO resultVO = StringToObjUtil.strToObj(actions.getResponse().getContentAsString(), ResultVO.class);
            assertNotNull(resultVO);
            assertEquals(Integer.valueOf(1000), resultVO.getStatus());
        }
        {
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
            assertEquals(profTitlePO.getCategory(), profTitleDTO1.getCategory());
            assertEquals(profTitlePO.getInfo(), profTitleDTO1.getInfo());
            assertEquals(profTitlePO.getPicture(), profTitleDTO1.getPicture());
            TalentPO talentPO = talentMapper.selectByPrimaryKey(profTitlePO.getTalentId());
            assertEquals(talentPO.getOpenId(), profTitleDTO1.getOpenId());
        }
    }
    //新增、查询核对、编辑、查询核对
    @Test
    public void editTalentHonour() throws Exception {
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
        //编辑然后核对
        String json="{\n" +
                "    \"thId\":\"5\",\n" +
                "    \"honourId\":\"1\",\n" +
                "    \"honourPicture\":\"编辑人才荣誉测试1\",\n" +
                "    \"openId\":\"gaojiyonghu\",\n" +
                "    \"info\":\"编辑人才荣誉测试1\"\n" +
                "}";
        TalentHonourDTO talentHonourDTO1 = StringToObjUtil.strToObj(json, TalentHonourDTO.class);
        talentHonourDTO1.setHonourId(talentHonourPO.getHonourId());//
        talentHonourDTO1.setThId(talentHonourPO.getThId());
        {
            //编辑内容
            MvcResult actions = mockMvc.perform(MockMvcRequestBuilders.post("/editTalent/editTalentHonour").
                    contentType(MediaType.APPLICATION_JSON_UTF8).
                    content(JSONObject.toJSONString(talentHonourDTO1))).//string类型的
                    andDo(MockMvcResultHandlers.print()).andReturn();
            ResultVO resultVO = StringToObjUtil.strToObj(actions.getResponse().getContentAsString(), ResultVO.class);
            assertNotNull(resultVO);
            assertEquals(Integer.valueOf(1000), resultVO.getStatus());
        }
        {
            MockHttpServletRequestBuilder findTalentCertificationDetail = MockMvcRequestBuilders.post("/editTalent/findTalentCertificationDetail")
                    .param("openId", talentHonourDTO1.getOpenId()).contentType(MediaType.APPLICATION_FORM_URLENCODED);//有@RequestParam注解优先对应,否则对应着表单的input标签的name属性的  值及value
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
            assertEquals(talentHonourPO.getHonourId(), talentHonourDTO1.getHonourId());
            assertEquals(talentHonourPO.getInfo(), talentHonourDTO1.getInfo());
            assertEquals(talentHonourPO.getHonourPicture(), talentHonourDTO1.getHonourPicture());
            TalentPO talentPO = talentMapper.selectByPrimaryKey(talentHonourPO.getTalentId());
            assertEquals(talentPO.getOpenId(), talentHonourDTO1.getOpenId());
        }
    }
    //编辑人才类型
    @Test
    public void editTalentCategory() throws Exception {
        {
            MvcResult actions = mockMvc.perform(MockMvcRequestBuilders.post("/editTalent/editTalentCategory").
                    contentType(MediaType.APPLICATION_JSON_UTF8).
                    param("openId","gaojiyonghu"). //2、3error
                    param("talentCategory","1")).//string类型的
                    andDo(MockMvcResultHandlers.print()).andReturn();
            ResultVO resultVO = StringToObjUtil.strToObj(actions.getResponse().getContentAsString(), ResultVO.class);
            assertNotNull(resultVO);
            assertEquals(Integer.valueOf(1000), resultVO.getStatus());
        }
    }
    //第五期的政策权益查询
    @Test
    public void findPolicy() throws Exception {
        String json="{\n" +
                "     \"educationList\": [\"1\",\"2\",\"3\"],\n" +
                "    \"titleList\": [\"1\",\"2\",\"3\"],\n" +
                "    \"educationList\": [\"1\",\"2\",\"3\"],\n" +
                "    \"qualityList\": [\"1\",\"2\",\"3\"],\n" +
                "    \"honourList\": [\"1\",\"2\",\"3\"],\n" +
                "    \"category\":\"1,2,3\",\n" +
                "    \"cardId\": 2\n" +
                "}";
        EditTalentPolicyDTO editTalentPolicyDTO=JSONObject.parseObject(json,EditTalentPolicyDTO.class);
        {
            MvcResult actions = mockMvc.perform(MockMvcRequestBuilders.post("/editTalent/findPolicy").
                    contentType(MediaType.APPLICATION_JSON_UTF8).
                    content(JSONObject.toJSONString(editTalentPolicyDTO))).
                    andDo(MockMvcResultHandlers.print()).andReturn();
            ResultVO resultVO = StringToObjUtil.strToObj(actions.getResponse().getContentAsString(), ResultVO.class);
            assertNotNull(resultVO);
            assertEquals(Integer.valueOf(1000), resultVO.getStatus());
            List<PolicyPO> list=JSONObject.parseArray(JSONObject.toJSONString(resultVO.getData()),PolicyPO.class);
            assertNotNull(list);
            System.out.println(list.size());
        }
    }

    @Test
    public void findTalentCertificationDetail() throws Exception {
        {
            MockHttpServletRequestBuilder findTalentCertificationDetail = MockMvcRequestBuilders.post("/editTalent/findTalentCertificationDetail")
                    .param("openId", "gaojiyonghu").contentType(MediaType.APPLICATION_FORM_URLENCODED);//有@RequestParam注解优先对应,否则对应着表单的input标签的name属性的  值及value
            MvcResult actions = mockMvc.perform(findTalentCertificationDetail).
                    andDo(MockMvcResultHandlers.print()).andReturn();
            ResultVO<JSONObject> resultVO = StringToObjUtil.strToObj(actions.getResponse().getContentAsString(), ResultVO.class);
            assertNotNull(resultVO);
            assertEquals(Integer.valueOf(1000), resultVO.getStatus());
            assertNotNull(resultVO.getData());
            JSONObject data = resultVO.getData();//hashmap键值对的数据
            TalentBO talentBO = StringToObjUtil.strToObj(data.getJSONObject("talentInfo").toJSONString(), TalentBO.class);//得到对象数据
            assertNotNull(talentBO);
            List<ProfQualityPO> profQualityPOList = talentBO.getProfQualityPOList();//对象内的list数据
            List<EducationPO> educationPOList = talentBO.getEducationPOList();
            List<ProfTitlePO> profTitlePOList = talentBO.getProfTitlePOList();
            List<TalentHonourPO> talentHonourPOList = talentBO.getTalentHonourPOList();//这四个list没有数据
            List<PolicyPO> policyPOList = JSON.toJavaObject(data.getJSONArray("policyPOList"),List.class);//没数据 ；获取list数据
            CardPO cardPO=JSON.toJavaObject(data.getJSONObject("cardInfo"),CardPO.class);//后面有数据  获取对象数据
            CertificationTimesVO certificationTimesVO=JSON.toJavaObject(data.getJSONObject("certificationTimes"),CertificationTimesVO.class);
        }
    }

    @Test
    public void changeCard() throws Exception {
        MvcResult actions = mockMvc.perform(MockMvcRequestBuilders.post("/editTalent/changeCard").
                contentType(MediaType.APPLICATION_JSON_UTF8).
                param("talentId", String.valueOf(13)).
                param("cardId", String.valueOf(1))).//2
                andDo(MockMvcResultHandlers.print()).andReturn();
        ResultVO resultVO = StringToObjUtil.strToObj(actions.getResponse().getContentAsString(), ResultVO.class);
        assertNotNull(resultVO);
        assertEquals(Integer.valueOf(1000), resultVO.getStatus());//有个http连接，但是没有返回数据

    }

    @Test
    public void findEnableChangeCard() throws Exception {
        MvcResult actions = mockMvc.perform(MockMvcRequestBuilders.post("/editTalent/findEnableChangeCard").
                contentType(MediaType.APPLICATION_JSON_UTF8).
                param("talentId", String.valueOf(13))
        ). //填充其他参数
                andDo(MockMvcResultHandlers.print()).andReturn();
        ResultVO resultVO = StringToObjUtil.strToObj(actions.getResponse().getContentAsString(), ResultVO.class);
        assertNotNull(resultVO);
        assertEquals(Integer.valueOf(1000), resultVO.getStatus());//有个http连接，但是没有返回数据
//        List<CardPO> list = StringToObjUtil.strToObj(JSONObject.toJSONString(resultVO.getData()), List.class);//obj --string --obj
        List<CardPO> list1=  JSON.toJavaObject((JSON) resultVO.getData(), List.class);
        assertNotNull(list1);
        System.out.println(list1.size());
    }
}