package priv.shiroko.amis.interceptor;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import priv.shiroko.amis.utils.ApiResult;
import priv.shiroko.amis.utils.exception.ApiException;

@RestControllerAdvice
public class ApiExceptionInterceptor {
    @ExceptionHandler({ApiException.class})
    public ApiResult apiExceptionHandler(ApiException e) {
        return e.toApiResult();
    }
}
