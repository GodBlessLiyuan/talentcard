package sbsdk.talentcard.bsnsdk.entity.base;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class ResHeader {
    @JSONField(name  = "code")
    int code;

    @JSONField(name  = "msg")
    String msg ;

    public String getHeaderString(){
        return  this.code + this.msg;
    }
}
