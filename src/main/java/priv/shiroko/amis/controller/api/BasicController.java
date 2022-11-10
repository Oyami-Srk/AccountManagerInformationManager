package priv.shiroko.amis.controller.api;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import priv.shiroko.amis.entity.BasicEntity;
import priv.shiroko.amis.mapper.BasicMapper;
import priv.shiroko.amis.utils.ApiResult;
import priv.shiroko.amis.utils.UpdateObject;
import priv.shiroko.amis.utils.exception.AlreadyExistsException;
import priv.shiroko.amis.utils.exception.NotFoundException;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

public abstract class BasicController<DO extends BasicEntity, MO extends BasicMapper<DO>> {
    protected abstract MO getMapper();

    protected NotFoundException getNotFoundException() {
        return new NotFoundException(getEntityName() + "未找到。");
    }

    protected AlreadyExistsException getAlreadyExistsException() {
        return new AlreadyExistsException(getEntityName() + "已存在。");
    }

    protected void updateObjectAfterGet(DO object) {
    }

    protected void updateObjectBeforeUpdate(DO object) {
    }

    protected abstract String getEntityName();

    protected String getGetSuccessMessage() {
        return getEntityName() + "查询成功。";
    }

    protected String getUpdateSuccessMessage() {
        return getEntityName() + "修改成功。";
    }

    protected String getAddSuccessMessage() {
        return getEntityName() + "添加成功。";
    }

    @GetMapping("/get")
    public ApiResult get(
            DO object,
            @RequestParam("last") Optional<Integer> last,
            @RequestParam("count") Optional<Integer> count) throws NotFoundException {
        MO mapper = getMapper();
        ApiResult result = new ApiResult();
        List<DO> objects = mapper.get(object, last.orElse(0), count.orElse(5));
        for (DO o : objects) {
            updateObjectAfterGet(o);
        }
        result.setMessage(getGetSuccessMessage());
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
            try {
                mapper.deleteById(id);
            } catch (Exception ignore) {
                result.setStatus(ApiResult.Status.FAILED);
                result.setMessage("删除" + getEntityName() + "记录失败！\n该" + getEntityName() + "记录与其他信息有关联。");
                return result;
            }
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
            updateObjectBeforeUpdate(object);
            mapper.update(origin);
            result.setMessage(getUpdateSuccessMessage());
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
            updateObjectBeforeUpdate(object);
            try {
                mapper.add(object);
            } catch (DuplicateKeyException ignore) {
                result.setStatus(ApiResult.Status.FAILED);
                result.setMessage("无法添加" + getEntityName() + "，有重复的条目。");
                return result;
            } catch (DataIntegrityViolationException ignore) {
                result.setStatus(ApiResult.Status.FAILED);
                result.setMessage("无法添加" + getEntityName() + "，数据校验错误，请检查数据。");
                return result;
            }
            result.setStatus(ApiResult.Status.OK);
            result.setMessage(getAddSuccessMessage());
        }
        return result;
    }
}
