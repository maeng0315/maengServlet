package hello.servlet.basic.response;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/*
    - HTTP 응답 메시지는 주로 다음 내용을 담아서 전달한다.
    단순 텍스트 응답 writer.println("ok");
    HTML 응답 *
    HTTP API - MessageBody JSON 응답
 */
@WebServlet(name = "responseHtmlServlet", urlPatterns = "/response-html")
public class ResponseHtmlServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //Content-Type: text/html;charset=utf-8
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");

        PrintWriter writer = response.getWriter();
        writer.println("<html>");
        writer.println("<body>");
        writer.println(" <div>HTTP 응답 데이터 - 단순 텍스트, HTML</div>");
        writer.println("</body>");
        writer.println("</html>");

    }
}