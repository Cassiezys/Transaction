package com.cassiezys.transaction.controller;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.cassiezys.transaction.dto.AliOrderCreateDTO;
import com.cassiezys.transaction.dto.PaginationDTO;
import com.cassiezys.transaction.dto.ResultDTO;
import com.cassiezys.transaction.exception.CustomizeCodeException;
import com.cassiezys.transaction.exception.ErrorCodeEnumImp;
import com.cassiezys.transaction.model.Orders;
import com.cassiezys.transaction.model.User;
import com.cassiezys.transaction.provider.AlipayProvider;
import com.cassiezys.transaction.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Copyright，2020
 * Author:曾念念
 * Description:订单
 */
@Controller
public class OrderController {

    @Autowired
    OrderService orderService;
    @Autowired
    AlipayProvider alipayProvider;


    @ResponseBody
    @RequestMapping(value = "/order" ,produces = "text/html; charset=UTF-8")
    public String doBuy(@RequestParam("id")Long id,
                        @RequestParam("amount")Integer amount,
                        @RequestParam("total")Float total,
                        HttpServletResponse response){
        AlipayClient alipayClient = alipayProvider.createClien();
        Orders thisOrder = orderService.updateOrderById(id, amount);
        AlipayTradePagePayRequest alipayRequest = alipayProvider.trade_precreate(thisOrder, total);

        String result = null;
        try {
            result = alipayClient.pageExecute(alipayRequest).getBody();

        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * notify.url.jsp
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/order/notify")
    public String doPost(HttpServletRequest request,
                         HttpServletResponse response,
                         Model model){
        System.out.println("post方法");
        model.addAttribute("session","tradepaypost");
        model.addAttribute("sessionName","支付页面");
        try {
            response.setContentType("text/html;charset=UTF-8");
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        /* *
         * 功能：支付宝服务器异步通知页面
         * 日期：2017-03-30
         * 说明：
         * 以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
         * 该代码仅供学习和研究支付宝接口使用，只是提供一个参考。


         *************************页面功能说明*************************
         * 创建该页面文件时，请留心该页面文件中无任何HTML代码及空格。
         * 该页面不能在本机电脑测试，请到服务器上做测试。请确保外部可以访问该页面。
         * 如果没有收到该页面返回的 success
         * 建议该页面只做支付成功的业务逻辑处理，退款的处理请以调用退款查询接口的结果为准。
         */

        //获取支付宝POST过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            try {
                valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            params.put(name, valueStr);
        }

        boolean signVerified = alipayProvider.signature(params);

        //——请在这里编写您的程序（以下代码仅作参考）——

	/* 实际验证过程建议商户务必添加以下校验：
	1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
	2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
	3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
	4、验证app_id是否为该商户本身。
	*/
        if(signVerified) {//验证成功
            //商户订单号
            try {
                String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            //支付宝交易号
            try {
                String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            //交易状态
            String trade_status = null;
            try {
                trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            if(trade_status.equals("TRADE_FINISHED")){
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序

                //注意：
                //退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
            }else if (trade_status.equals("TRADE_SUCCESS")){
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //如果有做过处理，不执行商户的业务程序

                //注意：
                //付款完成后，支付宝系统发送该交易状态通知
            }

            model.addAttribute("message","success");

        }else {//验证失败
            model.addAttribute("message","fail");

            //调试用，写文本函数记录程序运行情况是否正常
            //String sWord = AlipaySignature.getSignCheckContentV1(params);
            //AlipayConfig.logResult(sWord);
        }

        //——请在这里编写您的程序（以上代码仅作参考）——

        return "profile";
    }


    /**
     * 支付成功
     * return_url.jsp
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/order/return")
    public String doGet(HttpServletRequest request,
                        HttpServletResponse response,
                        Model model){
        System.out.println("支付成功");
        //获取支付宝GET过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            try {
                valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            params.put(name, valueStr);
        }

        boolean signVerified = alipayProvider.signature(params);

        //——请在这里编写您的程序（以下代码仅作参考）——
        if(signVerified) {
            //商户订单号
            String out_trade_no = null;
            //支付宝交易号
            String trade_no = null;
            //付款金额
            String total_amount = null;
            try {
                out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
                 Long oid= Long.valueOf(out_trade_no.substring(out_trade_no.lastIndexOf("-")+1));
                 orderService.payOK(oid);
                trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
                total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }else {
            throw new CustomizeCodeException(ErrorCodeEnumImp.FAIL_ALIPAY);
        }
        //——请在这里编写您的程序（以上代码仅作参考）——
        return "redirect:/profile/cart";
    }


    /**
     * 删除订单
     * @param id 订单的id
     * @param request
     * @return
     */
    @ResponseBody
    @GetMapping("/order/{id}")
    public Object delorder(@PathVariable("id")Long id,
                           HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
            throw new CustomizeCodeException(ErrorCodeEnumImp.NO_LOGIN);
        }
        orderService.delOrder(id);
        return ResultDTO.successOf();
    }
}
