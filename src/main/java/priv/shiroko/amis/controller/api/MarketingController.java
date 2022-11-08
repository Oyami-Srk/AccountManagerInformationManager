package priv.shiroko.amis.controller.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.shiroko.amis.entity.Marketing;
import priv.shiroko.amis.mapper.MarketingMapper;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/api/marketing")
public class MarketingController extends BasicController<Marketing, MarketingMapper> {
    @Resource
    MarketingMapper mapper;

    protected MarketingMapper getMapper() {
        return mapper;
    }

    protected String getEntityName() {
        return "营销记录";
    }
}
