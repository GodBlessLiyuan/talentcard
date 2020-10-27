package sbsdk.talentcard.bsnsdk.entity.req.fabric;

import sbsdk.talentcard.bsnsdk.entity.base.IBody;
import lombok.Data;

import java.io.Serializable;

@Data
public class ReqKeyEscrowEnroll implements IBody, Serializable {

    String name;

    String secret;

    String csrPem;
	@Override
	public String getEncryptionValue() {
		return (this.name == null? "":this.name)
				+(this.secret == null? "":this.secret)
		+(this.csrPem == null? "":this.csrPem);
	}
}
