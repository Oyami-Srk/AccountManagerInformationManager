package priv.shiroko.amis.entity.detail;

public interface BasicEntity {
    public Integer getManagerId();

    public Integer getUpdateUser();

    public Integer getId();

    public void setUpdateUser(Integer updateUserId);

    public void setName(String name);

    public void setUpdateUsername(String name);
}
