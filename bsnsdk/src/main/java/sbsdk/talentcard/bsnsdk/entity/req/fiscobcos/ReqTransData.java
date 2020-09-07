package sbsdk.talentcard.bsnsdk.entity.req.fiscobcos;

import lombok.Data;

import java.util.List;

@Data
public class ReqTransData {
    String contractAbi;
    String contractAddress;
    String ContractName;
    String funcName;
    List<Object> funcParam;
    String userName;
}
