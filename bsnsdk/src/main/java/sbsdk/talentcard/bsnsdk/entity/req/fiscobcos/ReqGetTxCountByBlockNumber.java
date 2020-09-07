package sbsdk.talentcard.bsnsdk.entity.req.fiscobcos;

import sbsdk.talentcard.bsnsdk.entity.base.IBody;
import lombok.Data;

@Data
public class ReqGetTxCountByBlockNumber implements IBody {
	String blockNumber;

	@Override
	public String getEncryptionValue() {
		return  (this.blockNumber == null? "":this.blockNumber);
	}
}
