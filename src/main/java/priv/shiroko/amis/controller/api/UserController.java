package priv.shiroko.amis.controller.api;

import at.favre.lib.crypto.bcrypt.BCrypt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import priv.shiroko.amis.config.Config;
import priv.shiroko.amis.entity.User;
import priv.shiroko.amis.mapper.UserMapper;
import priv.shiroko.amis.utils.ApiResult;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController extends BasicController<User, UserMapper> {
    @Resource
    private UserMapper userMapper;
    @Resource
    private Config config;

    protected UserMapper getMapper() {
        return userMapper;
    }

    protected void updateObjectAfterGet(User user) {
    }

    protected void updateObjectBeforeUpdate(User user) {
        int cost = config.getBcryptCost();
        byte[] hashedPasswd = BCrypt.withDefaults().hash(cost, user.getPassword());
        user.setPassword(hashedPasswd);
    }

    protected String getEntityName() {
        return "用户";
    }


    @PostMapping("/set_status")
    public ApiResult setStatus(
            @RequestParam("id") int id,
            @RequestParam("enabled") boolean status) {
        ApiResult result = new ApiResult();
        if (userMapper.hasUsersWithNameAndId(null, id)) {
            result.setStatus(ApiResult.Status.OK);
            userMapper.updateEnabled(id, status ? User.Enabled.ENABLED : User.Enabled.SUSPENDED);
        } else {
            result.setStatus(ApiResult.Status.FAILED);
            result.setMessage("用户不存在。");
        }
        return result;
    }

    @PostMapping("/reset_password")
    public ApiResult passwdUser(@RequestParam("id") int id) {
        ApiResult result = new ApiResult();
        if (userMapper.hasUsersWithNameAndId(null, id)) {
            int cost = config.getBcryptCost();
            byte[] hashedPasswd = BCrypt.withDefaults().hash(cost, config.getDefaults().getPassword().getBytes());
            userMapper.updatePassword(id, hashedPasswd);
            result.setStatus(ApiResult.Status.OK);
        } else {
            result.setStatus(ApiResult.Status.FAILED);
            result.setMessage("用户不存在。");
        }
        return result;
    }

    @GetMapping("/get_display_name")
    public ApiResult getDispName(@RequestParam("id") Optional<Integer> id, HttpSession session) {
        ApiResult result = new ApiResult();
        if (id.isPresent()) {
            if (userMapper.hasUsersWithNameAndId(null, id.get())) {
                result.setStatus(ApiResult.Status.OK);
                result.setData(userMapper.getDispNameById(id.get()));
            } else {
                result.setStatus(ApiResult.Status.FAILED);
                result.setMessage("用户不存在。");
            }
        } else {
            result.setStatus(ApiResult.Status.OK);
            result.setData(userMapper.getDispNameById(((User) session.getAttribute("user")).getId()));
        }
        return result;
    }


}
