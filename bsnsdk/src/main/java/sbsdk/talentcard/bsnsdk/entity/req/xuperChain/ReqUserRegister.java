package sbsdk.talentcard.bsnsdk.entity.req.xuperChain;

import com.alibaba.fastjson.annotation.JSONField;
import sbsdk.talentcard.bsnsdk.entity.base.IBody;
import lombok.Data;

/**
 * 用户注册请求参数
 */
@Data
public class ReqUserRegister implements IBody {
	@JSONField(name  = "userId")
	String userId ;


    @Override
    public String getEncryptionValue() {
        return (this.userId == null? "":this.userId);
    }
}
