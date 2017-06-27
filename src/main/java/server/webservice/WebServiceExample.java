package server.webservice;


import javax.xml.ws.Endpoint;

/**
 * Created by mwjsolar on 16/8/29.
 */
public class WebServiceExample {
    public static void main(String[] args) {
        HelloService helloService = new HelloServiceImpl();
        Endpoint.publish("http://localhost:8084/hello", helloService);
        System.out.println("Web Service暴露成功！");
    }
}
