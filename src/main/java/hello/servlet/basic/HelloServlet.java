package hello.servlet.basic;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "helloServlet", urlPatterns = "/hello")
public class HelloServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //soutm - 현재 메소드 이름 출력 단축어
        System.out.println("HelloServlet.service");

        //soutv - 변수 출력 단축어
        System.out.println("request = " + request);
        System.out.println("response = " + response);

        String userName = request.getParameter("userName");     //쿼리 파라미터(쿼리 스트링)를 편하게 가져올 수 있도록 서블릿에서 지원해주는 함수
        System.out.println("userName = " + userName);

        //ContentType
        response.setContentType("text/plain");
        response.setCharacterEncoding("utf-8");

        //HTTP 응답 메시지 바디에 들어감
        response.getWriter().write("hello " + userName);
    }
}
