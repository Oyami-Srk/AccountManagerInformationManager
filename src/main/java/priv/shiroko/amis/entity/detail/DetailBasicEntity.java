package priv.shiroko.amis.entity.detail;

import priv.shiroko.amis.entity.BasicEntity;

public interface DetailBasicEntity extends BasicEntity {
    Integer getManagerId();

    Integer getUpdateUser();

    void setUpdateUser(Integer updateUserId);

    void setName(String name);

    void setUpdateUsername(String name);
}
