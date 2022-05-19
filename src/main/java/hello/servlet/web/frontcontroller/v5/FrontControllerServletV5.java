package hello.servlet.web.frontcontroller.v5;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import hello.servlet.web.frontcontroller.v4.controller.MemberFormControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberListControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberSaveControllerV4;
import hello.servlet.web.frontcontroller.v5.adapter.ControllerV3HandlerApapter;
import hello.servlet.web.frontcontroller.v5.adapter.ControllerV4HandlerApapter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "frontControllerServletV5", urlPatterns = "/front-controller/v5/*")
public class FrontControllerServletV5 extends HttpServlet {

//    private Map<String, ControllerV4> controllerMap = new HashMap<>();
    private final Map<String, Object> handlerMappingMap = new HashMap<>();      // 여러 컨트롤러를 지원 해야 하니 Object로
    private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();   // 꺼낼 수 있는 컨트롤러 리스트

    public FrontControllerServletV5() {
        initHandlerMappingMap();
        initHandlerAdapters();
    }

    private void initHandlerMappingMap() {
        // V3
        handlerMappingMap.put("/front-controller/v5/v3/members/new-form", new MemberFormControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members/save", new MemberSaveControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members", new MemberListControllerV3());

        // V4
        handlerMappingMap.put("/front-controller/v5/v4/members/new-form", new MemberFormControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members/save", new MemberSaveControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members", new MemberListControllerV4());
    }

    private void initHandlerAdapters() {
        // V3
        handlerAdapters.add(new ControllerV3HandlerApapter());

        // V4
        handlerAdapters.add(new ControllerV4HandlerApapter());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 1. 일치하는 핸들러 찾아오기
        Object handler = getHandler(request);
        if (handler == null) {   // 3. 예외 처리 (404)
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 2. 위에서 찾은 핸들러랑 일치하는 어댑터 찾아오기
        MyHandlerAdapter adapter = getHandlerAdapter(handler);

        ModelView mv = adapter.handle(request, response, handler);

        String viewName = mv.getViewName();     // 논리이름 (new-form)
        MyView view = viewResolver(viewName);   // new-form -> /WEB-INF/views/new-form.jsp

        view.render(mv.getModel(), request, response);
    }

    private MyHandlerAdapter getHandlerAdapter(Object handler) {
        for (MyHandlerAdapter adapter : handlerAdapters) {
            if (adapter.supports(handler)) {
                return adapter;
            }
        }
        throw new IllegalArgumentException("handler adapter를 찾을 수 없습니다. handler=" + handler);
    }

    private Object getHandler(HttpServletRequest request) {

        // 1. URI 변수에 담고 | /front-controller/V3/members/save
        String requestURI = request.getRequestURI();

        // 2. 받은 URI를 key로 handlerMappingMap에서 컨트롤러를 꺼내 옴 (다형성)
        return handlerMappingMap.get(requestURI);
    }

    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }

}
