package priv.shiroko.amis.controller.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.shiroko.amis.entity.Client;
import priv.shiroko.amis.mapper.ClientMapper;
import priv.shiroko.amis.mapper.ManagerMapper;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/api/client")
public class ClientController extends BasicController<Client, ClientMapper> {
    @Resource
    ClientMapper mapper;
    @Resource
    ManagerMapper managerMapper;

    protected ClientMapper getMapper() {
        return mapper;
    }

    protected String getEntityName() {
        return "客户信息";
    }

    @Override
    protected void updateObjectAfterGet(Client o) {
        o.setManagerName(managerMapper.getById(o.getManagerId()).getName());
    }
}
