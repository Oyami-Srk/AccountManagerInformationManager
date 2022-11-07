package priv.shiroko.amis.entity.detail;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName change_record
 */
@Data
public class ChangeRecord implements Serializable, BasicEntity {
    private String updateUsername;

    private String name;

    private Integer id;

    private Integer managerId;

    private Date date;

    private String unitFrom;

    private String unitTo;

    private String deptFrom;

    private String deptTo;

    private String comment;

    private String attachment;

    private Date lastUpdate;

    private Integer updateUser;

    private static final long serialVersionUID = 1L;
}