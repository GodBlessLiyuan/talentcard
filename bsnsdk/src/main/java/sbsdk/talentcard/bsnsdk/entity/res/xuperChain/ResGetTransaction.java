package sbsdk.talentcard.bsnsdk.entity.res.xuperChain;

import sbsdk.talentcard.bsnsdk.entity.base.IBody;
import lombok.Data;

@Data
public class ResGetTransaction implements IBody {
    String txId;
    String blockId;
	String version;
	ContractRequestData[] contractRequests;
	long receivedTimestamp;

	public ResGetTransaction(){
		this.contractRequests =new ContractRequestData[]{};
	}
	@Override
	public String getEncryptionValue() {
		String str= (this.txId == null?"":this.txId)
				+ (this.blockId == null?"":this.blockId)
				+ (this.version== null?"":this.version);
		for (int i = 0; i<this.contractRequests.length; i++) {
			ContractRequestData contractRequestData = this.contractRequests[i];
			str += (contractRequestData.contractName == null ? "" : contractRequestData.contractName);
			str += (contractRequestData.methodName == null ? "" : contractRequestData.methodName);
			str += (contractRequestData.args == null ? "" : contractRequestData.args);
		}
		str+=receivedTimestamp;
		return str;
	}
}
