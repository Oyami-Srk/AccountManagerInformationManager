package priv.shiroko.amis.controller.api.detail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.shiroko.amis.entity.detail.TrainRecord;
import priv.shiroko.amis.mapper.detail.TrainRecordMapper;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/manager/detail/train_record")
@Slf4j
public class TrainRecordController extends DetailBasicController<TrainRecord, TrainRecordMapper> {
    @Resource
    private TrainRecordMapper mapper;

    protected String getEntityName() {
        return "学习培训记录";
    }

    protected TrainRecordMapper getMapper() {
        return mapper;
    }

}
