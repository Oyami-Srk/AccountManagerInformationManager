package priv.shiroko.amis.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName user
 */
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class User implements Serializable {
    private Integer id;

    @NonNull
    private String username;

    @NonNull
    private byte[] password;

    private String passwordQuestion;

    private String passwordAnswer;

    private String icNum;

    private String nickname;

    private String email;

    private Date registrationTime;

    private Date lastLoginTime;

    private boolean enabled;

    private Role role;

    private static final long serialVersionUID = 1L;

    public User(String username, byte[] password) {
        this.setUsername(username);
        this.setPassword(password);
    }

    @Getter
    public enum Role {
        @JsonProperty("admin")
        ADMIN("admin", "管理员"),
        @JsonProperty("user")
        USER("user", "用户");
        private final String value;
        private final String name;

        Role(String value, String name) {
            this.value = value;
            this.name = name;
        }

        @Override
        public String toString() {
            return this.value;
        }
    }
}