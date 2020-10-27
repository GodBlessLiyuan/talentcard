package sbsdk.talentcard.bsnsdk.entity.res.xuperChain;

import sbsdk.talentcard.bsnsdk.entity.base.IBody;
import lombok.Data;

@Data
public class ResKeyEscrow implements IBody {
    String txId;
	String queryInfo;


	@Override
	public String getEncryptionValue() {
		String str=(this.txId == null?"":this.txId)+(this.queryInfo == null?"":this.queryInfo);
		return str;

	}
}
