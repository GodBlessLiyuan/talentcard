package sbsdk.talentcard.bsnsdk.entity.req.fabric;

import sbsdk.talentcard.bsnsdk.entity.base.IBody;
import lombok.Data;

@Data
public class ReqUserInfo implements IBody {
    String appName;
    String appType;
    Integer caType;
    Integer algorithmType;
    String mspId;
    String channelId;
	@Override
	public String getEncryptionValue() {
		return null;
	}
}
