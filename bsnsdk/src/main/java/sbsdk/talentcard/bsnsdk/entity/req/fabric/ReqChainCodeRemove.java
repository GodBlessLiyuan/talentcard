package sbsdk.talentcard.bsnsdk.entity.req.fabric;

import sbsdk.talentcard.bsnsdk.entity.base.IBody;
import lombok.Data;

@Data
public class ReqChainCodeRemove implements IBody {
    String eventId;
	@Override
	public String getEncryptionValue() {
		return (this.eventId == null? "":this.eventId);
	}
}
