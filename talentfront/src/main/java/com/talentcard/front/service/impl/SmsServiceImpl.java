package com.talentcard.front.service.impl;

import com.talentcard.front.service.ISmsService;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

@Service
public class SmsServiceImpl implements ISmsService {
    /**
     * @param verifyCode
     * @return 1 成功 0 失敗
     */
    @Override
    public int sendSMS(String phone, String verifyCode) {

        try {
            String content = String.format("尊敬的用户，您本次的验证码是%s，请及时输入验证码完成操作。【衢江人才卡】", verifyCode);

            String postData = "sname=dlxinj00&spwd=admin123456&scorpid=&sprdid=1012818&sdst=" + phone + "&smsg=" + java.net.URLEncoder.encode(content, "utf-8");
            //发送POST请求
            URL url = new URL("http://cf.51welink.com/submitdata/Service.asmx/g_Submit");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setUseCaches(false);
            conn.setDoOutput(true);

            conn.setRequestProperty("Content-Length", "" + postData.length());
            OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            out.write(postData);
            out.flush();
            out.close();

            //获取响应状态
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                System.out.println("connect failed!");
                return 0;
            }
            //获取响应内容体
            String line, result = "";
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            while ((line = in.readLine()) != null) {
                result += line + "\n";
            }
            in.close();
            if (result.contains("提交成功")) {
                return 1;
            } else {
                return 0;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
