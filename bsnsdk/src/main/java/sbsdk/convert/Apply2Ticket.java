package sbsdk.convert;

import com.alibaba.fastjson.JSON;
import com.bsnsapi.fabric.chaincodeEntities.Application;
import com.bsnsapi.fabric.chaincodeEntities.Ticket;
import sbsdk.talentcard.bsnsdk.entity.req.fabric.ReqKeyEscrow;

/**
 * @Description:
 * @author: liyuan
 * @data 2020-09-08 17:44
 */
public class Apply2Ticket {

    public static ReqKeyEscrow convert(Application application) {
        String[] args = {JSON.toJSONString(application)};
        ReqKeyEscrow reqkey = new ReqKeyEscrow();
        reqkey.setArgs(args);
        //bsn网络上调用智能合约的名称标识
        reqkey.setChainCode("cc_app0001202006081111440843077_00");
        //bsn网络用户注册时的用户名
//        reqkey.setUserName("test21");
        //保存到bsn节点上的一个暂时性的键值对数据
        reqkey.setTransientData(null);
        return reqkey;
    }
}
