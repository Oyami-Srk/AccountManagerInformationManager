package priv.shiroko.amis.controller.api.detail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.shiroko.amis.entity.detail.WorkRecord;
import priv.shiroko.amis.mapper.detail.WorkRecordMapper;
import priv.shiroko.amis.utils.exception.AlreadyExistsException;
import priv.shiroko.amis.utils.exception.NotFoundException;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/manager/detail/work_record")
@Slf4j
public class WorkRecordController extends BasicController<WorkRecord, WorkRecordMapper> {
    @Resource
    WorkRecordMapper mapper;

    @Override
    WorkRecordMapper getMapper() {
        return mapper;
    }

    protected NotFoundException getNotFoundException() {
        return new NotFoundException("工作经历未找到。");
    }

    protected AlreadyExistsException getAlreadyExistsException() {
        return new AlreadyExistsException("工作经历已存在。");
    }
}
