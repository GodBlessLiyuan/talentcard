package sbsdk.talentcard.bsnsdk.client.fabric.test;

import com.alibaba.fastjson.JSON;
import sbsdk.talentcard.bsnsdk.client.fabric.service.TransactionService;
import sbsdk.talentcard.bsnsdk.entity.config.Config;
import sbsdk.talentcard.bsnsdk.entity.req.fabric.ReqKeyEscrow;
import sbsdk.talentcard.bsnsdk.util.Log;
import sbsdk.talentcard.bsnsdk.util.exception.GlobalException;
import sbsdk.chaincodeEntities.Ticket;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class TransactionServiceTest {


    public void initConfig() throws IOException {
        Config config = new Config();
        config.setAppCode("app0001202006081111440843077");
        config.setUserCode("USER0001202006050930126612022");//每个usecode都有自己的证书和私匙
        config.setApi("https://nanjinode.bsngate.com:17602");//bsn节点通信的地址，这里是南京
        config.setCert("certs/bsn_gateway_https.crt");//https通讯时的https证书
        //公钥加密，私匙解密
        config.setPrk("certs/nanji/userAppCert/private_key.pem");//用户的私匙
        config.setPuk("certs/nanji/gatewayCert/gateway_public_cert_secp256r1.pem");//bsn网关的公匙
        config.setMspDir("D:/test");
        config.initConfig(config);
    }



    @Test
    public void testEntitiesToJson(){
        Ticket ticket = new Ticket();
        ticket.setType("test");
        ticket.setUid("0001");
        ticket.setName("testticket");
        ticket.setDescription("only for test");
        ticket.setStatus("ok");
        String ret = JSON.toJSONString(ticket);
        Log.d("testJson:"+ ret);
    }
    //2:智能合约
    @Test
    public void reqCreateTicketChainCode() {
        try {
            initConfig();
        } catch (IOException e) {
            e.printStackTrace();
            return ;
        }
        ReqKeyEscrow reqkey = new ReqKeyEscrow();
//        String[] args = {"test"};
        Ticket ticket = new Ticket();
        ticket.setType("test");
        ticket.setUid("0001");
        ticket.setName("testticket");
        ticket.setDescription("only for test");
        ticket.setStatus("ok");
        //{}
        String[] args = {JSON.toJSONString(ticket)};
        System.out.println(args);
        //智能合约的参数:json形式
        reqkey.setArgs(args);
        /***
         * //要调用的智能合约的方法名称
         * */
        reqkey.setFuncName("createTicket");
        reqkey.setChainCode("cc_app0001202006081111440843077_00");//bsn网络上调用智能合约的名称标识
//        reqkey.setUserName("test21");//bsn网络用户注册时的用户名
        reqkey.setTransientData(null);//保存到bsn节点上的一个暂时性的键值对数据
        /***
         * 好像少了nonce：随机字符串，使用base64编码的24位随机byte数据，
         * */
        try {
            /***
             * 传入TransactionService类的reqChaincode方法中作为参数，进行正式调用。
             * */
            TransactionService.reqChainCode(reqkey);
        } catch(GlobalException g) {
            g.printStackTrace();
            return ;
        } catch (IOException e) {
            e.printStackTrace();
            return ;
        }
    }

    @Test
    public void reqGetTicketChainCode() {
        try {
            initConfig();
        } catch (IOException e) {
            e.printStackTrace();
            return ;
        }
        ReqKeyEscrow reqkey = new ReqKeyEscrow();
//        String[] args = {"test"};
        Ticket ticket = new Ticket();
        ticket.setUid("0001");
        String[] args = {JSON.toJSONString(ticket)};
        reqkey.setArgs(args);
        reqkey.setFuncName("getTicketInfo");
        reqkey.setChainCode("cc_app0001202006081111440843077_00");
//        reqkey.setUserName("test21");
        reqkey.setTransientData(null);
        try {
            TransactionService.reqChainCode(reqkey);
        } catch(GlobalException g) {
            g.printStackTrace();
            return ;
        } catch (IOException e) {
            e.printStackTrace();
            return ;
        }
    }

    @Test
    public void reqProfileChainCode() {
        try {
            initConfig();
        } catch (IOException e) {
            e.printStackTrace();
            return ;
        }
        ReqKeyEscrow reqkey = new ReqKeyEscrow();
//        String[] args = {"test"};
        Ticket ticket = new Ticket();
        ticket.setType("test");
        ticket.setUid("0001");
        ticket.setName("testticket");
        ticket.setDescription("only for test");
        ticket.setStatus("ok");
        String[] args = {JSON.toJSONString(ticket)};
        reqkey.setArgs(args);
        reqkey.setFuncName("createTicket");
        reqkey.setChainCode("cc_app0001202006081111440843077_00");
//        reqkey.setUserName("test21");
        reqkey.setTransientData(null);
        try {
            TransactionService.reqChainCode(reqkey);
        } catch(GlobalException g) {
            g.printStackTrace();
            return ;
        } catch (IOException e) {
            e.printStackTrace();
            return ;
        }
    }

    @Test
    public void reqApplicationChainCode() {
        try {
            initConfig();
        } catch (IOException e) {
            e.printStackTrace();
            return ;
        }
        ReqKeyEscrow reqkey = new ReqKeyEscrow();
//        String[] args = {"test"};
        Ticket ticket = new Ticket();
        ticket.setType("test");
        ticket.setUid("0001");
        ticket.setName("testticket");
        ticket.setDescription("only for test");
        ticket.setStatus("ok");
        String[] args = {JSON.toJSONString(ticket)};
        reqkey.setArgs(args);
        reqkey.setFuncName("createTicket");
        reqkey.setChainCode("cc_app0001202006081111440843077_00");
//        reqkey.setUserName("test21");
        reqkey.setTransientData(null);
        try {
            TransactionService.reqChainCode(reqkey);
        } catch(GlobalException g) {
            g.printStackTrace();
            return ;
        } catch (IOException e) {
            e.printStackTrace();
            return ;
        }
    }

}
