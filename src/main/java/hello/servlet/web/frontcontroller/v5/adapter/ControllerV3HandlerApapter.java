package hello.servlet.web.frontcontroller.v5.adapter;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.v3.ControllerV3;
import hello.servlet.web.frontcontroller.v5.MyHandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ControllerV3HandlerApapter implements MyHandlerAdapter {

    // 어댑터에서 지원하는 핸들러(컨트롤러) 인지 확인
    @Override
    public boolean supports(Object handler) {
        return (handler instanceof ControllerV3);
    }

    @Override
    public ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {

        ControllerV3 controller = (ControllerV3) handler;
        Map<String, String> paramMap = createParamMap(request);
        ModelView mv = controller.process(paramMap);

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
