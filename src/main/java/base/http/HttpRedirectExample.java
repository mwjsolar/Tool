package base.http;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;

/**
 * Created by mwjsolar on 16/11/11.
 */
public class HttpRedirectExample {
    //开始请求地址
    private static final String START_REQUEST_URL = "http://test.m.zc.163.com/order/pay.shtml?id=82298339585";

    public static void main(String[] args) {
        HttpClient client = new DefaultHttpClient();
        try {
            client.execute(new HttpGet(START_REQUEST_URL));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println();
    }
}
