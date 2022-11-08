package priv.shiroko.amis.controller.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import priv.shiroko.amis.entity.Learn;
import priv.shiroko.amis.entity.User;
import priv.shiroko.amis.mapper.LearnMapper;
import priv.shiroko.amis.mapper.UserMapper;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@RequestMapping("/api/learn")
public class LearnController extends BasicController<Learn, LearnMapper> {
    @Resource
    LearnMapper mapper;

    @Resource
    UserMapper userMapper;

    protected LearnMapper getMapper() {
        return mapper;
    }

    protected String getEntityName() {
        return "学习信息记录";
    }

    @Override
    protected void updateObjectAfterGet(Learn o) {
        o.setUploaderName(userMapper.getDispNameById(o.getUploader()));
    }

    @Override
    protected void updateObjectBeforeUpdate(Learn o) {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession();
        assert session != null;
        o.setUploader(((User) session.getAttribute("user")).getId());
        System.out.println(o);
    }
}
