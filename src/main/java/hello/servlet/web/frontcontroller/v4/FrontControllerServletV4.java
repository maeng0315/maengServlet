package hello.servlet.web.frontcontroller.v4;

import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v4.controller.MemberFormControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberListControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberSaveControllerV4;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV4", urlPatterns = "/front-controller/v4/*")  // 하위에 들어오는 컨트롤러가 있어도 무조건 여기로 먼저 들어 오도록 맵핑 | frontController
public class FrontControllerServletV4 extends HttpServlet {

    private Map<String, ControllerV4> controllerMap = new HashMap<>();

    public FrontControllerServletV4() {
        controllerMap.put("/front-controller/v4/members/new-form", new MemberFormControllerV4());
        controllerMap.put("/front-controller/v4/members/save", new MemberSaveControllerV4());
        controllerMap.put("/front-controller/v4/members", new MemberListControllerV4());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControllerServletV4.service");

        // 1. URI 변수에 담고 | /front-controller/V4/members/save
        String requestURI = request.getRequestURI();

        // 2. 받은 URI를 key로 controllerMap에서 컨트롤러를 꺼내 옴 (다형성)
        ControllerV4 controller = controllerMap.get(requestURI);
        if (controller == null) {   // 3. 예외 처리 (404)
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
    /*
        // V2 Controller
        MyView view = controller.process(request, response);
        view.render(request, response);
    */
    /*  // V3 Controller
        Map<String, String> paramMap = createParamMap(request);

        ModelView mv = controller.process(paramMap);
        String viewName = mv.getViewName();     // 논리이름 (new-form)

        MyView view = viewResolver(viewName);   // new-form -> /WEB-INF/views/new-form.jsp
        view.render(mv.getModel(), request, response);
    */
        Map<String, String> paramMap = createParamMap(request);
        Map<String, Object> model = new HashMap<>();

        String viewName = controller.process(paramMap, model);  // 논리이름 (new-form)

        MyView view = viewResolver(viewName);   // new-form -> /WEB-INF/views/new-form.jsp
        view.render(model, request, response);
    }

    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }

    //
    private Map<String, String> createParamMap(HttpServletRequest request) {
        /*
            request.getParameterNames() -> 모든 파라미터네임을 다 가져옴
            forEachRemaining -> 반복
            paramName -> Key 변수
            request에서 모든 파라미터 네임을 가져와서 반복하며 paramMap에 넣어 줌
        */
        Map<String, String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));

        return paramMap;
    }
}
