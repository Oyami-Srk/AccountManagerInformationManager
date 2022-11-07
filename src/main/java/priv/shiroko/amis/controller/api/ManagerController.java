package priv.shiroko.amis.controller.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import priv.shiroko.amis.entity.Manager;
import priv.shiroko.amis.mapper.ManagerMapper;
import priv.shiroko.amis.utils.ApiResult;
import priv.shiroko.amis.utils.UpdateObject;
import priv.shiroko.amis.utils.exception.NotFoundException;
import priv.shiroko.amis.view.EasyExcelView;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/api")
@Slf4j
public class ManagerController {
    @Resource
    ManagerMapper managerMapper;

    @GetMapping("/manager/get")
    public ApiResult getManager(
            @RequestParam("dept") Optional<String> dept,
            @RequestParam("id") Optional<Integer> id,
            @RequestParam("name") Optional<String> name,
            @RequestParam("status") Optional<Manager.Status> status,
            @RequestParam("last") Optional<Integer> last,
            @RequestParam("count") Optional<Integer> count
    ) throws NotFoundException {
        ApiResult result = new ApiResult();
        NotFoundException e = new NotFoundException("无此客户经理。");
        Map<String, Object> params = new HashMap<>();
        params.put("dept", dept.orElse(null));
        params.put("id", id.orElse(null));
        params.put("name", name.orElse(null));
        params.put("status", status.orElse(null));
        List<Manager> managers = managerMapper.getManagersBy(params, last.orElse(0), count.orElse(5));
        if (managers.isEmpty())
            throw e;
        result.setStatus(ApiResult.Status.OK);
        result.setData(managers);
        return result;
    }


    @GetMapping("/manager/count_by")
    public ApiResult getManager(
            @RequestParam("dept") Optional<String> dept,
            @RequestParam("id") Optional<Integer> id,
            @RequestParam("name") Optional<String> name,
            @RequestParam("status") Optional<Manager.Status> status) {
        ApiResult result = new ApiResult();
        Map<String, Object> params = new HashMap<>();
        params.put("dept", dept.orElse(null));
        params.put("id", id.orElse(null));
        params.put("name", name.orElse(null));
        params.put("status", status.orElse(null));
        result.setStatus(ApiResult.Status.OK);
        result.setData(managerMapper.getManagersCountBy(params));
        return result;
    }

    @GetMapping("/manager/count")
    public ApiResult getManagerCount() {
        ApiResult result = new ApiResult(ApiResult.Status.OK);
        result.setData(managerMapper.getManagersCount());
        return result;
    }

    @PostMapping("/manager/add")
    public ApiResult addManager(Manager manager) {
        ApiResult result = new ApiResult();
        if (managerMapper.hasManagerWithId(manager.getId())) {
            result.setStatus(ApiResult.Status.FAILED);
            result.setMessage("该客户经理编号已经存在！");
        } else {
            managerMapper.insertManager(manager);
            result.setStatus(ApiResult.Status.OK);
            result.setMessage("添加成功！");
        }
        return result;
    }

    @PostMapping("/manager/update")
    public ApiResult updateManager(Manager manager) throws NotFoundException {
        ApiResult result = new ApiResult();
        System.out.println(manager);
        if (!managerMapper.hasManagerWithId(manager.getId())) {
            throw new NotFoundException("无此客户经理。");
        } else {
            Manager origin = managerMapper.getManagerById(manager.getId());
            assert origin != null;
            UpdateObject.update(origin, manager);
            managerMapper.updateManager(origin);
            result.setMessage("更新保存成功！");
            result.setStatus(ApiResult.Status.OK);
        }
        return result;
    }

    @PostMapping("/manager/delete")
    public ApiResult delManager(@RequestParam("id") int id) {
        ApiResult result = new ApiResult();
        if (managerMapper.hasManagerWithId(id)) {
            managerMapper.deleteManagerById(id);
            result.setStatus(ApiResult.Status.OK);
        } else {
            result.setStatus(ApiResult.Status.FAILED);
            result.setMessage("客户经理不存在。");
        }
        return result;
    }

    @GetMapping("/manager/export")
    public ModelAndView exportManager() {
        ModelAndView mav = new ModelAndView(new EasyExcelView("export-"
                + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())));
        mav.addObject("class", Manager.class);
        mav.addObject("data", managerMapper.getAllManagers());
        return mav;
    }
}
