package com.cassiezys.transaction.provider;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Copyright，2020
 * Author:曾念念
 * Description:
 */
public class AlipayConfig {
    //↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = " 2016101800714953";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCX1i3Z+/kdZdxPUkQ8CrACaZOc1hu3rNV3/Y9pGMIhZfrEErCzlpUdSYOq9v5Qi2enQLPt065FGgZk4hEuwpLKmqmb/0RMOpx8YdVnGgsXzeqomJHlGYbKWvLJWfVtOfGAuHa1f8bOn0K9vVYm2bxydnVGlkalAX0Ic7P2nUTrnGpQvArXCNVqK8041e/D5JGaDlfXqOPeaf4pVgSuEcVE+7eOXsonzFpxkyJzIZ1HcG/eTAKPnJi0Wg20xww4w4HiTapFLr3yCIYcupUGYNWCVU5UdWTYSsMypLMwVXAkvra63HfSqASP578NzbLBbQEi009X3doIa5ba4E6nDUcpAgMBAAECggEAcv4Qp8C/gFY+rgxeBSMK/ii7VNKLF2ZcHY35aN0JWYYEjy6giMKImC+u+nIpVRLfI7pLCtmAaHE3WnwkC0ftKGhOAAkpzpmtHj5yAq2krRiUma9C/0F/5g1PRtNMnrZ9gsExlm/5P086mrykXLQWlpHEa/sv8ZPY/+ew6htdsRoJAO2jU2KewFO/jJbvQI9iX3EE4lad05XY5/xv0VI2KTa+NGhzqji1xEST7LxUOyXQdJ1VIveeuRaVX+OJKBxrNmfrIISCuOmYwR19w1RVvS7ssRK7S7nfGBssvtMzHGjbdUz9OcdBn97tZ4Mr4TdZrgG/5Vs/l6sIHBY8Rxx9iQKBgQD2kMsyUkrDBaJ40/XI102GPu06YwTeufXz4LxBu5UM+sgUJNNv++d2ZuL2NRSe362957NSjiaKcLHL/ZktOsTIcRb16OhLD//V/xswWDe/81DtgZCIu3PkcP2O3XDhlD65B9UD4lWIoJhzBLutFjVH50eA39Gf06A6CBWueOp4uwKBgQCdpXpsI3UJjslEDXL73ApogDI0yEVnxzvUE5JtnlqOve/jmYn9flLK01qgFCshdcoVR14zKnYehJOKb/y2Vy9i2X9mfIX5wDp2ReH++biZL+JRKjUaLcAxQpB+tVLcaEUHOuRzHAwS0wLvXnLrOmlGT5JLpFYvRwIjm5oMT43jawKBgFBlK+wvivMGDlDpg/XFxiBkukNUcA0Tg4/OknO4b0Q5LS3sSAj2kSU5LP3sltmqw2LtfroHYae4UTDbcqudwvY8cakjFUMMZ3XmZX4g9aSf+J5IpdD/li1Bblu79oeHJ+B7HkjS7uLKwoqWW393g037qoPp9tbvUQo7Ap7ODF3rAoGAU1+E01QdPaTKRLdoLFM/He1OoCOasTvpJEwFkPvCoONKz3AQToRUfPgyYZ7JALUu3voTk7PNLG6pKPn5COYrJXgprKYGpQNI5+wljZe8TSfJ5WsREntHvcWabaiv3ek3OI+o6kfXCs2WK8ojtMv7iTfkAyUyf2ZqdzJ/0irwu2kCgYEAuoYPtJgm0OGlfWMGrAfW74QBwYu52AZUebRXeh9jPMtu47+JzKSpRycum6Bgy9bD6PPlPCI6ansFt1NtrNpp3kU9al9L7EXiTGb6PksvcLZ/BLBXYgCyzZIbwFWIDOoxgAoqvDBBKOwszRvuyqj+6FFwETXQ8F6wlx+MsnSdXPQ=";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAr5r50U/5wiPl31R3aH+IAhbqozfCDnV71Xv1rBdrYpqUR7lWCWAlqw8Y887obV9EYueDp7nIMLVkS0qTU0FedTkcUyp5+7ANWGYdA4WB+8nTwS65bda+U5JAWzA8pl3hzaR5jlYPeYyduDkg0lqOeXWY30q2S8l7lUPt2lCPH/qKdHmjz53UNUaXgxsQNnEeuoOhz/JzzFiUiyOMOzL2JY5bJE3/yyuYpqwqungIJ4KNCql8PhGr7s5cxi0NGwOcD3PgOUYs+jJfErf+yDMMB1uCMWgdcssxfKIfE1+TPg1njPZjbcZ3mfTIVFcqa5HuBjf+gAGlGt5xz0xf5LMi9QIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://alipay.trade.page.pay-JAVA-UTF-8/notify_url.jsp";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "http://工程公网访问地址/alipay.trade.page.pay-JAVA-UTF-8/return_url.jsp";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    // 支付宝网关
    public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
