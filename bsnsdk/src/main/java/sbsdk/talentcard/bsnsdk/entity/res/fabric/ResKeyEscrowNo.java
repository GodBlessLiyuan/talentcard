package sbsdk.talentcard.bsnsdk.entity.res.fabric;

import sbsdk.talentcard.bsnsdk.entity.base.IBody;
import lombok.Data;

@Data
public class ResKeyEscrowNo implements IBody {
    sbsdk.talentcard.bsnsdk.entity.res.until.blockInfo blockInfo;
    sbsdk.talentcard.bsnsdk.entity.res.until.ccRes ccRes;
    
	@Override
	public String getEncryptionValue() {
		return (this.blockInfo.getTxId() == null?"":this.blockInfo.getTxId())
				+ (this.blockInfo.getBlockHash() == null?"":this.blockInfo.getBlockHash())
				+ (this.blockInfo.getStatus() == null?"":this.blockInfo.getStatus())
				+ (this.ccRes.getCcCode() == null?"":this.ccRes.getCcCode())
				+ (this.ccRes.getCcData() == null?"":this.ccRes.getCcData());
	}
}
