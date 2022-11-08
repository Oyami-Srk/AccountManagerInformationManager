package priv.shiroko.amis.controller.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.shiroko.amis.entity.Work;
import priv.shiroko.amis.mapper.WorkMapper;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/api/work")
public class WorkController extends BasicController<Work, WorkMapper> {
    @Resource
    WorkMapper mapper;

    protected WorkMapper getMapper() {
        return mapper;
    }

    protected String getEntityName() {
        return "工作记录";
    }
}
