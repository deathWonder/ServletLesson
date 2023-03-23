package org.example.servlet;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.example.controller.PostController;
import org.example.config.JavaConfig;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;



public class MainServlet extends HttpServlet {
    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String DELETE = "DELETE";
    private static final String PATH = "/api/posts";

    final List<String> allowedMethods = List.of(GET, POST, DELETE);
    
    final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(JavaConfig.class);
    private final PostController controller = context.getBean(PostController.class);


    

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {

        try {
            final String path = req.getRequestURI();
            final String method = req.getMethod();


            if(allowedMethods.contains(method) || !path.contains(PATH)) {


            if (method.equals(GET) && path.equals(PATH)) {
                controller.all(resp);
                return;
            }
            if (method.equals(POST) && path.equals(PATH)) {
                controller.save(req.getReader(), resp);
                return;
            }

            if(path.matches(PATH+"/\\d+")) {
                final long id = Long.parseLong(path.substring(path.length() - 1));

                if (method.equals(GET)) {
                    controller.getById(id, resp);
                    return;
                }
                if (method.equals(DELETE)) {
                    controller.removeById(id, resp);
                    return;
                }
            }
            }
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}