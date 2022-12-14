package priv.shiroko.amis.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;

import java.util.List;

public interface BasicMapper<DO> {
    public List<DO> get(@Param("param") DO param, @Param("last") Integer last, @Param("count") Integer count);

    public DO getById(@Param("id") int id);

    public int count(@Param("param") DO param);

    public boolean hasId(@Param("id") int id);

    public void deleteById(@Param("id") int id) throws DataIntegrityViolationException;

    public void update(@Param("param") DO param) throws DataIntegrityViolationException, DuplicateKeyException;

    public void add(@Param("param") DO param) throws DuplicateKeyException, DataIntegrityViolationException;
}
