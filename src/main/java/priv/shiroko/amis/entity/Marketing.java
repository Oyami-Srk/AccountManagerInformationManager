package priv.shiroko.amis.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName marketing
 */
@Data
public class Marketing implements Serializable, BasicEntity {
    private Integer id;

    private String name;

    private String status;

    private String requirement;

    private String report;

    private String recommend;

    private String evaluation;

    private Date date;

    private static final long serialVersionUID = 1L;
}