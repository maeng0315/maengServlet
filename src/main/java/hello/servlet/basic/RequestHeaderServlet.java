package hello.servlet.basic;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Locale;

@WebServlet(name = "requestHeaderServlet", urlPatterns = "/request-header")
public class RequestHeaderServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        printStartLine(request);
        printHeaders(request);
        printHeaderUtils(request);
        printEtc(request);

    }

    //start-line 정보
    private void printStartLine(HttpServletRequest request) {
        System.out.println("--- REQUEST-LINE - start ---");
        System.out.println("request.getMethod() = " + request.getMethod());                 // GET
        System.out.println("request.getProtocal() = " + request.getProtocol());             // HTTP/1.1
        System.out.println("request.getScheme() = " + request.getScheme());                 // http
        System.out.println("request.getRequestURL() = " + request.getRequestURL());         // http://localhost:8080/request-header
        System.out.println("request.getRequestURI() = " + request.getRequestURI());         // /request-header
        System.out.println("request.getQueryString() = " + request.getQueryString());       // userName=hi
        System.out.println("request.isSecure() = " + request.isSecure());                   // https 사용 유무
        System.out.println("--- REQUEST-LINE - end ---");
        System.out.println();
    }

    //Header 모든 정보
    private void printHeaders(HttpServletRequest request) {
        System.out.println("--- Headers - start ---");

        /* java 9 이상 지원 문법
        request.getHeaderNames().asIterator()
                .forEachRemaining(headerName -> System.out.println(headerName + ":" + request.getHeader(headerName)));
        */

         Enumeration<String> headerNames = request.getHeaderNames();
         while(headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            System.out.println(headerName + ": " + request.getHeader(headerName));
         }

        System.out.println("--- Headers - end ---");
        System.out.println();
    }

    //Header 편리한 조회
    private void printHeaderUtils(HttpServletRequest request) {

        System.out.println("--- Header 편의 조회 start ---");

        System.out.println("[Host 편의 조회]");
        System.out.println("request.getServerName() = " + request.getServerName()); //Host 헤더
        System.out.println("request.getServerPort() = " + request.getServerPort()); //Host 헤더
        System.out.println();

        System.out.println("[Accept-Language 편의 조회] | 언어 사용 우선 순위");
        Enumeration<Locale> acceptLanguages = request.getLocales();
        while(acceptLanguages.hasMoreElements()) {
            Locale headerName = acceptLanguages.nextElement();
            System.out.println("locale: " + headerName);
        }
        System.out.println();

        /* java 9 이상 문법
        request.getLocales().asIterator()
                .forEachRemaining(locale -> System.out.println("locale = " +
                        locale));
        System.out.println("request.getLocale() = " + request.getLocale());
        */


        System.out.println("[cookie 편의 조회]");
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                System.out.println(cookie.getName() + ": " + cookie.getValue());
            }
        }
        System.out.println();

        System.out.println("[Content 편의 조회] | GET 방식은 Content를 거의 보내지 않아 확인하려면 Postman-POST");
        System.out.println("request.getContentType() = " + request.getContentType());
        System.out.println("request.getContentLength() = " + request.getContentLength());
        System.out.println("request.getCharacterEncoding() = " + request.getCharacterEncoding());

        System.out.println("--- Header 편의 조회 end ---");
        System.out.println();

        //헤더에서 원하는것만 꺼낼때
        System.out.println(request.getHeader("host"));;
    }

    private void printEtc(HttpServletRequest request) {
        System.out.println("--- 기타 조회 start ---");

        System.out.println("[Remote 정보] | 요청한 사용자의 정보");
        System.out.println("request.getRemoteHost() = " + request.getRemoteHost()); // 192.168.2.7
        System.out.println("request.getRemoteAddr() = " + request.getRemoteAddr()); // 192.168.2.7
        System.out.println("request.getRemotePort() = " + request.getRemotePort()); // 53166
        System.out.println();

        System.out.println("[Local 정보] | 내 서버에 대한 정보");
        System.out.println("request.getLocalName() = " + request.getLocalName()); // 10.100.20.108 | localhost
        System.out.println("request.getLocalAddr() = " + request.getLocalAddr()); // 10.100.20.108
        System.out.println("request.getLocalPort() = " + request.getLocalPort()); // 8080

        System.out.println("--- 기타 조회 end ---");
        System.out.println();
    }
}
