package priv.shiroko.amis.controller.api.detail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.shiroko.amis.entity.detail.AssessmentRecord;
import priv.shiroko.amis.mapper.detail.AssessmentRecordMapper;
import priv.shiroko.amis.utils.exception.AlreadyExistsException;
import priv.shiroko.amis.utils.exception.NotFoundException;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/manager/detail/assessment_record")
@Slf4j
public class AssessmentRecordController extends BasicController<AssessmentRecord, AssessmentRecordMapper> {
    @Resource
    AssessmentRecordMapper mapper;

    @Override
    AssessmentRecordMapper getMapper() {
        return mapper;
    }

    protected NotFoundException getNotFoundException() {
        return new NotFoundException("考核记录未找到。");
    }

    protected AlreadyExistsException getAlreadyExistsException() {
        return new AlreadyExistsException("考核记录已存在。");
    }
}
