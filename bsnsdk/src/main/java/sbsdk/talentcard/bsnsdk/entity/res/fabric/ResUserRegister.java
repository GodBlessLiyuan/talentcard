package sbsdk.talentcard.bsnsdk.entity.res.fabric;

import sbsdk.talentcard.bsnsdk.entity.base.IBody;
import lombok.Data;

@Data
public class ResUserRegister implements IBody {
    String name;
    String secret;
	@Override
	public String getEncryptionValue() {
        return (this.name == null? "":this.name) +(this.secret==null?"":this.secret);
	}
}
