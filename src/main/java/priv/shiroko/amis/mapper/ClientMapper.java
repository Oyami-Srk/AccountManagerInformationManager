package priv.shiroko.amis.mapper;

import org.apache.ibatis.annotations.Mapper;
import priv.shiroko.amis.entity.Client;

/**
 * @author Shiroko
 * @description 针对表【client】的数据库操作Mapper
 * @createDate 2022-11-07 17:49:34
 * @Entity priv.shiroko.amis.entity.Client
 */
@Mapper
public interface ClientMapper extends BasicMapper<Client> {
}




