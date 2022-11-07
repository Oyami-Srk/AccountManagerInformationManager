package priv.shiroko.amis.utils.exception;

import priv.shiroko.amis.utils.ApiResult;

public class ApiException extends Exception {
    private String reason = null;

    public ApiException(String message, String reason) {
        super(message);
        this.reason = reason;
    }

    public ApiException(String message) {
        super(message);
    }

    public ApiResult toApiResult() {
        ApiResult result = new ApiResult(ApiResult.Status.FAILED);
        result.setMessage(this.getMessage());
        result.setReason(this.reason);
        return result;
    }
}
