package server.jetty;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.mortbay.jetty.servlet.ServletHandler;
import org.mortbay.jetty.servlet.ServletHolder;
import org.mortbay.thread.QueuedThreadPool;
import server.DispatcherServlet;

/**
 * Created by mwjsolar on 16/5/29.
 */
public class JettyServer {

    public static Server newServer() {
        int threads = 10;
        QueuedThreadPool threadPool = new QueuedThreadPool();
        threadPool.setDaemon(true);
        threadPool.setMaxThreads(threads);
        threadPool.setMinThreads(threads);

        SelectChannelConnector connector = new SelectChannelConnector();
        connector.setHost("127.0.0.1");

        connector.setPort(8080);

        Server server = new Server();
        server.setThreadPool(threadPool);
        server.addConnector(connector);

        ServletHandler servletHandler = new ServletHandler();
        ServletHolder servletHolder = servletHandler.addServletWithMapping(DispatcherServlet.class, "/*");
        servletHolder.setInitOrder(2);

        server.addHandler(servletHandler);
        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return server;
    }

    public static void main(String[] args) {

        JettyServer.newServer();

        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}