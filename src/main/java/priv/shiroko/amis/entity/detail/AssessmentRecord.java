package priv.shiroko.amis.entity.detail;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName assessment_record
 */
@Data
public class AssessmentRecord implements Serializable, BasicEntity {
    private String updateUsername;

    private String name;

    private Integer id;

    private Integer managerId;

    private Date date;

    private String context;

    private String period;

    private String result;

    private String unit;

    private String evaluation;

    private String attachment;

    private Date lastUpdate;

    private Integer updateUser;

    private static final long serialVersionUID = 1L;
}