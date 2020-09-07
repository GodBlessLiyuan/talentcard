package sbsdk.talentcard.bsnsdk.entity.req.xuperChain;

import sbsdk.talentcard.bsnsdk.entity.base.IBody;
import lombok.Data;

@Data
public class ReqGetBlockInformation implements IBody {
    int blockHeight;
	String blockHash;
	@Override
	public String getEncryptionValue() {
		return (this.blockHeight+"")+(this.blockHash == null? "":this.blockHash);
	}
}
