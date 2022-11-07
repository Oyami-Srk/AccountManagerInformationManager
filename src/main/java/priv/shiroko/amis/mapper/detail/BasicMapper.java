package priv.shiroko.amis.mapper.detail;

import java.util.List;

public interface BasicMapper<DO> {
    public List<DO> get(DO param, int last, int count);

    public DO getById(int id);

    public int count(DO param);

    public boolean hasId(int id);

    public void deleteById(int id);

    public void update(DO param);

    public void add(DO param);
}
