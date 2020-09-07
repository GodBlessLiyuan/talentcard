package sbsdk.talentcard.bsnsdk.entity.res.fabric;

import lombok.Data;

@Data
public class ResGetBlockTranaction {
    String txId;
    int status;
    String createName;
    long timeSpanSec;
    long timeSpanNsec;
}
