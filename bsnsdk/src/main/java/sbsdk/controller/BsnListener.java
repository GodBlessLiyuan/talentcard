package sbsdk.controller;

import com.alibaba.fastjson.JSON;
import com.talentcard.common.utils.StringToObjUtil;
import com.talentcard.common.utils.rabbit.chaincodeEntities.Application;
import com.talentcard.common.utils.rabbit.chaincodeEntities.Business;
import com.talentcard.common.utils.rabbit.chaincodeEntities.Profile;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import sbsdk.talentcard.bsnsdk.client.fabric.service.TransactionService;
import sbsdk.talentcard.bsnsdk.entity.req.fabric.ReqKeyEscrow;
import sbsdk.talentcard.bsnsdk.entity.res.fabric.ResKeyEscrow;

import java.io.IOException;
import java.util.Map;

/**
 * @author: velve
 * @date: Created in 2020/10/15 19:41
 * @description: TODO
 * @version: 1.0
 */
public class BsnListener {

    @Autowired
    private TransactionService transactionService;

    /**
     * 追踪队列
     *
     * @param data 数据包
     */
    @RabbitListener(queues = "bsn")
    public void track(Map<String, Object> data) throws IOException {
        if (data != null) {
            String method = (String) data.get("method");
            String data_c = (String) data.get("data");
            if (StringUtils.equals(method, "application")) {
                //查询是否存在，
                ResKeyEscrow resKeyEscrow = transactionService.reqChainCode(this.setMethodName(data_c, "getApplicationInfo"));
                if (resKeyEscrow != null) {
                    String res = resKeyEscrow.getCcRes().getCcData();
                    if (StringUtils.isNotEmpty(res)) {
                        Application old_application = StringToObjUtil.strToObj(res, Application.class);
                        if (old_application != null) {
                            Application new_application = StringToObjUtil.strToObj(data_c, Application.class);
                            if (new_application != null) {
                                if (StringUtils.isNotEmpty(new_application.getStatus())) {
                                    old_application.setStatus(new_application.getStatus());
                                }
                                if (StringUtils.isNotEmpty(new_application.getAp_id())) {
                                    old_application.setAp_id(new_application.getAp_id());
                                }
                                if (StringUtils.isNotEmpty(new_application.getExtra())) {
                                    old_application.setExtra(new_application.getExtra());
                                }
                                this.transactionService.reqChainCode(this.setMethodName(JSON.toJSONString(old_application), "updateApplication"));
                            }
                        } else {
                            this.transactionService.reqChainCode(this.setMethodName(data_c, "apply"));
                        }
                    } else {
                        this.transactionService.reqChainCode(this.setMethodName(data_c, "apply"));
                    }
                }

            } else if (StringUtils.equals(method, "business")) {
                ResKeyEscrow resKeyEscrow = transactionService.reqChainCode(this.setMethodName(data_c, "getBusinessActivityInfoByUID"));
                if (resKeyEscrow != null) {
                    String res = resKeyEscrow.getCcRes().getCcData();
                    if (StringUtils.isNotEmpty(res)) {
                        Business old_business = StringToObjUtil.strToObj(res, Business.class);
                        if (old_business != null) {
                            Business new_business = StringToObjUtil.strToObj(data_c, Business.class);
                            if (new_business != null) {
                                old_business.setBid(new_business.getBid());
                                old_business.setMenu(new_business.getMenu());
                                old_business.setActivity(new_business.getActivity());
                                old_business.setFunds(new_business.getFunds());
                                old_business.setCreate_time(new_business.getCreate_time());
                                old_business.setExtra(new_business.getExtra());
                                this.transactionService.reqChainCode(this.setMethodName(JSON.toJSONString(old_business), "updateBusinessActivity"));
                            }
                        }
                    } else {
                        this.transactionService.reqChainCode(this.setMethodName(data_c, "createBusinessActivity"));
                    }
                } else {
                    this.transactionService.reqChainCode(this.setMethodName(data_c, "createBusinessActivity"));
                }
            } else if (StringUtils.equals(method, "profile")) {
                ResKeyEscrow resKeyEscrow = transactionService.reqChainCode(this.setMethodName(data_c, "getProfileByID"));
                if (resKeyEscrow != null) {
                    String res = resKeyEscrow.getCcRes().getCcData();
                    if (StringUtils.isNotEmpty(res)) {
                        Profile old_profile = StringToObjUtil.strToObj(res, Profile.class);
                        if (old_profile != null) {
                            Profile new_profile = StringToObjUtil.strToObj(data_c, Profile.class);
                            if (new_profile != null) {
                                if(StringUtils.isNotEmpty(new_profile.getName())){
                                    old_profile.setName(new_profile.getName());
                                }
                                if(StringUtils.isNotEmpty(new_profile.getIdentity_card())){
                                    old_profile.setIdentity_card(new_profile.getIdentity_card());
                                }
                                if(StringUtils.isNotEmpty(new_profile.getPassport())){
                                    old_profile.setPassport(new_profile.getPassport());
                                }
                                if(StringUtils.isNotEmpty(new_profile.getOpenid())){
                                    old_profile.setOpenid(new_profile.getOpenid());
                                }
                                if(StringUtils.isNotEmpty(new_profile.getWx_card())){
                                    old_profile.setWx_card(new_profile.getWx_card());
                                }
                                if(StringUtils.isNotEmpty(new_profile.getWx_card_num())){
                                    old_profile.setWx_card_num(new_profile.getWx_card_num());
                                }
                                if(StringUtils.isNotEmpty(new_profile.getAction())){
                                    old_profile.setAction(new_profile.getAction());
                                }
                                if(StringUtils.isNotEmpty(new_profile.getModule())){
                                    old_profile.setModule(new_profile.getModule());
                                }
                                if(StringUtils.isNotEmpty(new_profile.getExtra())){
                                    old_profile.setExtra(new_profile.getExtra());
                                }

                                this.transactionService.reqChainCode(this.setMethodName(JSON.toJSONString(old_profile), "updateProfile"));
                            }
                        }
                    } else {
                        this.transactionService.reqChainCode(this.setMethodName(data_c, "createProfile"));
                    }
                } else {
                    this.transactionService.reqChainCode(this.setMethodName(data_c, "createProfile"));
                }
            }
        }
    }


    private ReqKeyEscrow setMethodName(String arg, String methodName) {
        String[] args = {arg};
        ReqKeyEscrow reqKeyEscrow = new ReqKeyEscrow();
        reqKeyEscrow.setArgs(args);
        //bsn网络上调用智能合约的名称标识
        reqKeyEscrow.setChainCode("cc_app0001202006081111440843077_00");
        //保存到bsn节点上的一个暂时性的键值对数据
        reqKeyEscrow.setTransientData(null);
        reqKeyEscrow.setFuncName(methodName);
        return reqKeyEscrow;
    }
}
