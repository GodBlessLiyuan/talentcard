package sbsdk.talentcard.bsnsdk.entity.res.fabric;

import com.alibaba.fastjson.annotation.JSONField;
import sbsdk.talentcard.bsnsdk.entity.base.IBody;
import lombok.Data;

@Data
public class ResKeyEscrowEnroll implements IBody {

	@JSONField(name  = "cert")
    String cert;
    @Override
	public String getEncryptionValue() {
    	return (this.cert == null?"":this.cert);
	}
}
