package priv.shiroko.amis.controller.api;

import at.favre.lib.crypto.bcrypt.BCrypt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import priv.shiroko.amis.config.Config;
import priv.shiroko.amis.entity.User;
import priv.shiroko.amis.mapper.UserMapper;
import priv.shiroko.amis.utils.ApiResult;
import priv.shiroko.amis.utils.EntityFilter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
public class userController {
    @Resource
    private UserMapper userMapper;
    @Resource
    private Config config;

    @PostMapping("/login")
    public ModelAndView doLogin(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session) {
        ModelAndView mav = new ModelAndView(new MappingJackson2JsonView());
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
        mav.addObject("result", result);
        return mav;
    }

    @GetMapping("/logout")
    public ModelAndView doLogout(HttpSession session) {
        ModelAndView mav = new ModelAndView(new MappingJackson2JsonView());
        session.removeAttribute("user");
        ApiResult result = new ApiResult(ApiResult.Status.OK);
        result.setMessage("登出成功。");
        mav.addObject("result", result);
        return mav;
    }

    @GetMapping("/user/get")
    public ModelAndView getUser(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView(new MappingJackson2JsonView());
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
        mav.addObject("result", result);
        return mav;
    }

    @GetMapping("/user/count")
    public ModelAndView getUsersCount() {
        ModelAndView mav = new ModelAndView(new MappingJackson2JsonView());
        ApiResult result = new ApiResult(ApiResult.Status.OK);
        result.setData(userMapper.getUsersCount());
        mav.addObject("result", result);
        return mav;
    }

    @PostMapping("/user/add")
    public ModelAndView addUser() {
        ModelAndView mav = new ModelAndView(new MappingJackson2JsonView());
        ApiResult result = new ApiResult();
        // Check if username and id is existed.
        mav.addObject("result", result);
        return mav;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void setupFirstUser() {
        if (!userMapper.hasAdminUser()) {
            // Create default admin account if there is none.
            log.info("Create default admin account.");
            // Hash password twice
            int cost = config.getBcryptCost();
            byte[] hashedPasswd = BCrypt.withDefaults().hash(cost, config.getDefaults().getAdminPassword().getBytes());
            User admin = new User(config.getDefaults().getAdminUsername(), hashedPasswd);
            admin.setRole(User.Role.ADMIN);
            admin.setEnabled(true);
            userMapper.createNewUser(admin);
        }
    }
}
