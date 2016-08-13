package server;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import java.io.IOException;

/**
 * Created by mwjsolar on 16/5/29.
 */
public class DispatcherServlet extends HttpServlet {

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        System.out.println("receive a req");

        res.getWriter().append("hello world ! this is a test");
    }

}
