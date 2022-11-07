package priv.shiroko.amis.mapper.detail;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import priv.shiroko.amis.entity.detail.AnnualPerformance;

import java.util.List;

/**
 * @author Shiroko
 * @description 针对表【annual_performance】的数据库操作Mapper
 * @createDate 2022-11-05 18:15:01
 * @Entity priv.shiroko.amis.entity.detail.AnnualPerformance
 */

@Mapper
public interface AnnualPerformanceMapper extends BasicMapper<AnnualPerformance> {
    public List<AnnualPerformance> get(@Param("param") AnnualPerformance param, @Param("last") int last, @Param("count") int count);

    @Select("SELECT * FROM annual_performance WHERE id = #{id};")
    public AnnualPerformance getById(@Param("id") int id);

    public int count(@Param("param") AnnualPerformance param);

    @Select("SELECT EXISTS(SELECT 1 FROM annual_performance WHERE id = #{id});")
    public boolean hasId(@Param("id") int id);

    @Delete("DELETE FROM annual_performance WHERE id = #{id};")
    public void deleteById(@Param("id") int id);

    public void update(@Param("param") AnnualPerformance param);

    public void add(@Param("param") AnnualPerformance param);
}




