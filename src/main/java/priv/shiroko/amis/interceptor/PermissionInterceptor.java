package priv.shiroko.amis.interceptor;

import com.fasterxml.jackson.databind.MappingJsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.servlet.HandlerInterceptor;
import priv.shiroko.amis.entity.User;
import priv.shiroko.amis.utils.ApiResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

public class PermissionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user.getRole() == User.Role.USER) {
            response.setContentType("application/json;charset=UTF-8");
            ObjectMapper objectMapper = new ObjectMapper(new MappingJsonFactory());
            PrintWriter write = response.getWriter();
            ApiResult result = new ApiResult(ApiResult.Status.FAILED);
            result.setMessage("权限不足！该操作需要管理员权限。");
            result.setReason("Permission denied");
            objectMapper.writeValue(write, result);
            write.flush();
            return false;
        }
        return true;
    }
}
