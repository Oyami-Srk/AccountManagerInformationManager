package priv.shiroko.amis.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName learn
 */
@Data
public class Learn implements Serializable, BasicEntity {
    private Integer id;

    private String name;

    private String type;

    private String description;

    private Date uploadDate;

    private String attachment;

    private Integer uploader;

    private String uploaderName;

    private static final long serialVersionUID = 1L;
}