package sbsdk.talentcard.bsnsdk.entity.res.fabric;
import sbsdk.talentcard.bsnsdk.entity.base.IBody;
import lombok.Data;

@Data
public class ResUserInfo implements IBody {
    String appName;
    String appType;
    Integer caType;
    Integer algorithmType;
    String mspId;
    String channelId;
   
	@Override
	public String getEncryptionValue() {
		return (this.appName == null? "":this.appName)
			+(this.appType == null? "":this.appType)
			+(this.caType == null? "":this.caType)
			+(this.algorithmType == null? "":this.algorithmType)
			+(this.mspId == null? "":this.mspId)
			+(this.channelId == null? "":this.channelId);
	}
}
