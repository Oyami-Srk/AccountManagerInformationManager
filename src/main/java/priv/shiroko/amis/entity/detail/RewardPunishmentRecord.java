package priv.shiroko.amis.entity.detail;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName reward_punishment_record
 */
@Data
public class RewardPunishmentRecord implements Serializable, BasicEntity {
    private String updateUsername;

    private String name;

    private Integer id;

    private Integer managerId;

    private Date date;

    private String type;

    private String context;

    private String unit;

    private String person;

    private Date withdrawDate;

    private String withdrawReason;

    private String attachment;

    private Date lastUpdate;

    private Integer updateUser;

    private static final long serialVersionUID = 1L;
}