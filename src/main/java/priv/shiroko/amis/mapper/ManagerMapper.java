package priv.shiroko.amis.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.lang.Nullable;
import priv.shiroko.amis.entity.Manager;

/**
 * @author Shiroko
 * @description 针对表【manager】的数据库操作Mapper
 * @createDate 2022-11-03 11:32:08
 * @Entity priv.shiroko.amis.entity.Manager
 */
@Mapper
public interface ManagerMapper extends BasicMapper<Manager> {
    @Nullable
    @Select("SELECT * FROM manager WHERE ic_num = #{idcard};")
    @ResultMap("BaseResultMap")
    public Manager getManagerByIdcard(String idcard);
}




