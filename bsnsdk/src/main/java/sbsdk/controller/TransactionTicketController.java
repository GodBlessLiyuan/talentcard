package sbsdk.controller;

import com.bsnsapi.fabric.chaincodeEntities.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sbsdk.config.MyConfig;
import sbsdk.convert.StringSToStringS;
import sbsdk.convert.Ticket2Ticket;
import sbsdk.convert.TransactionStatus;
import sbsdk.talentcard.bsnsdk.client.fabric.service.TransactionService;
import sbsdk.talentcard.bsnsdk.entity.req.fabric.ReqKeyEscrow;
import sbsdk.talentcard.bsnsdk.entity.res.fabric.ResKeyEscrow;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description:
 * @author: liyuan
 * @data 2020-09-08 18:12
 */
@RestController
public class TransactionTicketController {
    @Autowired
    private MyConfig myConfig;

    @PostMapping("transactionTicketApi/createTicket")
    public String createTicket(@RequestBody Ticket ticket, HttpServletRequest request){
        try{
            TransactionService.reqChainCode(this.ticketFunName(ticket, request));
            return TransactionStatus.OK.getStatus();
        }catch (Exception e){
            e.printStackTrace();
            return TransactionStatus.Error.getStatus();
        }
    }

    private ReqKeyEscrow ticketFunName(Ticket ticket, HttpServletRequest request) {
        myConfig.initConfig();
        ReqKeyEscrow reqKeyEscrow = Ticket2Ticket.convert(ticket);
        String[] url = request.getRequestURI().split("/");
        // /transactionProfile/getProfile
        reqKeyEscrow.setFuncName(url[2]);
        return reqKeyEscrow;
    }

    @PostMapping("transactionTicketApi/getTicketInfo")
    public String getTicketInfo(@RequestBody Ticket ticket, HttpServletRequest request){
        try{
            ResKeyEscrow resKeyEscrow = TransactionService.reqChainCode(this.ticketFunName(ticket, request));
            return resKeyEscrow.getCcRes().getCcData();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("transactionTicketApi/changeTicket")
    public String changeTicket(@RequestBody Ticket ticket, HttpServletRequest request){
        try{
            TransactionService.reqChainCode(this.ticketFunName(ticket, request));
            return TransactionStatus.OK.getStatus();
        }catch (Exception e){
            e.printStackTrace();
            return TransactionStatus.Error.getStatus();
        }
    }

    @PostMapping("transactionTicketApi/invokeTicket")
    public String invokeTicket(@RequestBody Ticket ticket, HttpServletRequest request){
        try{
            TransactionService.reqChainCode(this.ticketFunName(ticket, request));
            return TransactionStatus.OK.getStatus();
        }catch (Exception e){
            e.printStackTrace();
            return TransactionStatus.Error.getStatus();
        }
    }

    @PostMapping("transactionTicketApi/getHistoryForTicketStatus")
    public String getHistoryForTicketStatus(@RequestBody Ticket ticket, HttpServletRequest request){
        try{
            ResKeyEscrow resKeyEscrow = TransactionService.reqChainCode(this.ticketFunName(ticket, request));
            //这些封装
//            return resKeyEscrow.getCcRes().getCcData();
            return StringSToStringS.convert(resKeyEscrow.getCcRes().getCcData(),"dataInfo",Ticket.class);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
