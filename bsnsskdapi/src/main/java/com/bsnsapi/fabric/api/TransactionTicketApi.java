package com.bsnsapi.fabric.api;

import com.bsnsapi.fabric.chaincodeEntities.Application;
import com.bsnsapi.fabric.chaincodeEntities.Ticket;
import com.bsnsapi.fabric.vo.ResultVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description:
 * @author: liyuan
 * @data 2020-09-08 17:57
 */
public interface TransactionTicketApi {
    @PostMapping("transactionTicketApi/createTicket")
    ResultVO createTicket(@RequestBody Ticket ticket, HttpServletRequest request);

    @PostMapping("transactionTicketApi/getTicketInfo")
    ResultVO getTicketInfo(@RequestBody Ticket ticket, HttpServletRequest request);

    @PostMapping("transactionTicketApi/changeTicket")
    ResultVO changeTicket(@RequestBody Ticket ticket, HttpServletRequest request);

    @PostMapping("transactionTicketApi/invokeTicket")
    ResultVO invokeTicket(@RequestBody Ticket ticket, HttpServletRequest request);

    @PostMapping("transactionTicketApi/getHistoryForTicketStatus")
    ResultVO getHistoryForTicketStatus(@RequestBody Ticket ticket, HttpServletRequest request);
}
