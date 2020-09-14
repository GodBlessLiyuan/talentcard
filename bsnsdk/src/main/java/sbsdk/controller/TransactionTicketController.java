package sbsdk.controller;

import com.bsnsapi.fabric.chaincodeEntities.Ticket;
import com.bsnsapi.fabric.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sbsdk.convert.Ticket2Ticket;
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
    private TransactionService transactionService;

    @PostMapping("transactionTicketApi/createTicket")
    public ResultVO createTicket(@RequestBody Ticket ticket, HttpServletRequest request) {
        try {
            transactionService.reqChainCode(this.ticketFunName(ticket, request));
            return new ResultVO(1000);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultVO(2000);
        }
    }

    private ReqKeyEscrow ticketFunName(Ticket ticket, HttpServletRequest request) {
        ReqKeyEscrow reqKeyEscrow = Ticket2Ticket.convert(ticket);
        String[] url = request.getRequestURI().split("/");
        // /transactionProfile/getProfile
        reqKeyEscrow.setFuncName(url[2]);
        return reqKeyEscrow;
    }

    @PostMapping("transactionTicketApi/getTicketInfo")
    public ResultVO getTicketInfo(@RequestBody Ticket ticket, HttpServletRequest request) {
        try {
            ResKeyEscrow resKeyEscrow = transactionService.reqChainCode(this.ticketFunName(ticket, request));
            return new ResultVO(1000, resKeyEscrow.getCcRes().getCcData());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultVO(2000);
        }
    }

    @PostMapping("transactionTicketApi/changeTicket")
    public ResultVO changeTicket(@RequestBody Ticket ticket, HttpServletRequest request) {
        try {
            transactionService.reqChainCode(this.ticketFunName(ticket, request));
            return new ResultVO(1000);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultVO(2000);
        }
    }

    @PostMapping("transactionTicketApi/invokeTicket")
    public ResultVO invokeTicket(@RequestBody Ticket ticket, HttpServletRequest request) {
        try {
            transactionService.reqChainCode(this.ticketFunName(ticket, request));
            return new ResultVO(1000);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultVO(2000);
        }
    }

    @PostMapping("transactionTicketApi/getHistoryForTicketStatus")
    public ResultVO getHistoryForTicketStatus(@RequestBody Ticket ticket, HttpServletRequest request) {
        try {
            ResKeyEscrow resKeyEscrow = transactionService.reqChainCode(this.ticketFunName(ticket, request));
//            return resKeyEscrow.getCcRes().getCcData();
            return new ResultVO(1000, resKeyEscrow.getCcRes().getCcData());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultVO(2000);
        }
    }
}
