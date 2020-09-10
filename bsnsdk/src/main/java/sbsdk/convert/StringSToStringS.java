package sbsdk.convert;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.talentcard.common.utils.StringToObjUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 参数形式：[{"":""},{"":""}]
 * @author: liyuan
 * @data 2020-09-08 19:21
 * 用于从json集合字符串中提取对象字符串，再将其转为json字符串
 * <p>
 * [{"txId":"63c98a94b5d6b5737ad1e39d6766bb4a274574cf1dfa54f7e06a618a00959732",
 * "dataInfo":"{\"applicationUid\":\"0100A\",\"pid\":\"0100B\",\"applyFor\":\"applyFor\",\"status\":\"ok\"}",
 * "txTime":"2020-09-08 10:47:55","isDelete":false}]
 * [{"applicationUid":"0100A","applyFor":"applyFor","pid":"0100B","status":"ok"}]
 */
public class StringSToStringS {
    public static <T> String convert(String paras, String lieName, Class<T> clazz) {
//        System.out.println(paras);
        List<JSONObject> lists = StringToObjUtil.strToObj(paras, List.class);
        List<T> list = new ArrayList<>(lists.size());
        for (JSONObject jsonObject : lists) {
            //jsonObject.get("dataInfo")得到的是字符串，不能使用  JSON.toJavaObject((JSON) String)。没删除是true
            if (jsonObject.containsKey("isDelete")) {
                if (!jsonObject.getBoolean("isDelete")) {
                    T dataInfo = JSON.parseObject((String) jsonObject.get(lieName), clazz);
                    list.add(dataInfo);
                }else{
                    list.add(null);
                }
            }else {
                T dataInfo = JSON.parseObject((String) jsonObject.get(lieName), clazz);
                list.add(dataInfo);
            }
        }
        return JSON.toJSONString(list);
    }
}
