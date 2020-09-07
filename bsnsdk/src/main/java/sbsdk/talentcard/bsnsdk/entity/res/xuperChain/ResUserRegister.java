package sbsdk.talentcard.bsnsdk.entity.res.xuperChain;

import sbsdk.talentcard.bsnsdk.entity.base.IBody;
import lombok.Data;

@Data
public class ResUserRegister implements IBody {
    String userId;
    String userAddr;
	@Override
	public String getEncryptionValue() {
        return (this.userId == null? "":this.userId) +(this.userAddr==null?"":this.userAddr);
	}
}
