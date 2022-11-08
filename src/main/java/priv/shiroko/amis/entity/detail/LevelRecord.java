package priv.shiroko.amis.entity.detail;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName level_record
 */
@Data
public class LevelRecord implements Serializable, DetailBasicEntity {
    private String updateUsername;

    private String name;

    private Integer id;

    private Integer managerId;

    private Date date;

    private String level;

    private String type;

    private String attachment;

    private Date lastUpdate;

    private Integer updateUser;

    private static final long serialVersionUID = 1L;
}