package priv.shiroko.amis.entity.detail;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName certificate
 */
@Data
public class Certificate implements Serializable, DetailBasicEntity {
    private String updateUsername;

    private String name;

    private Integer id;

    private Integer managerId;

    private String certName;

    private String certId;

    private String type;

    private String issuer;

    private Date issueDate;

    private Date startDate;

    private Date endDate;

    private Integer valid;

    private Integer invalidMark;

    private String attachment;

    private Date lastUpdate;

    private Integer updateUser;

    private static final long serialVersionUID = 1L;
}