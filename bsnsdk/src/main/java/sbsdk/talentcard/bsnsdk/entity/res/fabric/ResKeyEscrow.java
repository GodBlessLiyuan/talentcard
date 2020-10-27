package sbsdk.talentcard.bsnsdk.entity.res.fabric;

import sbsdk.talentcard.bsnsdk.entity.base.IBody;
import lombok.Data;

@Data
public class ResKeyEscrow implements IBody {
	/***
	 * 块信息，包含交易id
	 */

    sbsdk.talentcard.bsnsdk.entity.res.until.blockInfo blockInfo;
    /****
	 * 智能合约响应结果
	 * */
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
