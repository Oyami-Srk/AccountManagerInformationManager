package priv.shiroko.amis.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.Getter;
import priv.shiroko.amis.utils.BasicEnum;
import priv.shiroko.amis.utils.JsonEnumNameSerializer;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName user
 */
@Data
public class User implements Serializable, BasicEntity {
    private Integer id;

    private String username;

    @JsonIgnore
    private byte[] password;

    private String passwordQuestion;

    @JsonIgnore
    private String passwordAnswer;

    private String icNum;

    private String nickname;

    private String email;

    private Date registrationTime;

    private Date lastLoginTime;

    @JsonSerialize(using = JsonEnumNameSerializer.class)
    private Enabled enabled;

    @JsonSerialize(using = JsonEnumNameSerializer.class)
    private Role role;

    private static final long serialVersionUID = 1L;

    public User(String username, byte[] password) {
        this.setUsername(username);
        this.setPassword(password);
    }

    @Getter
    public enum Role implements BasicEnum {
        ADMIN("admin", "管理员"),
        USER("user", "用户");
        private final String value;
        private final String name;

        Role(String value, String name) {
            this.value = value;
            this.name = name;
        }
    }

    @Getter
    public enum Enabled implements BasicEnum {
        SUSPENDED("suspended", "停用"),
        ENABLED("enabled", "启用");

        private final String value;
        private final String name;

        Enabled(String value, String name) {
            this.value = value;
            this.name = name;
        }
    }
}