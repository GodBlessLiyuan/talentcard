package com.talentcard.front.controller;

import com.alibaba.fastjson.JSONObject;
import com.talentcard.common.utils.StringToObjUtil;
import com.talentcard.common.vo.ResultVO;
import org.apache.poi.ss.formula.functions.T;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class BaseTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    protected MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    public ResultVO<T> mockMvcPostUrlContent(String url, String jsonString) throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post(url)//get、post、put、delete
                .contentType(MediaType.APPLICATION_JSON_UTF8)//设置编码格式
                .content(jsonString)
                .accept(MediaType.APPLICATION_JSON) //接收的是json数据
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        ResultVO<T> resultVO = StringToObjUtil.strToObj(mvcResult.getResponse().getContentAsString(), ResultVO.class);
        return resultVO;
    }
    public ResultVO<T> mockMvcPostUrlParams(String url,JSONObject jsonObject) throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(url);
        if(jsonObject!=null){
            for(String key:jsonObject.keySet()){
                requestBuilder.param(key,(String) jsonObject.get(key));
            }
        }
        MvcResult mvcResult = mockMvc.perform(requestBuilder
//                .content().accept()
                ).andDo(MockMvcResultHandlers.print()).andExpect(status().isOk()).andReturn();
        ResultVO<T> resultVO = StringToObjUtil.strToObj(mvcResult.getResponse().getContentAsString(),ResultVO.class);
        return resultVO;
    }

    public ResultVO<T> mockMvcPostUrlParams(String url, Map<String,String> stringParamsMap) throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(url);
        if(stringParamsMap!=null){
            for(Map.Entry<String,String> entry:stringParamsMap.entrySet()){
                requestBuilder.param(entry.getKey(),entry.getValue());
            }
        }
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andDo(MockMvcResultHandlers.print()).andExpect(status().isOk()).andReturn();
        ResultVO<T> resultVO = StringToObjUtil.strToObj(mvcResult.getResponse().getContentAsString(),ResultVO.class);
        return resultVO;
    }
    /**
     * 多文件上传还没有验证
     * */
    public ResultVO mockMvcFilePostUrlParams(String url,Map<String,String> stringParamsMap,MockMultipartFile... mockMultipartFile) throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(url, mockMultipartFile);
        if(stringParamsMap!=null){
            for(Map.Entry<String,String> entry:stringParamsMap.entrySet()){
                requestBuilder.param(entry.getKey(),entry.getValue());
            }
        }
        MvcResult mvcResult = mockMvc.perform(requestBuilder).andDo(MockMvcResultHandlers.print()).andExpect(status().isOk()).andReturn();
        ResultVO resultVO = StringToObjUtil.strToObj(mvcResult.getResponse().getContentAsString(), ResultVO.class);
        return resultVO;
    }
}
