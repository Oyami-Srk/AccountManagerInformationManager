package priv.shiroko.amis.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName meeting
 */
@Data
public class Meeting implements Serializable, BasicEntity {
    private Integer id;

    private Date date;

    private String context;

    private String participants;

    private String attachment;

    private static final long serialVersionUID = 1L;
}