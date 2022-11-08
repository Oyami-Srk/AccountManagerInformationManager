package priv.shiroko.amis.controller.api.detail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.shiroko.amis.entity.detail.WorkRecord;
import priv.shiroko.amis.mapper.detail.WorkRecordMapper;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/manager/detail/work_record")
@Slf4j
public class WorkRecordController extends DetailBasicController<WorkRecord, WorkRecordMapper> {
    @Resource
    WorkRecordMapper mapper;

    @Override
    protected WorkRecordMapper getMapper() {
        return mapper;
    }

    protected String getEntityName() {
        return "工作经历";
    }

}
