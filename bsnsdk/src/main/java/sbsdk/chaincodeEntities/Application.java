package sbsdk.chaincodeEntities;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
/***
 * 4-2 人才申请政策智能合约参数字段：
 *    人才申请实体类：
 * */
@Data
public class Application {
    //UID string `json:"applicationUid"`
    //	PID string `json:"pid"`
    //	ApplyFor string `json:"applyFor"`
    //	Status string `json:"status"`

    @JSONField(name = "applicationUid")
    String applicationUid;

    //profile id
    @JSONField(name = "pid")
    String pid;

    @JSONField(name = "applyFor")
    String applyFor;
    @JSONField(name = "status")
    String status;
}
