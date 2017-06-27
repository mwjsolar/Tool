package server.webservice;

import javax.jws.WebService;

/**
 * Created by mwjsolar on 16/8/29.
 */
@WebService(endpointInterface="server.webservice.HelloService",serviceName="HelloWorldWs")//指定webservice所实现的接口以及服务名称
public class HelloServiceImpl implements HelloService {
    @Override
    public String hello() {
        System.out.println("test hello");
        return "SSS";
    }
}
