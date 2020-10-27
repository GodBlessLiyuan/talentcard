package sbsdk.talentcard.bsnsdk.entity.res.until;

import lombok.Data;

import java.io.Serializable;

@Data
public class blockInfo implements Serializable {
    /**
     * 1	交易Id	txId	String	是
     * 2	块哈希	blockHash	String	否	同步接口返回块哈希
     * 3	状态值	status	Int	是	详见交易状态描述
     */
    String txId;
    String blockHash;
    Integer status;

}
