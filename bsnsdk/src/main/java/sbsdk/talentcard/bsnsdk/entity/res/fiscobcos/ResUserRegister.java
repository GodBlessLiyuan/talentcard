package sbsdk.talentcard.bsnsdk.entity.res.fiscobcos;

import sbsdk.talentcard.bsnsdk.entity.base.IBody;
import lombok.Data;

@Data
public class ResUserRegister implements IBody {
    String userId;
    String userAddress;
	@Override
	public String getEncryptionValue() {
        return (this.userId == null? "":this.userId) +(this.userAddress==null?"":this.userAddress);
	}
}
