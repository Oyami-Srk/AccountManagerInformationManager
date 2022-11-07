package priv.shiroko.amis.mapper.detail;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import priv.shiroko.amis.entity.detail.LevelRecord;

import java.util.List;

/**
 * @author Shiroko
 * @description 针对表【level_record】的数据库操作Mapper
 * @createDate 2022-11-05 18:15:01
 * @Entity priv.shiroko.amis.entity.detail.LevelRecord
 */
@Mapper
public interface LevelRecordMapper extends BasicMapper<LevelRecord> {

    public List<LevelRecord> get(@Param("param") LevelRecord param, @Param("last") int last, @Param("count") int count);

    @Select("SELECT * FROM level_record WHERE id = #{id};")
    public LevelRecord getById(@Param("id") int id);

    public int count(@Param("param") LevelRecord param);

    @Select("SELECT EXISTS(SELECT 1 FROM level_record WHERE id = #{id});")
    public boolean hasId(@Param("id") int id);

    @Delete("DELETE FROM level_record WHERE id = #{id};")
    public void deleteById(@Param("id") int id);

    public void update(@Param("param") LevelRecord param);

    public void add(@Param("param") LevelRecord param);

}




