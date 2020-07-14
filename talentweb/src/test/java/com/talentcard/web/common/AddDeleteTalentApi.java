package com.talentcard.web.common;

import com.alibaba.fastjson.JSONObject;
import com.talentcard.common.dto.TalentHonourDTO;
import com.talentcard.common.utils.StringToObjUtil;
import com.talentcard.common.vo.ResultVO;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author: velve
 * @date: Created in 2020/7/14 9:48
 * @description: TODO
 * @version: 1.0
 */
public class AddDeleteTalent {


    public static ResultVO<JSONObject> addTalentHonour(MockMvc mockMvc, TalentHonourDTO talentHonourDTO) throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/addDeleteTalent/addTalentHonour").
                contentType(MediaType.APPLICATION_JSON_UTF8).
                content(JSONObject.toJSONString(talentHonourDTO))).
                andDo(MockMvcResultHandlers.print()).andExpect(status().isOk()).andReturn();
        ResultVO<JSONObject> resultVO = StringToObjUtil.strToObj(mvcResult.getResponse().getContentAsString(), ResultVO.class);
        return resultVO;
    }
}
