package priv.shiroko.amis.controller.api.detail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.shiroko.amis.entity.detail.Certificate;
import priv.shiroko.amis.mapper.detail.CertificateMapper;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/manager/detail/certificate")
@Slf4j
public class CertificateController extends DetailBasicController<Certificate, CertificateMapper> {
    @Resource
    CertificateMapper mapper;

    @Override
    protected CertificateMapper getMapper() {
        return mapper;
    }

    protected String getEntityName() {
        return "证照";
    }
}
