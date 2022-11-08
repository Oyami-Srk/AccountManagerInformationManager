package priv.shiroko.amis.mapper;

import org.apache.ibatis.annotations.Mapper;
import priv.shiroko.amis.entity.Meeting;

/**
 * @author Shiroko
 * @description 针对表【meeting】的数据库操作Mapper
 * @createDate 2022-11-07 19:58:01
 * @Entity priv.shiroko.amis.entity.Meeting
 */
@Mapper
public interface MeetingMapper extends BasicMapper<Meeting> {

}




