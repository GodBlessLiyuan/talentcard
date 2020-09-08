package com.bsnsapi.fabric.api;

import com.bsnsapi.fabric.chaincodeEntities.Application;
import com.bsnsapi.fabric.chaincodeEntities.Ticket;
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
    String createTicket(@RequestBody Ticket ticket, HttpServletRequest request);

    @PostMapping("transactionTicketApi/getTicketInfo")
    String getTicketInfo(@RequestBody Ticket ticket, HttpServletRequest request);

    @PostMapping("transactionTicketApi/changeTicket")
    String changeTicket(@RequestBody Ticket ticket, HttpServletRequest request);

    @PostMapping("transactionTicketApi/invokeTicket")
    String invokeTicket(@RequestBody Ticket ticket, HttpServletRequest request);

    @PostMapping("transactionTicketApi/getHistoryForTicketStatus")
    String getHistoryForTicketStatus(@RequestBody Ticket ticket, HttpServletRequest request);
}
