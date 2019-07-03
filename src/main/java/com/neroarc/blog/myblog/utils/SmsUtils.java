package com.neroarc.blog.myblog.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.neroarc.blog.myblog.constant.SMSConstants;

/**
 * @author: fjx
 * @date: 2019/3/23 18:55
 * Descripe:
 */
public class SmsUtils {

    public static SendSmsResponse sendSms(String telephone,String code) throws ClientException {

        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", SMSConstants.ACCESS_KEY_ID, SMSConstants.ACCESS_KEY_SECRET);
        DefaultProfile.addEndpoint("cn-hangzhou","cn-hangzhou",SMSConstants.PRODUCT,SMSConstants.DOMAIN);


        IAcsClient client = new DefaultAcsClient(profile);
        SendSmsRequest sendSmsRequest = new SendSmsRequest();
        sendSmsRequest.setPhoneNumbers(telephone);
        sendSmsRequest.setSignName("Altri");
        sendSmsRequest.setTemplateCode("SMS_161592670");
        sendSmsRequest.setTemplateParam("{\"code\":\""+code+"\"}");

        SendSmsResponse sendSmsResponse = client.getAcsResponse(sendSmsRequest);
        if(sendSmsResponse.getCode()!= null && sendSmsResponse.getCode().equals("OK")){
            System.out.println("短信发送成功！");
        }else {
            System.out.println("短信发送失败！");
        }
        return sendSmsResponse;

    }

    public static void main(String[] args) {
        int code = (int)(Math.random()*9000)+1000;

        System.out.println("验证码："+code);

        try {
            SendSmsResponse sendSmsResponse = sendSms("18227805311",Integer.toString(code));
            System.out.println("Code=" + sendSmsResponse.getCode());
            System.out.println("Message=" + sendSmsResponse.getMessage());
            System.out.println("RequestId=" + sendSmsResponse.getRequestId());
            System.out.println("BizId=" + sendSmsResponse.getBizId());
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}


