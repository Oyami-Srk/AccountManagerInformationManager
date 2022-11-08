package priv.shiroko.amis.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import priv.shiroko.amis.entity.Learn;

/**
 * @author Shiroko
 * @description 针对表【learn】的数据库操作Mapper
 * @createDate 2022-11-07 17:49:34
 * @Entity priv.shiroko.amis.entity.Learn
 */
@Mapper
public interface LearnMapper extends BasicMapper<Learn> {
    @Select("SELECT COALESCE(SUM(credit), 0) FROM train_record WHERE manager_id = #{managerId} AND YEAR(date) = YEAR(NOW());")
    public double getYearCreditByManagerId(@Param("managerId") int managerId);

    @Select("SELECT COALESCE(SUM(credit), 0) FROM train_record WHERE manager_id = #{managerId};")
    public double getTotalCreditByManagerId(@Param("managerId") int managerId);
}




