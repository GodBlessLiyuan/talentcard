package sbsdk.talentcard.bsnsdk.entity.req.fabric;

import sbsdk.talentcard.bsnsdk.entity.base.IBody;
import lombok.Data;

@Data
public class ReqChainCodeRegister implements IBody {
    String chainCode;
    String eventKey;
    String notifyUrl;
    String attachArgs;
	@Override
	public String getEncryptionValue() {
		return (this.chainCode == null? "":this.chainCode) +(this.eventKey==null?"":this.eventKey)+
				(this.notifyUrl==null?"":this.notifyUrl)+(this.attachArgs==null?"":this.attachArgs);
	}
}
