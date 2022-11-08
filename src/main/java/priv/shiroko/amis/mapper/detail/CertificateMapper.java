package priv.shiroko.amis.mapper.detail;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import priv.shiroko.amis.entity.detail.Certificate;
import priv.shiroko.amis.mapper.BasicMapper;

import java.util.List;

/**
 * @author Shiroko
 * @description 针对表【certificate】的数据库操作Mapper
 * @createDate 2022-11-05 18:15:01
 * @Entity priv.shiroko.amis.entity.detail.Certificate
 */
@Mapper
public interface CertificateMapper extends BasicMapper<Certificate> {

    public List<Certificate> get(@Param("param") Certificate param, @Param("last") int last, @Param("count") int count);

    @Select("SELECT * FROM certificate WHERE id = #{id};")
    public Certificate getById(@Param("id") int id);

    public int count(@Param("param") Certificate param);

    @Select("SELECT EXISTS(SELECT 1 FROM certificate WHERE id = #{id});")
    public boolean hasId(@Param("id") int id);

    @Delete("DELETE FROM certificate WHERE id = #{id};")
    public void deleteById(@Param("id") int id);

    public void update(@Param("param") Certificate param);

    public void add(@Param("param") Certificate param);
}




