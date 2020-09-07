package sbsdk.talentcard.bsnsdk.entity.req.fiscobcos;

import sbsdk.talentcard.bsnsdk.entity.base.IBody;
import lombok.Data;

@Data
public class ReqTrans implements IBody {
    String contractName;
    String transData;

    @Override
    public String getEncryptionValue() {
        return (this.contractName == null ? "" : this.contractName)
                + (this.contractName == null ? "" : this.transData);
    }
}
