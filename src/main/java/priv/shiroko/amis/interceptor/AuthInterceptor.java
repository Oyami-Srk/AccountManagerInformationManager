package priv.shiroko.amis.interceptor;

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
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter writer = response.getWriter();
            ApiResult result = new ApiResult(ApiResult.Status.Failed);
            result.setMessage("Need login.");
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(writer, result);
            writer.flush();
            return false;
        }
        return true;
    }
}
