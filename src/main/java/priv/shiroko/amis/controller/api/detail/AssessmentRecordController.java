package priv.shiroko.amis.controller.api.detail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.shiroko.amis.entity.detail.AssessmentRecord;
import priv.shiroko.amis.mapper.detail.AssessmentRecordMapper;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/manager/detail/assessment_record")
@Slf4j
public class AssessmentRecordController extends DetailBasicController<AssessmentRecord, AssessmentRecordMapper> {
    @Resource
    AssessmentRecordMapper mapper;

    @Override
    protected AssessmentRecordMapper getMapper() {
        return mapper;
    }

    protected String getEntityName() {
        return "考核记录";
    }
}
