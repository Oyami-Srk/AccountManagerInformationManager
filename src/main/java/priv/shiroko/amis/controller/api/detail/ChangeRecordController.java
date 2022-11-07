package priv.shiroko.amis.controller.api.detail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.shiroko.amis.entity.detail.ChangeRecord;
import priv.shiroko.amis.mapper.detail.ChangeRecordMapper;
import priv.shiroko.amis.utils.exception.AlreadyExistsException;
import priv.shiroko.amis.utils.exception.NotFoundException;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/manager/detail/change_record")
@Slf4j
public class ChangeRecordController extends BasicController<ChangeRecord, ChangeRecordMapper> {
    @Resource
    ChangeRecordMapper mapper;

    @Override
    ChangeRecordMapper getMapper() {
        return mapper;
    }

    protected NotFoundException getNotFoundException() {
        return new NotFoundException("人事变动记录未找到。");
    }

    protected AlreadyExistsException getAlreadyExistsException() {
        return new AlreadyExistsException("人事变动记录已存在。");
    }
}
