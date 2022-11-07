package priv.shiroko.amis.controller.api.detail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.shiroko.amis.entity.detail.AnnualPerformance;
import priv.shiroko.amis.mapper.detail.AnnualPerformanceMapper;
import priv.shiroko.amis.utils.exception.AlreadyExistsException;
import priv.shiroko.amis.utils.exception.NotFoundException;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/manager/detail/annual_performance")
@Slf4j
public class AnnualPerformanceController extends BasicController<AnnualPerformance, AnnualPerformanceMapper> {
    @Resource
    AnnualPerformanceMapper mapper;

    @Override
    AnnualPerformanceMapper getMapper() {
        return mapper;
    }

    protected NotFoundException getNotFoundException() {
        return new NotFoundException("年度报告未找到。");
    }

    protected AlreadyExistsException getAlreadyExistsException() {
        return new AlreadyExistsException("年度报告已存在。");
    }
}
