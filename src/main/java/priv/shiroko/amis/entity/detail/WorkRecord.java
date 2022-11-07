package priv.shiroko.amis.entity.detail;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName work_record
 */
@Data
public class WorkRecord implements Serializable, BasicEntity {
    private String updateUsername;

    private String name;

    private Integer id;

    private Integer managerId;

    private Date officeDate;

    private String position;

    private String unit;

    private String workDetail;

    private String attachment;

    private Date lastUpdate;

    private Integer updateUser;

    private static final long serialVersionUID = 1L;
}