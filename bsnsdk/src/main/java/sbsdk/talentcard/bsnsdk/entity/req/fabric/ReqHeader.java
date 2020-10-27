package sbsdk.talentcard.bsnsdk.entity.req.fabric;

import sbsdk.talentcard.bsnsdk.entity.base.IBody;
import lombok.Data;

/**
 * 头部请求参数
 */
@Data
public class ReqHeader implements IBody {
    String userCode;
    String appCode;
   // String tId;
    
	@Override
	public String getEncryptionValue() {
		return (this.userCode == null? "":this.userCode)+(this.appCode == null? "":this.appCode);
	}

};
