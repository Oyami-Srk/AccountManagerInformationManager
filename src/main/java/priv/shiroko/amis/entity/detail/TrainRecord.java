package priv.shiroko.amis.entity.detail;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName train_record
 */
@Data
public class TrainRecord implements Serializable, DetailBasicEntity {
    private String updateUsername;

    private String name;

    private Integer id;

    private Integer managerId;

    private Date date;

    private String subject;

    private String unit;

    private Date startDate;

    private Date endDate;

    private Integer hours;

    private Double credit;

    private Double score;

    private String attachment;

    private Date lastUpdate;

    private Integer updateUser;

    private static final long serialVersionUID = 1L;
}