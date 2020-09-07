package sbsdk.talentcard.bsnsdk.entity.res.fabric;

import sbsdk.talentcard.bsnsdk.entity.base.IBody;
import lombok.Data;

@Data
public class ResHeader implements IBody {
    Integer code;
    String msg;
    
	@Override
	public String getEncryptionValue() {
		return (this.code == null?"":this.code)
				+ (this.msg == null?"":this.msg);
	}
}
