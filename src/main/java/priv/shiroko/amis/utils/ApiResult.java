package priv.shiroko.amis.utils;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class ApiResult {
    @NonNull
    Status status = Status.Unknown;
    String message;
    Map<Object, Object> data;

    public ApiResult(Status status) {
        this.status = status;
    }

    public ApiResult() {
    }

    public enum Status {
        OK,
        Failed,
        Unknown
    }
}
