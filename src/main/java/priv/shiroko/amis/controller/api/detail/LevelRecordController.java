package priv.shiroko.amis.controller.api.detail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.shiroko.amis.entity.detail.LevelRecord;
import priv.shiroko.amis.mapper.detail.LevelRecordMapper;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/manager/detail/level_record")
@Slf4j
public class LevelRecordController extends DetailBasicController<LevelRecord, LevelRecordMapper> {
    @Resource
    LevelRecordMapper mapper;

    protected String getEntityName() {
        return "等级认定记录";
    }

    @Override
    protected LevelRecordMapper getMapper() {
        return mapper;
    }
}
