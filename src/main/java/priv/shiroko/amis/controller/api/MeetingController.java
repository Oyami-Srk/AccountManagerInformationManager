package priv.shiroko.amis.controller.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.shiroko.amis.entity.Meeting;
import priv.shiroko.amis.mapper.MeetingMapper;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/api/meeting")
public class MeetingController extends BasicController<Meeting, MeetingMapper> {
    @Resource
    MeetingMapper mapper;

    protected MeetingMapper getMapper() {
        return mapper;
    }

    protected String getEntityName() {
        return "例会记录";
    }
}
