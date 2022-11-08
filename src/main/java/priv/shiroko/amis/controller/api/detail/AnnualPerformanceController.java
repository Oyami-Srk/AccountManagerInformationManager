package priv.shiroko.amis.controller.api.detail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.shiroko.amis.entity.detail.AnnualPerformance;
import priv.shiroko.amis.mapper.detail.AnnualPerformanceMapper;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/manager/detail/annual_performance")
@Slf4j
public class AnnualPerformanceController extends DetailBasicController<AnnualPerformance, AnnualPerformanceMapper> {
    @Resource
    AnnualPerformanceMapper mapper;

    @Override
    protected AnnualPerformanceMapper getMapper() {
        return mapper;
    }

    protected String getEntityName() {
        return "年度工作业绩";
    }
}
