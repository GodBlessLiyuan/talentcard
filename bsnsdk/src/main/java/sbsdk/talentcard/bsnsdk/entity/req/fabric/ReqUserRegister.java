package sbsdk.talentcard.bsnsdk.entity.req.fabric;

import com.alibaba.fastjson.annotation.JSONField;
import sbsdk.talentcard.bsnsdk.entity.base.IBody;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求参数
 */
@Data
public class ReqUserRegister implements IBody, Serializable {
	@JSONField(name  = "name")
	String name ;

    @JSONField(name  = "secret")
    String secret;

    @Override
    public String getEncryptionValue() {
        return (this.name == null? "":this.name) +(this.secret==null?"":this.secret);
    }
}
