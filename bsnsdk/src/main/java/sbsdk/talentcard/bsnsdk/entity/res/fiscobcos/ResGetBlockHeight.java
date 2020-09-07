package sbsdk.talentcard.bsnsdk.entity.res.fiscobcos;

import sbsdk.talentcard.bsnsdk.entity.base.IBody;
import lombok.Data;

@Data
public class ResGetBlockHeight implements IBody {
	String data;
	@Override
	public String getEncryptionValue() {
		String str = this.data==null?"":this.data;
		return str;
	}
}
