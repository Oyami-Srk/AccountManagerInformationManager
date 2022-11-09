package priv.shiroko.amis.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.lang.Nullable;
import priv.shiroko.amis.entity.User;

/**
 * @author Shiroko
 * @description 针对表【user】的数据库操作Mapper
 * @createDate 2022-10-31 19:27:59
 * @Entity priv.shiroko.amis.entity.User
 */
@Mapper
public interface UserMapper extends BasicMapper<User> {
    @Select("SELECT EXISTS(SELECT 1 FROM user WHERE role = 'admin' LIMIT 1);")
    public boolean hasAdminUser();

    public boolean hasUsersWithNameAndId(@Nullable @Param("username") String username, @Nullable @Param("id") Integer id);

    @Nullable
    @Select("SELECT * FROM user WHERE username = #{username};")
    public User getUserByUsername(String username);

    @Nullable
    @Select("SELECT * FROM user WHERE ic_num = #{idcard};")
    public User getUserByIdcard(String idcard);

    @Update("UPDATE user SET last_login_time = NOW() WHERE id = #{userId};")
    public void updateLastLogin(int userId);

    @Update("UPDATE user SET enabled = #{status} WHERE id = #{userId};")
    public void updateEnabled(@Param("userId") int userId, @Param("status") User.Enabled enabled);

    @Update("UPDATE user SET password = #{password} WHERE id = #{userId};")
    public void updatePassword(@Param("userId") int userId, @Param("password") byte[] password);

    @Select("SELECT COALESCE(nickname, username) FROM user WHERE id = #{id};")
    public String getDispNameById(int id);
}





