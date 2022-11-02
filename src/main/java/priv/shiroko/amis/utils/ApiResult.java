package priv.shiroko.amis.utils;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Setter
@Getter
public class ApiResult {
    @NonNull
    Status status = Status.UNKNOWN;
    String message;
    String reason;
    Object data;

    public ApiResult(Status status) {
        this.status = status;
    }

    public ApiResult() {
    }

    public enum Status {
        OK,
        FAILED,
        UNKNOWN
    }
}
