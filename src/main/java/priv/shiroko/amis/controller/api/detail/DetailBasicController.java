package priv.shiroko.amis.controller.api.detail;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import priv.shiroko.amis.controller.api.BasicController;
import priv.shiroko.amis.entity.User;
import priv.shiroko.amis.entity.detail.DetailBasicEntity;
import priv.shiroko.amis.mapper.BasicMapper;
import priv.shiroko.amis.mapper.ManagerMapper;
import priv.shiroko.amis.mapper.UserMapper;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

public abstract class DetailBasicController<DO extends DetailBasicEntity, MO extends BasicMapper<DO>> extends BasicController<DO, MO> {
    @Resource
    ManagerMapper managerMapper;
    @Resource
    UserMapper userMapper;

    protected void updateObjectAfterGet(DO o) {
        String name = managerMapper.getById(o.getManagerId()).getName();
        String updateUsername = userMapper.getDispNameById(o.getUpdateUser());
        o.setName(name);
        o.setUpdateUsername(updateUsername);
    }

    protected void updateObjectBeforeUpdate(DO o) {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession();
        assert session != null;
        o.setUpdateUser(((User) session.getAttribute("user")).getId());
    }
}
