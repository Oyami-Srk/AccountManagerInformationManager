package priv.shiroko.amis.entity.detail;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName annual_performance
 */
@Data
public class AnnualPerformance implements Serializable, DetailBasicEntity {
    private String updateUsername;

    private String name;

    private Integer id;

    private Integer managerId;

    private Integer year;

    private String performance;

    private String attachment;

    private Date lastUpdate;

    private Integer updateUser;

    private static final long serialVersionUID = 1L;
}