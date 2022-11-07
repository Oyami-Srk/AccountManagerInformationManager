package priv.shiroko.amis.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.lang.Nullable;
import priv.shiroko.amis.entity.Manager;

import java.util.List;
import java.util.Map;

/**
 * @author Shiroko
 * @description 针对表【manager】的数据库操作Mapper
 * @createDate 2022-11-03 11:32:08
 * @Entity priv.shiroko.amis.entity.Manager
 */
@Mapper
public interface ManagerMapper {

    @Select("SELECT EXISTS(SELECT 1 FROM manager WHERE id = #{id} LIMIT 1);")
    public boolean hasManagerWithId(int id);

    @Nullable
    @Select("SELECT * FROM manager WHERE ic_num = #{idcard};")
    public Manager getManagerByIdcard(String idcard);

    @Nullable
    @Select("SELECT * FROM manager WHERE id = #{id};")
    public Manager getManagerById(int id);

    public List<Manager> getManagersBy(@Param("params") Map<String, Object> params, @Param("last") int last, @Param("count") int count);

    public int getManagersCountBy(@Param("params") Map<String, Object> params);

    @Select("SELECT * FROM manager;")
    public List<Manager> getAllManagers();

    @Select("SELECT COUNT(*) FROM manager;")
    public int getManagersCount();

    @Delete("DELETE FROM manager WHERE id = #{id};")
    public void deleteManagerById(int id);

    public void insertManager(Manager manager);

    public void updateManager(Manager manager);
}




