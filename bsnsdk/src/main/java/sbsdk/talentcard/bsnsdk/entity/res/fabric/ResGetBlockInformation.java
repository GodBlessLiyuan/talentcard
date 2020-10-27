package sbsdk.talentcard.bsnsdk.entity.res.fabric;

import sbsdk.talentcard.bsnsdk.entity.base.IBody;
import lombok.Data;

@Data
public class ResGetBlockInformation implements IBody {
    String blockHash;
	int blockNumber;
    String preBlockHash;
    long blockSize;
	int blockTxCount;

	 ResGetBlockTranaction[] transactions;

	 public ResGetBlockInformation(){
	 	this.transactions = new  ResGetBlockTranaction[]{};
	 }
    
	@Override
	public String getEncryptionValue() {
		String str = "";

		str +=this.blockHash==null?"":this.blockHash;
		str +=this.blockNumber ;
		str +=this.preBlockHash==null?"":this.preBlockHash;
		str +=this.blockSize;
		str +=this.blockTxCount ;

		for (int i=0;i<this.transactions.length;i++) {
			ResGetBlockTranaction trans = this.transactions[i];
			str += (trans.txId == null ? "" : trans.txId);
			str +=  trans.status;
			str += (trans.createName == null ? "" : trans.createName);
			str +=  trans.timeSpanSec;
			str += trans.timeSpanNsec;
		}

		return str;
	}
}
