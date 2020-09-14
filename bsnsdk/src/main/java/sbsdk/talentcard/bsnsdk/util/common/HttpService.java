package sbsdk.talentcard.bsnsdk.util.common;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;
import sbsdk.talentcard.bsnsdk.entity.base.BaseReqModel;
import sbsdk.talentcard.bsnsdk.entity.base.BaseResArrayModel;
import sbsdk.talentcard.bsnsdk.entity.base.BaseResModel;
import sbsdk.talentcard.bsnsdk.entity.base.IBody;
import sbsdk.talentcard.bsnsdk.util.Log;
import sbsdk.talentcard.bsnsdk.util.enums.ResultInfoEnum;
import sbsdk.talentcard.bsnsdk.util.exception.GlobalException;
import org.apache.http.client.HttpClient;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.json.JsonException;
import java.io.File;

@Component
public class HttpService<T extends Object & IBody, K extends Object & IBody> {
    /***
     * httpService.post 封装了两次
     * */
    public BaseResModel<K> post(BaseReqModel<T> req, String url, String cert, Class<K> clazz) throws Exception {

        String res;
        BaseResModel<K> resModel = new BaseResModel<K>();

        req.sign();
        res = doPost(req, url, cert);


        if (res != null && res.length() > 0) {
            try {

                resModel = JSON.parseObject(res, resModel.getClass());

                String bodystr = JSON.toJSONString(resModel.getBody());

                resModel.setBody(JSON.parseObject(bodystr, clazz));

            } catch (JsonException e) {
                throw new GlobalException(ResultInfoEnum.DATA_CONVERSION_ERROR);
            }
            // 验签、数据转换
            if (resModel == null) {
                throw new GlobalException(ResultInfoEnum.INVALID_RESPONSE_ERROR);
            }

            boolean result = false;
            try {
                result = resModel.verify();
            } catch (Exception e) {
                throw new GlobalException(ResultInfoEnum.RES_MAC_ERROR);
            }

            if (!result) {
                throw new GlobalException(ResultInfoEnum.RES_MAC_ERROR);
            }else{
                Log.i("================验签成功！！！=================");
            }

            if (resModel.getHeader().getCode() != 0) {
                throw new GlobalException(resModel.getHeader().getMsg());
            } else {

                Log.i(resModel.getHeader().getMsg());
            }

            return resModel;

        } else {
            throw new GlobalException(ResultInfoEnum.SYSTEM_ERROR);
        }

    }

    public BaseResModel<K> noSignPost(BaseReqModel<T> req, String url, String cert, Class<K> clazz) {

        String res;
        BaseResModel<K> resModel = new BaseResModel<K>();
        try {
            res = doPost(req, url, cert);
            System.out.println("响应结果：" + res);
        } catch (GlobalException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(ResultInfoEnum.SYSTEM_ERROR);
        }

        if (res != null && res.length() > 0) {
            try {
                //有种指定转换为类class类型的味道
                resModel = JSON.parseObject(res, resModel.getClass());

                String bodystr = JSON.toJSONString(resModel.getBody());

                resModel.setBody(JSON.parseObject(bodystr, clazz));

            } catch (JsonException e) {
                throw new GlobalException(ResultInfoEnum.DATA_CONVERSION_ERROR);
            }
            // 验签、数据转换
            if (resModel == null) {
                throw new GlobalException(ResultInfoEnum.INVALID_RESPONSE_ERROR);
            }

            if (resModel.getHeader().getCode() != 0) {
                throw new GlobalException(resModel.getHeader().getMsg());
            } else {
            }
            return resModel;
        } else {
            throw new GlobalException(ResultInfoEnum.SYSTEM_ERROR);
        }

    }

    public BaseResArrayModel<K> arrayPost(BaseReqModel<T> req, String url, String cert, Class<K> clazz) {

        String res;
        BaseResArrayModel<K> resModel = new BaseResArrayModel<K>();
        try {
            req.sign();
            res = doPost(req, url, cert);

            System.out.println("响应结果：" + res);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GlobalException(ResultInfoEnum.SYSTEM_ERROR);
        }

        if (res != null && res.length() > 0) {
            try {

                resModel = JSON.parseObject(res, resModel.getClass());

                String bodystr = JSON.toJSONString(resModel.getBody());

                resModel.setBody(JSON.parseArray(bodystr, clazz));

            } catch (JsonException e) {
                throw new GlobalException(ResultInfoEnum.DATA_CONVERSION_ERROR);
            }
            // 验签、数据转换
            if (resModel == null) {
                throw new GlobalException(ResultInfoEnum.INVALID_RESPONSE_ERROR);
            }

            boolean result = false;
            try {
                result = resModel.verify();
            } catch (Exception e) {
                throw new GlobalException(ResultInfoEnum.RES_MAC_ERROR);
            }

            if (!result) {
                throw new GlobalException(ResultInfoEnum.RES_MAC_ERROR);
            }

            if (resModel.getHeader().getCode() != 0) {
                throw new GlobalException(resModel.getHeader().getMsg());
            } else {


            }

            return resModel;

        } else {
            throw new GlobalException(ResultInfoEnum.SYSTEM_ERROR);
        }

    }

    private String doPost(@NotNull BaseReqModel<T> req, String url, String cert) throws Exception {
        String param = JSON.toJSONString(req);
        System.out.println("------发送数据格式-------------:" + param);
        HttpClient httpClient = getHttpClient(cert);
        String res = HTTPSClientUtil.doPost(httpClient, url, param);

        System.out.println("响应结果：" + res);
        return res;
    }


    private HttpClient getHttpClient(String httpCertPath) throws Exception {
        Resource keystoreResource = new ClassPathResource(httpCertPath);
        File keystoreFile = keystoreResource.getFile();
        return new HTTPSCertifiedClient().init(keystoreFile.getAbsolutePath());
    }

}
