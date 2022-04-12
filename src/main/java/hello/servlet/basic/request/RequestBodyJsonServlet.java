package hello.servlet.basic.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.servlet.basic.HelloData;
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
 * 4. HTTP 요청 데이터 - API 메시지 바디 (JSON)
 */
@WebServlet(name = "requestBodyJsonServlet", urlPatterns = "/request-body-json")
public class RequestBodyJsonServlet extends HttpServlet {

    //스프링 부트 기본 JSON 파싱 라이브러리
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //메시지 body의 내용을 바이트코드로 바로 얻을 수 있음
        ServletInputStream inputStream = request.getInputStream();

        //바이트코드를 String으로 바꿔줌 - spring 유틸리티 클래스
        String massageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        System.out.println("massageBody = " + massageBody);

        HelloData helloData = objectMapper.readValue(massageBody, HelloData.class);

        System.out.println("helloData.userName = " + helloData.getUserName());
        System.out.println("helloData.age = " + helloData.getAge());

        response.getWriter().write("requestBodyJsonServlet Page");

    }
}
