package priv.shiroko.amis.controller.api.detail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.shiroko.amis.entity.detail.Certificate;
import priv.shiroko.amis.mapper.detail.CertificateMapper;
import priv.shiroko.amis.utils.exception.AlreadyExistsException;
import priv.shiroko.amis.utils.exception.NotFoundException;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/manager/detail/certificate")
@Slf4j
public class CertificateController extends BasicController<Certificate, CertificateMapper> {
    @Resource
    CertificateMapper mapper;

    @Override
    CertificateMapper getMapper() {
        return mapper;
    }

    protected NotFoundException getNotFoundException() {
        return new NotFoundException("证照未找到。");
    }

    protected AlreadyExistsException getAlreadyExistsException() {
        return new AlreadyExistsException("证照已存在。");
    }
}
