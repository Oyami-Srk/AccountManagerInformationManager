package priv.shiroko.amis.mapper.detail;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import priv.shiroko.amis.entity.detail.TrainRecord;
import priv.shiroko.amis.mapper.BasicMapper;

import java.util.List;

/**
 * @author Shiroko
 * @description 针对表【train_record】的数据库操作Mapper
 * @createDate 2022-11-05 18:15:01
 * @Entity priv.shiroko.amis.entity.detail.TrainRecord
 */
@Mapper
public interface TrainRecordMapper extends BasicMapper<TrainRecord> {

    public List<TrainRecord> get(@Param("param") TrainRecord param, @Param("last") int last, @Param("count") int count);

    @Select("SELECT * FROM train_record WHERE id = #{id};")
    public TrainRecord getById(@Param("id") int id);

    public int count(@Param("param") TrainRecord param);

    @Select("SELECT EXISTS(SELECT 1 FROM train_record WHERE id = #{id});")
    public boolean hasId(@Param("id") int id);

    @Delete("DELETE FROM train_record WHERE id = #{id};")
    public void deleteById(@Param("id") int id);

    public void update(@Param("param") TrainRecord param);

    public void add(@Param("param") TrainRecord param);

}




