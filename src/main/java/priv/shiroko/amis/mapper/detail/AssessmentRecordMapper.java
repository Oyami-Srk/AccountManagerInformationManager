package priv.shiroko.amis.mapper.detail;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import priv.shiroko.amis.entity.detail.AssessmentRecord;
import priv.shiroko.amis.mapper.BasicMapper;

import java.util.List;

/**
 * @author Shiroko
 * @description 针对表【assessment_record】的数据库操作Mapper
 * @createDate 2022-11-05 18:15:01
 * @Entity priv.shiroko.amis.entity.detail.AssessmentRecord
 */
@Mapper
public interface AssessmentRecordMapper extends BasicMapper<AssessmentRecord> {
    public List<AssessmentRecord> get(@Param("param") AssessmentRecord param, @Param("last") int last, @Param("count") int count);

    @Select("SELECT * FROM assessment_record WHERE id = #{id};")
    public AssessmentRecord getById(@Param("id") int id);

    public int count(@Param("param") AssessmentRecord param);

    @Select("SELECT EXISTS(SELECT 1 FROM assessment_record WHERE id = #{id});")
    public boolean hasId(@Param("id") int id);

    @Delete("DELETE FROM assessment_record WHERE id = #{id};")
    public void deleteById(@Param("id") int id);

    public void update(@Param("param") AssessmentRecord param);

    public void add(@Param("param") AssessmentRecord param);
}




