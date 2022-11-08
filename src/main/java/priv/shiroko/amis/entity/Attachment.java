package priv.shiroko.amis.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

/**
 * @TableName attachment
 */
@Data
public class Attachment implements Serializable, BasicEntity {
    private Integer id;

    private String uuid;

    private String filename;

    private static final long serialVersionUID = 1L;

    public Attachment(UUID uuid) {
        this.uuid = uuid.toString();
    }

    public Attachment(UUID uuid, String filename) {
        this.uuid = uuid.toString();
        this.filename = filename;
    }

    public Attachment() {
    }
}