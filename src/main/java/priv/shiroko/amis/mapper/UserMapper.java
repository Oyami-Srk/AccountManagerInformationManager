package priv.shiroko.amis.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.lang.Nullable;
import priv.shiroko.amis.entity.User;

import java.util.List;

/**
 * @author Shiroko
 * @description 针对表【user】的数据库操作Mapper
 * @createDate 2022-10-31 19:27:59
 * @Entity priv.shiroko.amis.entity.User
 */
@Mapper
public interface UserMapper {
    @Select("SELECT EXISTS(SELECT 1 FROM user WHERE role = 'admin' LIMIT 1);")
    public boolean hasAdminUser();

    public boolean hasUsersWithNameAndId(@Nullable @Param("username") String username, @Nullable @Param("id") Integer id);

    public void createNewUser(User user);

    @Nullable
    @Select("SELECT * FROM user WHERE username = #{username};")
    public User getUserByUsername(String username);

    @Nullable
    @Select("SELECT * FROM user WHERE ic_num = #{idcard};")
    public User getUserByIdcard(String idcard);

    @Nullable
    @Select("SELECT * FROM user WHERE id = #{id};")
    public User getUserById(int id);

    @Select("SELECT * FROM user LIMIT #{last},#{count};")
    public List<User> getUsers(@Param("last") int last, @Param("count") int count);

    @Select("SELECT * FROM user;")
    public List<User> getAllUsers();

    @Select("SELECT COUNT(*) FROM user;")
    public int getUsersCount();

    @Update("UPDATE user SET last_login_time = NOW() WHERE id = #{userId};")
    public void updateLastLogin(int userId);

    @Update("UPDATE user SET enabled = #{status} WHERE id = #{userId};")
    public void updateEnabled(@Param("userId") int userId, @Param("status") boolean enabled);

    @Update("UPDATE user SET password = #{password} WHERE id = #{userId};")
    public void updatePassword(@Param("userId") int userId, @Param("password") byte[] password);

    public void updateUser(User user);

    @Delete("DELETE FROM user WHERE id = #{id};")
    public void deleteUserById(int id);

    @Select("SELECT COALESCE(nickname, username) FROM user WHERE id = #{id};")
    public String getDispNameById(int id);
}





