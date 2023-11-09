package com.qingzhou.quareat_java.utils;

import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import com.wechat.pay.java.service.payments.jsapi.JsapiServiceExtension;
import com.wechat.pay.java.service.payments.jsapi.model.Amount;
import com.wechat.pay.java.service.payments.jsapi.model.Payer;
import com.wechat.pay.java.service.payments.jsapi.model.PrepayRequest;
import com.wechat.pay.java.service.payments.jsapi.model.PrepayWithRequestPaymentResponse;

import java.io.IOException;

@Component
@Slf4j
public class WxPayUtils {

    @Autowired
    private ResourceLoader resourceLoader;

//    public PrepayWithRequestPaymentResponse prepay(Order order, String openId, Integer payPrice) {
//
//        String merchantId = "1640014777";
//
//        String merchantSerialNumber = "71AD08C7E4A07336AD00FAF2C4C2E80329699DED";
//
//        String apiV3Key = "8JEkGIUY5wfoAVp4VznCkUlZ9rJ7knII";
//
//        /**
//         * 获取文件绝对路径
//         */
//        Resource resource = resourceLoader.getResource("classpath:wechat/apiclient_key.pem");
//        String privateKeyPath = "";
//
//        try {
//            privateKeyPath = resource.getFile().getAbsolutePath();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        Config config = new RSAAutoCertificateConfig.Builder()
//                .merchantId(merchantId)
//                .privateKeyFromPath(privateKeyPath)
//                .merchantSerialNumber(merchantSerialNumber)
//                .apiV3Key(apiV3Key)
//                .build();
//        // 构建service
//        JsapiServiceExtension service = new JsapiServiceExtension.Builder().config(config).build();
//
//        // request.setXxx(val)设置所需参数，具体参数可见Request定义
//        PrepayRequest request = new PrepayRequest();
//
//        //商品数量
//        Amount amount = new Amount();
//        amount.setTotal(payPrice);
//        request.setAmount(amount);
//
//        //支付者
//        Payer payer = new Payer();
//        payer.setOpenid(openId);
//        request.setPayer(payer);
//
//        //appId
//        request.setAppid("wxf3fe07dd9337bf8c");
//
//        //商家Id
//        request.setMchid("1640014777");
//
//        //商品描述
//        request.setDescription("订单：" + order.getId());
//        request.setNotifyUrl("https://api-qnc.mynatapp.cc/order/callback");
//        request.setOutTradeNo(System.currentTimeMillis() + "" + order.getId());
//
//        // 调用下单方法，得到应答
//        PrepayWithRequestPaymentResponse response = service.prepayWithRequestPayment(request);
//        // 使用微信扫描 code_url 对应的二维码，即可体验Native支付
//        return response;
//    }
}
