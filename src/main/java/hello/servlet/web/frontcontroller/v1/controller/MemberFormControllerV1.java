package hello.servlet.web.frontcontroller.v1.controller;

import hello.servlet.web.frontcontroller.v1.ControllerV1;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MemberFormControllerV1 implements ControllerV1 {

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String viewPath = "/WEB-INF/views/new-form.jsp";

        // RequestDispatcher -> 컨트롤러에서 뷰로 이동할때 사용
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);      // 이동할 경로 지정
        dispatcher.forward(request, response);  // 서블릿에서 jsp 호출

        // forward -> 다른 서블릿이나 JSP로 이동할 수 있는 기능, 서버 내부 적으로 호출 발생하기에 클라이언트가 인지할 수 없음, URL 변경 없음
        // redirect -> 실제 클라이언트(브라우저)에 응답이 나갔다가 클라이언트가 redirect 경로로 다시 요청함, 클라이언트가 인지할 수 있고(깜빡임), URL 변경 됨
    }
}
