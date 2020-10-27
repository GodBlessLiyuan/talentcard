package sbsdk.talentcard.bsnsdk.entity.req.fabric;

import com.alibaba.fastjson.annotation.JSONField;
import sbsdk.talentcard.bsnsdk.entity.base.IBody;
import lombok.Data;

@Data
public class UserResister implements IBody {

    @JSONField(name  = "name")
    String name ;

    @JSONField(name  = "secret")
    String secret;

    @Override
    public String getEncryptionValue() {
        return (this.name == null? "":this.name) +(this.secret==null?"":this.secret);
    }
}
