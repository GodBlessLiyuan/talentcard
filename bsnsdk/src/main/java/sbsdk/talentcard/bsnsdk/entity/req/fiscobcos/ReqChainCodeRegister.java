package sbsdk.talentcard.bsnsdk.entity.req.fiscobcos;

import sbsdk.talentcard.bsnsdk.entity.base.IBody;
import lombok.Data;

@Data
public class ReqChainCodeRegister implements IBody {
    int eventType;
    String contractAddress;
    String contractName;
    String notifyUrl;
	String attachArgs;
	@Override
	public String getEncryptionValue() {
		return (this.eventType) +(this.contractAddress==null?"":this.contractAddress)+
				(this.contractName==null?"":this.contractName)+(this.notifyUrl==null?"":this.notifyUrl)
				+(this.attachArgs==null?"":this.attachArgs);
	}
}
