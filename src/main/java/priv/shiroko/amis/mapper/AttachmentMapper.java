package priv.shiroko.amis.mapper;

import org.apache.ibatis.annotations.Mapper;
import priv.shiroko.amis.entity.Attachment;

/**
 * @author Shiroko
 * @description 针对表【attachment】的数据库操作Mapper
 * @createDate 2022-11-06 00:06:54
 * @Entity priv.shiroko.amis.entity.Attachment
 */
@Mapper
public interface AttachmentMapper {
    public void createNewFile(Attachment attachment);

    public String getFilename(String uuid);

}




