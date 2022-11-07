package priv.shiroko.amis.controller.api.detail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.shiroko.amis.entity.detail.TrainRecord;
import priv.shiroko.amis.mapper.detail.TrainRecordMapper;
import priv.shiroko.amis.utils.exception.AlreadyExistsException;
import priv.shiroko.amis.utils.exception.NotFoundException;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/manager/detail/train_record")
@Slf4j
public class TrainRecordController extends BasicController<TrainRecord, TrainRecordMapper> {
    @Resource
    private TrainRecordMapper mapper;

    protected NotFoundException getNotFoundException() {
        return new NotFoundException("学习培训记录未找到。");
    }

    protected AlreadyExistsException getAlreadyExistsException() {
        return new AlreadyExistsException("学习培训记录已存在。");
    }

    protected TrainRecordMapper getMapper() {
        return mapper;
    }

}
