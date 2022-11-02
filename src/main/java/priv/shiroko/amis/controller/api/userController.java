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
import priv.shiroko.amis.utils.EntityFilter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@Slf4j
public class userController {
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
                    user = userMapper.getUserById(Integer.parseInt(username));
                } catch (NumberFormatException ignored) {
                }
            }
            if (user == null) {
                result.setStatus(ApiResult.Status.FAILED);
                result.setMessage("用户不存在。");
                result.setReason("user not existed");
            } else {
                if (BCrypt.verifyer().verify(password.getBytes(), user.getPassword()).verified) {
                    if (!user.isEnabled()) {
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

    @GetMapping("/user/get")
    public ApiResult getUser(HttpServletRequest request) {
        ApiResult result = new ApiResult();
        if (request.getParameterMap().containsKey("id") || request.getParameterMap().containsKey("username")) {
            // query single user
            User user = null;
            do {
                // TODO: rewrite use new interface hasUserWithNameAndId
                if (request.getParameter("id") != null) {
                    try {
                        int id = Integer.parseInt(request.getParameter("id"));
                        user = userMapper.getUserById(id);
                        if (user == null) {
                            result.setStatus(ApiResult.Status.FAILED);
                            result.setMessage("用户编号不存在。");
                            result.setReason("id not existed");
                        } else {
                            result.setStatus(ApiResult.Status.OK);
                        }
                    } catch (NumberFormatException ignored) {
                        result.setStatus(ApiResult.Status.FAILED);
                        result.setMessage("用户编号格式错误。");
                        result.setReason("id format error");
                        break;
                    }
                }
                if (request.getParameter("username") != null) {
                    if (user != null) {
                        if (!user.getUsername().equals(request.getParameter("username"))) {
                            result.setStatus(ApiResult.Status.FAILED);
                            result.setMessage("无此用户。");
                            result.setReason("no such user");
                            user = null;
                        }
                    } else {
                        user = userMapper.getUserByUsername(request.getParameter("username"));
                        if (user == null) {
                            result.setStatus(ApiResult.Status.FAILED);
                            result.setMessage("用户名不存在。");
                            result.setReason("username not existed");
                        }
                    }
                }
            } while (false);
            if (user != null) {
                result.setStatus(ApiResult.Status.OK);
                result.setData(EntityFilter.filter(user, new String[]{
                        "password",
                        "password_answer"
                }));
            }
        } else {
            // query users
            result.setStatus(ApiResult.Status.OK);
            List<User> users = null;
            if (request.getParameterMap().containsKey("last")) {
                try {
                    int last = Integer.parseInt(request.getParameter("last"));
                    int count = 5;
                    try {
                        count = Integer.parseInt(request.getParameter("count"));
                    } catch (NumberFormatException ignored) {
                    }
                    users = userMapper.getUsers(last, count);
                } catch (NumberFormatException ignored) {
                    result.setStatus(ApiResult.Status.FAILED);
                    result.setMessage("用户编号格式错误。");
                    result.setReason("id format error");
                }
            } else {
                users = userMapper.getAllUsers();
            }
            if (users != null)
                result.setData(users.stream().map((user) -> {
                            return EntityFilter.filter(user, new String[]{
                                    "password",
                                    "password_answer"
                            });
                        }).toList()
                );
        }
        return result;
    }

    @GetMapping("/user/count")
    public ApiResult getUsersCount() {
        ApiResult result = new ApiResult(ApiResult.Status.OK);
        result.setData(userMapper.getUsersCount());
        return result;
    }

    @PostMapping("/user/add")
    public ApiResult addUser(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("idcard") String idcard,
            @RequestParam("email") String email,
            @RequestParam("enabled") boolean status,
            @RequestParam("role") User.Role role
    ) {
        ApiResult result = new ApiResult();
        if (!userMapper.hasUsersWithNameAndId(username, null)) {
            int cost = config.getBcryptCost();
            byte[] hashedPasswd = BCrypt.withDefaults().hash(cost, password.getBytes());
            User user = new User(username, hashedPasswd);
            user.setEnabled(status);
            user.setRole(role);
            // FIXME: No backend validation here
            if (!idcard.isEmpty()) user.setIcNum(idcard);
            if (!email.isEmpty()) user.setEmail(email);
            userMapper.createNewUser(user);
            user = userMapper.getUserById(user.getId());
            result.setStatus(ApiResult.Status.OK);
            result.setData(EntityFilter.filter(user, new String[]{
                    "password",
                    "password_answer"
            }));
        } else {
            result.setStatus(ApiResult.Status.FAILED);
            result.setMessage("用户名已存在！");
            result.setReason("username existed");
        }
        return result;
    }

    @PostMapping("/user/update")
    public ApiResult updateUser(
            @RequestParam("id") int id,
            @RequestParam("username") String username,
            @RequestParam("idcard") String idcard,
            @RequestParam("email") String email,
            @RequestParam("enabled") boolean status,
            @RequestParam("role") User.Role role
    ) {
        ApiResult result = new ApiResult();
        if (userMapper.hasUsersWithNameAndId(null, id)) {
            User user = userMapper.getUserById(id);
            assert user != null;
            if (!username.equals(user.getUsername())) {
                if (userMapper.hasUsersWithNameAndId(username, null)) {
                    result.setStatus(ApiResult.Status.FAILED);
                    result.setMessage("用户名已存在！");
                    result.setReason("username existed");
                    return result;
                }
                user.setUsername(username);
            }
            // FIXME: No backend validation here
            if (idcard.isEmpty()) idcard = null;
            if (email.isEmpty()) email = null;
            user.setIcNum(idcard);
            user.setEmail(email);
            user.setEnabled(status);
            user.setRole(role);
            userMapper.updateUser(user);
            result.setStatus(ApiResult.Status.OK);
            result.setData(EntityFilter.filter(user, new String[]{
                    "password",
                    "password_answer"
            }));
        } else {
            result.setStatus(ApiResult.Status.FAILED);
            result.setMessage("用户不存在！");
            result.setReason("user not existed");
        }
        return result;
    }

    @PostMapping("/user/set_status")
    public ApiResult setStatus(
            @RequestParam("id") int id,
            @RequestParam("enabled") boolean status) {
        ApiResult result = new ApiResult();
        if (userMapper.hasUsersWithNameAndId(null, id)) {
            result.setStatus(ApiResult.Status.OK);
            userMapper.updateEnabled(id, status);
        } else {
            result.setStatus(ApiResult.Status.FAILED);
            result.setMessage("用户不存在。");
        }
        return result;
    }

    @PostMapping("/user/delete")
    public ApiResult delUser(@RequestParam("id") int id) {
        ApiResult result = new ApiResult();
        if (userMapper.hasUsersWithNameAndId(null, id)) {
            userMapper.deleteUserById(id);
            result.setStatus(ApiResult.Status.OK);
        } else {
            result.setStatus(ApiResult.Status.FAILED);
            result.setMessage("用户不存在。");
        }
        return result;
    }

    @PostMapping("/user/reset_password")
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

    @GetMapping("/user/get_display_name")
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

    @EventListener(ApplicationReadyEvent.class)
    public void setupFirstUser() {
        if (!userMapper.hasAdminUser()) {
            // Create default admin account if there is none.
            log.info("Create default admin account.");
            int cost = config.getBcryptCost();
            byte[] hashedPasswd = BCrypt.withDefaults().hash(cost, config.getDefaults().getAdminPassword().getBytes());
            User admin = new User(config.getDefaults().getAdminUsername(), hashedPasswd);
            admin.setRole(User.Role.ADMIN);
            admin.setEnabled(true);
            userMapper.createNewUser(admin);
        }
    }
}
