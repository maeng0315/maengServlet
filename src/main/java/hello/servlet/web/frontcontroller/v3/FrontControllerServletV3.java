package hello.servlet.web.frontcontroller.v3;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV3", urlPatterns = "/front-controller/V3/*")  // 하위에 들어오는 컨트롤러가 있어도 무조건 여기로 먼저 들어 오도록 맵핑 | frontController
public class FrontControllerServletV3 extends HttpServlet {

    private Map<String, ControllerV3> controllerMap = new HashMap<>();

    public FrontControllerServletV3() {
        controllerMap.put("/front-controller/v3/members/new-form", new MemberFormControllerV3());
        controllerMap.put("/front-controller/v3/members/save", new MemberSaveControllerV3());
        controllerMap.put("/front-controller/v3/members", new MemberListControllerV3());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControllerServletV3.service");

        // 1. URI 변수에 담고 | /front-controller/V3/members/save
        String requestURI = request.getRequestURI();

        // 2. 받은 URI를 key로 controllerMap에서 컨트롤러를 꺼내 옴 (다형성)
        ControllerV3 controller = controllerMap.get(requestURI);
        if (controller == null) {   // 3. 예외 처리 (404)
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        /*
            // V2 Controller
            MyView view = controller.process(request, response);
            view.render(request, response);
        */
        Map<String, String> paramMap = crateParamMap(request);

        ModelView mv = controller.process(paramMap);
        String viewName = mv.getViewName();     // n
        // ew-form
        MyView view = viewResolver(viewName);
        view.render(mv.getModel(), request, response);

    }

    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }

    private Map<String, String> crateParamMap(HttpServletRequest request) {

        // request.getParameterNames() -> 모든 파라미터네임을 다 가져옴
        // forEachRemaining -> 반복
        // paramName -> Key 변수
        // request에서 모든 파라미터 네임을 가져와서 반복하며 paramMap에 넣어 줌
        Map<String, String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
        return paramMap;
    }
}
