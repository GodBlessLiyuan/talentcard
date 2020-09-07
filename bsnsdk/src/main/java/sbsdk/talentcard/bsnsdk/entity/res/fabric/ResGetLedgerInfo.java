package sbsdk.talentcard.bsnsdk.entity.res.fabric;

import sbsdk.talentcard.bsnsdk.entity.base.IBody;
import lombok.Data;

@Data
public class ResGetLedgerInfo implements IBody {
    String blockHash;
    Long height;
    String preBlockHash;
    
	@Override
	public String getEncryptionValue() {
		return (this.blockHash == null?"":this.blockHash)
				+ (this.height == null?"":this.height)
				+ (this.preBlockHash == null?"":this.preBlockHash);
	}
}
