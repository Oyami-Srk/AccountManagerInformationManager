package priv.shiroko.amis.mapper.detail;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import priv.shiroko.amis.entity.detail.ChangeRecord;

import java.util.List;

/**
 * @author Shiroko
 * @description 针对表【change_record】的数据库操作Mapper
 * @createDate 2022-11-05 18:15:01
 * @Entity priv.shiroko.amis.entity.detail.ChangeRecord
 */
@Mapper
public interface ChangeRecordMapper extends BasicMapper<ChangeRecord> {


    public List<ChangeRecord> get(@Param("param") ChangeRecord param, @Param("last") int last, @Param("count") int count);

    @Select("SELECT * FROM change_record WHERE id = #{id};")
    public ChangeRecord getById(@Param("id") int id);

    public int count(@Param("param") ChangeRecord param);

    @Select("SELECT EXISTS(SELECT 1 FROM change_record WHERE id = #{id});")
    public boolean hasId(@Param("id") int id);

    @Delete("DELETE FROM change_record WHERE id = #{id};")
    public void deleteById(@Param("id") int id);

    public void update(@Param("param") ChangeRecord param);

    public void add(@Param("param") ChangeRecord param);

}




