package hello.servlet.basic.request;

import org.springframework.util.StreamUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 3. HTTP 요청 데이터 - API 메시지 바디 (단순 텍스트)
 * 'HTTP massage body'에 데이터를 직접 담아서 요청
 * 주로 JSON 사용
 */
@WebServlet(name = "requestBodyStringServlet", urlPatterns = "/request-body-string")
public class RequestBodyStringServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //메시지 body의 내용을 바이트코드로 바로 얻을 수 있음
        ServletInputStream inputStream = request.getInputStream();

        //바이트코드를 String으로 바꿔줌 - spring 유틸리티 클래스
        String massageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        System.out.println("massageBody = " + massageBody);

        response.getWriter().write("requestBodyStringServlet Page");
    }
}
