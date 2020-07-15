package com.talentcard.web.service.impl;

import com.github.pagehelper.Page;
import com.talentcard.common.mapper.TalentMapper;
import com.talentcard.common.mapper.UserFeedbackMapper;
import com.talentcard.common.pojo.TalentPO;
import com.talentcard.common.pojo.UserFeedbackPO;
import com.talentcard.common.vo.PageInfoVO;
import com.talentcard.common.vo.ResultVO;
import com.talentcard.web.service.IUserFeedbackService;
import com.talentcard.web.utils.PageHelper;
import com.talentcard.web.vo.UserFeedbackVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @author: liyuan
 * @data 2020-07-14 19:35
 */
@Service
public class UserFeedbackServiceImpl implements IUserFeedbackService {
    @Autowired
    private UserFeedbackMapper userFeedbackMapper;
    @Autowired
    private TalentMapper talentMapper;
    @Transactional()
    @Override
    public ResultVO query(Integer pageNum,Integer pageSize,Map<String, Object> map) {
        Page<UserFeedbackPO> page = PageHelper.startPage(pageNum, pageSize);
        List<UserFeedbackPO> pos = userFeedbackMapper.query(map);
        return new ResultVO(1000,new PageInfoVO<>(page.getTotal(), convert(pos)));
    }

    public List<UserFeedbackVO> convert(List<UserFeedbackPO> pos) {
        if(pos==null||pos.size()==0){
            return null;
        }
        List<UserFeedbackVO> list=new ArrayList<>(pos.size());
        for(UserFeedbackPO po:pos){
            list.add(convertPO(po));
        }
        return list;
    }

    private UserFeedbackVO convertPO(UserFeedbackPO po) {
        if(po==null){
            return null;
        }
        UserFeedbackVO userFeedbackVO=new UserFeedbackVO();
        userFeedbackVO.setPageType(po.getPageType());
        userFeedbackVO.setPageItem(po.getPageType());
        userFeedbackVO.setRelateItem(po.getRelateItem());
        userFeedbackVO.setProDescribe(po.getProDescribe());
        userFeedbackVO.setSubmitDate(po.getSubmitDate());
        TalentPO talentPO = talentMapper.selectByOpenId(po.getOpenId());
        if(talentPO!=null&&!StringUtils.isEmpty(talentPO.getOpenId())){
            if(talentPO.getOpenId().equals("000000000000000")){
                userFeedbackVO.setName("游客");//游客的话统一显示为“游客“
            }else {
                String name=talentPO.getName();
                name+=talentPO.getCardType();
                if(talentPO.getCardType()==1){ //1身份证2护照3驾照
                    name+=talentPO.getIdCard();
                }else if(talentPO.getCardType()==2){
                    name+=talentPO.getPassport();
                }else if(talentPO.getCardType()==3){
                    name+=talentPO.getDriverCard();
                }
                userFeedbackVO.setName(name);//提交人的姓名+证件类型+证件号,
            }
        }
        return userFeedbackVO;
    }
}
