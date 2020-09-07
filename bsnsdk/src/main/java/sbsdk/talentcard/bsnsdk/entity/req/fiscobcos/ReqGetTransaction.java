package sbsdk.talentcard.bsnsdk.entity.req.fiscobcos;

import sbsdk.talentcard.bsnsdk.entity.base.IBody;
import lombok.Data;

@Data
public class ReqGetTransaction implements IBody {
    String txHash;
	@Override
	public String getEncryptionValue() {
		return (this.txHash == null? "":this.txHash);
	}
}
