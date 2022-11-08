package priv.shiroko.amis.controller.api.detail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.shiroko.amis.entity.detail.RewardPunishmentRecord;
import priv.shiroko.amis.mapper.detail.RewardPunishmentRecordMapper;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/manager/detail/reward_punishment_record")
@Slf4j
public class RewardPunishmentRecordController extends DetailBasicController<RewardPunishmentRecord, RewardPunishmentRecordMapper> {
    @Resource
    RewardPunishmentRecordMapper mapper;

    @Override
    protected RewardPunishmentRecordMapper getMapper() {
        return mapper;
    }

    protected String getEntityName() {
        return "奖惩记录";
    }
}
