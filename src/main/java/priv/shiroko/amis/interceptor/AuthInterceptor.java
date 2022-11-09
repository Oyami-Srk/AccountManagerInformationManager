package priv.shiroko.amis.interceptor;

import com.fasterxml.jackson.databind.MappingJsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.servlet.HandlerInterceptor;
import priv.shiroko.amis.utils.ApiResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        if (session.getAttribute("user") == null) {
            if (request.getRequestURI().contains("/api/")) {
                response.setContentType("application/json;charset=UTF-8");
                ObjectMapper objectMapper = new ObjectMapper(new MappingJsonFactory());
                PrintWriter write = response.getWriter();
                ApiResult result = new ApiResult(ApiResult.Status.FAILED);
                result.setMessage("登录失效，请重新登录！");
                result.setReason("Need login");
                objectMapper.writeValue(write, result);
                write.flush();
            } else {
                response.sendRedirect("/login.html");
            }
            return false;
        }
        return true;
    }
}
