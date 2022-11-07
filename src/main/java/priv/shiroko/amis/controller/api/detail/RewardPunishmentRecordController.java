package priv.shiroko.amis.controller.api.detail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.shiroko.amis.entity.detail.RewardPunishmentRecord;
import priv.shiroko.amis.mapper.detail.RewardPunishmentRecordMapper;
import priv.shiroko.amis.utils.exception.AlreadyExistsException;
import priv.shiroko.amis.utils.exception.NotFoundException;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/manager/detail/reward_punishment_record")
@Slf4j
public class RewardPunishmentRecordController extends BasicController<RewardPunishmentRecord, RewardPunishmentRecordMapper> {
    @Resource
    RewardPunishmentRecordMapper mapper;

    @Override
    RewardPunishmentRecordMapper getMapper() {
        return mapper;
    }

    protected NotFoundException getNotFoundException() {
        return new NotFoundException("奖惩记录未找到。");
    }

    protected AlreadyExistsException getAlreadyExistsException() {
        return new AlreadyExistsException("奖惩记录已存在。");
    }
}
