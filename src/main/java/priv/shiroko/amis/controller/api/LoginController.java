package priv.shiroko.amis.controller.api;

import at.favre.lib.crypto.bcrypt.BCrypt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.*;
import priv.shiroko.amis.config.Config;
import priv.shiroko.amis.entity.User;
import priv.shiroko.amis.mapper.UserMapper;
import priv.shiroko.amis.utils.ApiResult;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;


@RestController
@RequestMapping("/api")
@Slf4j
public class LoginController {

    @Resource
    private UserMapper userMapper;
    @Resource
    private Config config;


    @PostMapping("/login")
    public ApiResult doLogin(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session) {
        ApiResult result = new ApiResult();
        if (username == null || password == null) {
            result.setStatus(ApiResult.Status.FAILED);
            result.setMessage("用户名和密码不能为空。");
            result.setReason("null exception");
        } else {
            User user = userMapper.getUserByUsername(username);
            if (user == null)
                user = userMapper.getUserByIdcard(username);
            if (user == null) {
                try {
                    user = userMapper.getById(Integer.parseInt(username));
                } catch (NumberFormatException ignored) {
                }
            }
            if (user == null) {
                result.setStatus(ApiResult.Status.FAILED);
                result.setMessage("用户不存在。");
                result.setReason("user not existed");
            } else {
                if (BCrypt.verifyer().verify(password.getBytes(), user.getPassword()).verified) {
                    if (user.getEnabled() == User.Enabled.SUSPENDED) {
                        result.setStatus(ApiResult.Status.FAILED);
                        result.setMessage("用户被停用。");
                        result.setReason("user disabled");
                    } else {
                        result.setStatus(ApiResult.Status.OK);
                        result.setMessage("登录成功！");
                        session.setAttribute("user", user);
                        userMapper.updateLastLogin(user.getId());
                    }
                } else {
                    result.setStatus(ApiResult.Status.FAILED);
                    result.setMessage("密码错误。");
                    result.setReason("password incorrect");
                }
            }
        }
        return result;
    }

    @GetMapping("/logout")
    public ApiResult doLogout(HttpSession session) {
        session.removeAttribute("user");
        ApiResult result = new ApiResult(ApiResult.Status.OK);
        result.setMessage("登出成功。");
        return result;
    }

    @PostMapping("/change_password")
    public ApiResult changePassword(
            HttpSession session,
            @RequestParam("old_password") String oldPassword,
            @RequestParam("new_password") String newPassword) {
        User user = (User) session.getAttribute("user");
        ApiResult result = new ApiResult();
        if (!BCrypt.verifyer().verify(oldPassword.getBytes(), user.getPassword()).verified) {
            result.setStatus(ApiResult.Status.FAILED);
            result.setMessage("旧密码不正确！");
            return result;
        }
        int cost = config.getBcryptCost();
        byte[] hashedPasswd = BCrypt.withDefaults().hash(cost, newPassword.getBytes());
        userMapper.updatePassword(user.getId(), hashedPasswd);
        result.setMessage("密码修改成功。");
        result.setStatus(ApiResult.Status.OK);
        return result;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void setupFirstUser() {
        if (!userMapper.hasAdminUser()) {
            // Create default admin account if there is none.
            log.info("Create default admin account.");
            int cost = config.getBcryptCost();
            byte[] hashedPasswd = BCrypt.withDefaults().hash(cost, config.getDefaults().getAdminPassword().getBytes());
            User admin = new User(config.getDefaults().getAdminUsername(), hashedPasswd);
            admin.setRole(User.Role.ADMIN);
            admin.setEnabled(User.Enabled.ENABLED);
            userMapper.add(admin);
        }
    }
}
