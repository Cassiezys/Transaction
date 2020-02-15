package com.cassiezys.transaction.provider;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.CertAlipayRequest;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.GoodsDetail;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradePayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.cassiezys.transaction.model.Orders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Copyright，2020
 * Author:曾念念
 * Description:支付宝沙箱支付
 */
@Component
public class AlipayProvider {

    @Value("${open-api-domain}")
    private String serverUrl;
    @Value("${appid}")
    private String appId;
    @Value("${private-key}")
    private String privateKey;
    @Value("${alipay-public-key}")
    private String alipayPulicKey;
    @Value("${return-url}")
    private String returnUrl;
    @Value("${nofity-url")
    private String notifyUrl;
    @Value("${timeout-express}")
    private String timeout_express;

    //实例化客户端
    AlipayClient alipayClient;

    public AlipayClient createClien() {
        System.out.println(serverUrl);
        this.alipayClient = new DefaultAlipayClient(serverUrl, appId, privateKey, "json", "utf-8", alipayPulicKey, "RSA2");
        return alipayClient;
    }

    /**
     * 创建支付码
     * @param order
     * @param total
     * @return
     */
    public AlipayTradePagePayRequest trade_precreate(Orders order, Float total) {

        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(returnUrl);
        alipayRequest.setNotifyUrl(notifyUrl);
//——请在这里编写您的程序（以下代码仅作参考）——
//业务逻辑部分(请开始你的数据调用)
        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = "zhenbao" + System.currentTimeMillis() + (Math.random() * 10000000L) +"-"+ order.getId().toString();
        //付款金额，必填
        String total_amount = total.toString();
        //订单名称，必填
        String subject = order.getOuterTitle();
        //商品描述，可空
        String body = "商品描述(自定义)";
        alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\","
                + "\"total_amount\":\"" + total_amount + "\","
                + "\"subject\":\"" + subject + "\","
                + "\"body\":\"" + body + "\","
                + "\"timeout_express\":\""+ timeout_express +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        return alipayRequest;

    }

    /**
     * notify 和 return 中需要得到 支付宝返回过来的信息
     * @param params
     * @return
     */
    public boolean signature(Map params) {
        try {
            return AlipaySignature.rsaCheckV1(params, alipayPulicKey, "utf-8", "RSA2"); //调用SDK验证签名
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return false;
    }
}
