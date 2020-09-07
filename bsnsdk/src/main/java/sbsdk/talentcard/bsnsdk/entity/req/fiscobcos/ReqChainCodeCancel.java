package sbsdk.talentcard.bsnsdk.entity.req.fiscobcos;

import sbsdk.talentcard.bsnsdk.entity.base.IBody;
import lombok.Data;

@Data
public class ReqChainCodeCancel implements IBody {
	String eventId;
	@Override
	public String getEncryptionValue() {
		return  (this.eventId == null? "":this.eventId);
	}
}
