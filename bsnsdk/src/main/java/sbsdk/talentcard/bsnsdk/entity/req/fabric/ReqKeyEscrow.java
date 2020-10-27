package sbsdk.talentcard.bsnsdk.entity.req.fabric;

import sbsdk.talentcard.bsnsdk.entity.base.IBody;
import lombok.Data;

import java.util.Map;

/**
 * 密钥托管模式请求参数
 */
@Data
public class ReqKeyEscrow implements IBody {
    String userName;
    String nonce;
    String chainCode;
    String funcName;
    String[]  args;
    Map<String,String> transientData;
	@Override
	public String getEncryptionValue() {

		String str = (this.userName == null? "":this.userName);
		str +=(this.nonce == null? "":this.nonce);
		str +=(this.chainCode == null? "":this.chainCode);
		str +=(this.funcName == null? "":this.funcName);

		for (int i =0 ;i<this.args.length;i++){
			str +=this.args[i];
		}

		if (this.transientData !=null) {
			for (Map.Entry<String, String> entry : this.transientData.entrySet()) {
				String mapKey = entry.getKey();
				String mapValue = entry.getValue();
				str = str +mapKey+mapValue;
			}
		}

		return str;
	}
}
