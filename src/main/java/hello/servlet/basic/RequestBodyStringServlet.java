package hello.servlet.basic;

import org.springframework.util.StreamUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet(name = "requestBodyStringServlet", urlPatterns = "/request-body-string")
public class RequestBodyStringServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //메시지 body의 내용을 바이트코드로 바로 얻을 수 있음
        ServletInputStream inputStream = request.getInputStream();

        //바이트코드를 String으로 바꿔줌 - spring 유틸리티 클래스
        String massageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        System.out.println("massageBody = " + massageBody);

        response.getWriter().write("requestBodyStringServlet");
    }
}
