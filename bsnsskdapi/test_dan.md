#### 1:http://192.168.10.23:9060/transaction/create #### 
参数:{
       "ticket":{
           "type":"test",
           "uid":"001",
           "name":"testticket",
           "description":"only for test",
           "status":"ok"
       },
       "reqKeyEscrow":{
           "funcName":"createTicket",
           "chainCode":"cc_app0001202006081111440843077_00"
       }
   }
 返回值：
 {
     "status": 1000,
     "data": {
         "blockInfo": {
             "txId": "36d1181d82daf7085ecac265b7e4e40e3ece40c9016bff78af0724f7b8a15580",
             "blockHash": "",
             "status": 0
         },
         "ccRes": {
             "ccCode": 200,
             "ccData": "ticketKey:ticket_001 ,create ticket OK"
         },
         "encryptionValue": "36d1181d82daf7085ecac265b7e4e40e3ece40c9016bff78af0724f7b8a155800200ticketKey:ticket_001 ,create ticket OK"
     }
 }
 ##### http://192.168.10.23:9060/fabric/userRegister
 {
     "name":"test25",
     "secret":"123456"
 }
 返回信息
 {
     "status": 2000,
     "data": null
 }
 user is already registered
 #####  第二次大改 后测试的
 http://192.168.10.23:9060/transactionProfile/createProfile
 {
     "type":"test",
     "id":"0100A",
     "name":"ly test profile",
     "politicalStatus":"ok"
 }
 http://192.168.10.23:9060/transactionProfile/getProfile
{
     "id":"0100A"
 }
 
 http://192.168.10.23:9060/transactionApplyApi/apply
  {
      "applicationUid":"0100A",
      "pid":"0100B",
      "applyFor":"applyFor",
      "status":"ok"
  }
  http://192.168.10.23:9060/transactionApplyApi/getApplicationInfo
  参数如上
  http://192.168.10.23:9060/transactionApplyApi/getHistoryForApplication
  参数如上
  http://192.168.10.23:9060/transactionTicketApi/createTicket
   {
       "type":"test",
       "uid":"0100A",
       "name":"ly test ticket",
       "status":"ok",
       "description":"description"
   }
   http://192.168.10.23:9060/transactionTicketApi/getTicketInfo
   参数如上
   http://192.168.10.23:9060/transactionTicketApi/getHistoryForTicketStatus
   
   http://192.168.10.23:9060/transactionTicketApi/invokeTicket
   核销删除政策信息
   