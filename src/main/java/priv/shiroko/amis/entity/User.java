package priv.shiroko.amis.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

/**
 * @TableName user
 */
@Getter
@Setter
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

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        User other = (User) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getUsername() == null ? other.getUsername() == null : this.getUsername().equals(other.getUsername()))
                && (this.getPasswordQuestion() == null ? other.getPasswordQuestion() == null : this.getPasswordQuestion().equals(other.getPasswordQuestion()))
                && (this.getPasswordAnswer() == null ? other.getPasswordAnswer() == null : this.getPasswordAnswer().equals(other.getPasswordAnswer()))
                && (this.getIcNum() == null ? other.getIcNum() == null : this.getIcNum().equals(other.getIcNum()))
                && (this.getNickname() == null ? other.getNickname() == null : this.getNickname().equals(other.getNickname()))
                && (this.getEmail() == null ? other.getEmail() == null : this.getEmail().equals(other.getEmail()))
                && (this.getRegistrationTime() == null ? other.getRegistrationTime() == null : this.getRegistrationTime().equals(other.getRegistrationTime()))
                && (this.getLastLoginTime() == null ? other.getLastLoginTime() == null : this.getLastLoginTime().equals(other.getLastLoginTime()))
                && (this.isEnabled() == other.isEnabled())
                && (this.getRole() == null ? other.getRole() == null : this.getRole().equals(other.getRole()))
                && (Arrays.equals(this.getPassword(), other.getPassword()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUsername() == null) ? 0 : getUsername().hashCode());
        result = prime * result + ((getPasswordQuestion() == null) ? 0 : getPasswordQuestion().hashCode());
        result = prime * result + ((getPasswordAnswer() == null) ? 0 : getPasswordAnswer().hashCode());
        result = prime * result + ((getIcNum() == null) ? 0 : getIcNum().hashCode());
        result = prime * result + ((getNickname() == null) ? 0 : getNickname().hashCode());
        result = prime * result + ((getEmail() == null) ? 0 : getEmail().hashCode());
        result = prime * result + ((getRegistrationTime() == null) ? 0 : getRegistrationTime().hashCode());
        result = prime * result + ((getLastLoginTime() == null) ? 0 : getLastLoginTime().hashCode());
        result = prime * result + (isEnabled() ? 0 : 1);
        result = prime * result + ((getRole() == null) ? 0 : getRole().hashCode());
        result = prime * result + (Arrays.hashCode(getPassword()));
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", username=").append(username);
        sb.append(", passwordQuestion=").append(passwordQuestion);
        sb.append(", passwordAnswer=").append(passwordAnswer);
        sb.append(", icNum=").append(icNum);
        sb.append(", nickname=").append(nickname);
        sb.append(", email=").append(email);
        sb.append(", registrationTime=").append(registrationTime);
        sb.append(", lastLoginTime=").append(lastLoginTime);
        sb.append(", enabled=").append(enabled);
        sb.append(", role=").append(role);
        sb.append(", password=").append(Arrays.toString(password));
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

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