package priv.shiroko.amis.controller.api.detail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.shiroko.amis.entity.detail.LevelRecord;
import priv.shiroko.amis.mapper.detail.LevelRecordMapper;
import priv.shiroko.amis.utils.exception.AlreadyExistsException;
import priv.shiroko.amis.utils.exception.NotFoundException;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/manager/detail/level_record")
@Slf4j
public class LevelRecordController extends BasicController<LevelRecord, LevelRecordMapper> {
    @Resource
    LevelRecordMapper mapper;

    @Override
    LevelRecordMapper getMapper() {
        return mapper;
    }

    protected NotFoundException getNotFoundException() {
        return new NotFoundException("等级认定记录未找到。");
    }

    protected AlreadyExistsException getAlreadyExistsException() {
        return new AlreadyExistsException("等级认定记录已存在。");
    }
}
