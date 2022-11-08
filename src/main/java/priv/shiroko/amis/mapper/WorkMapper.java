package priv.shiroko.amis.mapper;

import org.apache.ibatis.annotations.Mapper;
import priv.shiroko.amis.entity.Work;

/**
 * @author Shiroko
 * @description 针对表【work】的数据库操作Mapper
 * @createDate 2022-11-07 17:49:34
 * @Entity priv.shiroko.amis.entity.Work
 */
@Mapper
public interface WorkMapper extends BasicMapper<Work> {

}




