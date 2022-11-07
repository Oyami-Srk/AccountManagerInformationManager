package priv.shiroko.amis.controller.api.detail;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import priv.shiroko.amis.entity.User;
import priv.shiroko.amis.entity.detail.BasicEntity;
import priv.shiroko.amis.mapper.ManagerMapper;
import priv.shiroko.amis.mapper.UserMapper;
import priv.shiroko.amis.mapper.detail.BasicMapper;
import priv.shiroko.amis.utils.ApiResult;
import priv.shiroko.amis.utils.UpdateObject;
import priv.shiroko.amis.utils.exception.AlreadyExistsException;
import priv.shiroko.amis.utils.exception.NotFoundException;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

public abstract class BasicController<DO extends BasicEntity, MO extends BasicMapper<DO>> {
    @Resource
    ManagerMapper managerMapper;
    @Resource
    UserMapper userMapper;

    abstract MO getMapper();

    protected NotFoundException getNotFoundException() {
        return new NotFoundException("未找到。");
    }

    protected AlreadyExistsException getAlreadyExistsException() {
        return new AlreadyExistsException("已存在。");
    }

    @GetMapping("/get")
    public ApiResult get(
            DO object,
            @RequestParam("last_id") Optional<Integer> last_id,
            @RequestParam("count") Optional<Integer> count) throws NotFoundException {
        MO mapper = getMapper();
        ApiResult result = new ApiResult();
        List<DO> objects = mapper.get(object, last_id.orElse(0), count.orElse(5));
        for (DO o : objects) {
            String name = managerMapper.getManagerById(o.getManagerId()).getName();
            String updateUsername = userMapper.getDispNameById(o.getUpdateUser());
            o.setName(name);
            o.setUpdateUsername(updateUsername);
        }
        result.setStatus(ApiResult.Status.OK);
        result.setData(objects);
        return result;
    }

    @GetMapping("/count")
    public ApiResult count(DO object) {
        MO mapper = getMapper();
        ApiResult result = new ApiResult();
        result.setStatus(ApiResult.Status.OK);
        result.setData(mapper.count(object));
        return result;
    }

    @PostMapping("/delete")
    public ApiResult delete(@RequestParam("id") int id) throws NotFoundException {
        MO mapper = getMapper();
        ApiResult result = new ApiResult();
        if (!mapper.hasId(id)) {
            throw getNotFoundException();
        } else {
            mapper.deleteById(id);
            result.setStatus(ApiResult.Status.OK);
        }
        return result;
    }

    @PostMapping("/update")
    public ApiResult update(DO object) throws NotFoundException {
        MO mapper = getMapper();
        ApiResult result = new ApiResult();
        if (!mapper.hasId(object.getId())) {
            throw getNotFoundException();
        } else {
            DO origin = mapper.getById(object.getId());
            assert origin != null;
            UpdateObject.update(origin, object);
            mapper.update(origin);
            result.setMessage("更新保存成功！");
            result.setStatus(ApiResult.Status.OK);
        }
        return result;
    }

    @PostMapping("/add")
    public ApiResult add(HttpSession session, DO object) throws AlreadyExistsException {
        MO mapper = getMapper();
        ApiResult result = new ApiResult();
        if (object.getId() != null && mapper.hasId(object.getId()))
            throw getAlreadyExistsException();
        else {
            object.setUpdateUser(((User) session.getAttribute("user")).getId());
            mapper.add(object);
            result.setStatus(ApiResult.Status.OK);
            result.setMessage("已添加。");
        }
        return result;
    }
}
