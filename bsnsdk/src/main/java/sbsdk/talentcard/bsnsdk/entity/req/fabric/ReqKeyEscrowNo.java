package sbsdk.talentcard.bsnsdk.entity.req.fabric;

import sbsdk.talentcard.bsnsdk.entity.base.IBody;
import lombok.Data;

@Data
public class ReqKeyEscrowNo implements IBody {
    String transData;
	@Override
	public String getEncryptionValue() {
		return this.transData == null?"":this.transData;
	}
}
