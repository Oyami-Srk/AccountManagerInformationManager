package priv.shiroko.amis.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.context.annotation.Bean;
import org.springframework.lang.Nullable;
import priv.shiroko.amis.entity.User;

/**
* @author Shiroko
* @description 针对表【user】的数据库操作Mapper
* @createDate 2022-10-31 19:27:59
* @Entity priv.shiroko.amis.entity.User
*/
@Mapper
public interface UserMapper {
    @Select("SELECT EXISTS(SELECT 1 FROM user WHERE role = 'admin' LIMIT 1);")
    public boolean hasAdminAccount();

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
}




