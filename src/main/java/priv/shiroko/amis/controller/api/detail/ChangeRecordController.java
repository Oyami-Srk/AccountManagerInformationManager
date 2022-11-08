package priv.shiroko.amis.controller.api.detail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.shiroko.amis.entity.detail.ChangeRecord;
import priv.shiroko.amis.mapper.detail.ChangeRecordMapper;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/manager/detail/change_record")
@Slf4j
public class ChangeRecordController extends DetailBasicController<ChangeRecord, ChangeRecordMapper> {
    @Resource
    ChangeRecordMapper mapper;

    @Override
    protected ChangeRecordMapper getMapper() {
        return mapper;
    }

    protected String getEntityName() {
        return "变动记录";
    }
}
