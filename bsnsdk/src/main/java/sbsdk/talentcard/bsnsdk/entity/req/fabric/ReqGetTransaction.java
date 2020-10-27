package sbsdk.talentcard.bsnsdk.entity.req.fabric;

import sbsdk.talentcard.bsnsdk.entity.base.IBody;
import lombok.Data;

import java.io.Serializable;

@Data
public class ReqGetTransaction implements IBody, Serializable {
    String txId;
	@Override
	public String getEncryptionValue() {
		return (this.txId == null? "":this.txId);
	}
}
