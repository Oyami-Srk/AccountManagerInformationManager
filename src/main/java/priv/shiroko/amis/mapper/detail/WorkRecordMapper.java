package priv.shiroko.amis.mapper.detail;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import priv.shiroko.amis.entity.detail.WorkRecord;

import java.util.List;

/**
 * @author Shiroko
 * @description 针对表【work_record】的数据库操作Mapper
 * @createDate 2022-11-05 18:15:02
 * @Entity priv.shiroko.amis.entity.detail.WorkRecord
 */
@Mapper
public interface WorkRecordMapper extends BasicMapper<WorkRecord> {
    public List<WorkRecord> get(@Param("param") WorkRecord param, @Param("last") int last, @Param("count") int count);

    @Select("SELECT * FROM work_record WHERE id = #{id};")
    public WorkRecord getById(@Param("id") int id);

    public int count(@Param("param") WorkRecord param);

    @Select("SELECT EXISTS(SELECT 1 FROM work_record WHERE id = #{id});")
    public boolean hasId(@Param("id") int id);

    @Delete("DELETE FROM work_record WHERE id = #{id};")
    public void deleteById(@Param("id") int id);

    public void update(@Param("param") WorkRecord param);

    public void add(@Param("param") WorkRecord param);
}




