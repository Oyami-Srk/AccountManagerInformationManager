package priv.shiroko.amis.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName work
 */
@Data
public class Work implements Serializable, BasicEntity {
    private Integer id;

    private Date date;

    private String client;

    private String info;

    private String after;

    private String risk;

    private String problem;

    private String suggestion;

    private static final long serialVersionUID = 1L;
}