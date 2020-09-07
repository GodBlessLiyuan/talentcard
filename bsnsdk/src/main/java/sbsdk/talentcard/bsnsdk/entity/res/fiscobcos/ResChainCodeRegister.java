package sbsdk.talentcard.bsnsdk.entity.res.fiscobcos;

import sbsdk.talentcard.bsnsdk.entity.base.IBody;
import lombok.Data;

@Data
public class ResChainCodeRegister implements IBody {
    String eventId;
	@Override
	public String getEncryptionValue() {
		return (this.eventId == null?"":this.eventId);
	}
}
