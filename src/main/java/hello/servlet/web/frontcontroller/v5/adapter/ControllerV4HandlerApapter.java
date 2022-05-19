package hello.servlet.web.frontcontroller.v5.adapter;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.v3.ControllerV3;
import hello.servlet.web.frontcontroller.v4.ControllerV4;
import hello.servlet.web.frontcontroller.v5.MyHandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ControllerV4HandlerApapter implements MyHandlerAdapter {

    // 어댑터에서 지원하는 핸들러(컨트롤러) 인지 확인
    @Override
    public boolean supports(Object handler) {
        return (handler instanceof ControllerV4);
    }

    @Override
    public ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {

        ControllerV4 controller = (ControllerV4) handler;
        Map<String, String> paramMap = createParamMap(request);
        Map<String, Object> model = new HashMap<>();

        String viewName = controller.process(paramMap, model);

    /*
        이 부분이 어댑터 다운 역할(110v -> 220v),
        V4는 controller.process의 결과가 String viewName 인데(110v)
        handle는 ModelView를 리턴하기 때문에(220v)
        틀에 맞춰 내보내 주는 것
    */
        ModelView mv = new ModelView(viewName);
        mv.setModel(model);

        return mv;
    }

    // request에 있는 파라미터들을 map으로 반환
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
