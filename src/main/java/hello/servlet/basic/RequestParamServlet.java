package hello.servlet.basic;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * 1. HTTP 요청 데이터 - GET 쿼리 파라미터(쿼리 스트링) 전송 기능
 * http://localhost:8080/request-param?userName=maeng&age=30
 * context-type이 존재하지 않음
 */
@WebServlet(name = "requestParamServlet", urlPatterns = "/request-param")
public class RequestParamServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("[전체 파라미터 조회] - start");
        Enumeration<String> parameterNames = request.getParameterNames();
        while(parameterNames.hasMoreElements()){
            String parameterName = parameterNames.nextElement();
            System.out.println(parameterName+": "+request.getParameter(parameterName));
        }
        System.out.println("[전체 파라미터 조회] - end");
        System.out.println();


        System.out.println("[단일 파라미터 조회 - start");
        String userName = request.getParameter("userName");
        String age = request.getParameter("age");
        System.out.println("userName: "+userName);
        System.out.println("age: "+age);
        System.out.println("[단일 파라미터 조회 - end");
        System.out.println();

        //http://localhost:8080/request-param?userName=maeng&age=30&userName=park
        System.out.println("[복수의 파라미터 조회] - start");
        String[] userNames = request.getParameterValues("userName");
        for(String name : userNames){
            System.out.println("name: "+name);
        }
        System.out.println("[복수의 파라미터 조회] - end");

        response.getWriter().write("requestParamServlet");
    }
}
