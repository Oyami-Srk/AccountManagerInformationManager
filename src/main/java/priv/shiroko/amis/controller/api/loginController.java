package priv.shiroko.amis.controller.api;

import at.favre.lib.crypto.bcrypt.BCrypt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import priv.shiroko.amis.config.Config;
import priv.shiroko.amis.entity.User;
import priv.shiroko.amis.mapper.UserMapper;
import priv.shiroko.amis.utils.ApiResult;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api")
@Slf4j
public class loginController {
    @Resource
    private UserMapper userMapper;
    @Resource
    private Config config;

    @PostMapping("/login")
    public ModelAndView doLogin(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session) {
        ModelAndView mav = new ModelAndView(new MappingJackson2JsonView());
        ApiResult result = new ApiResult();
        if (username == null || password == null) {
            result.setStatus(ApiResult.Status.Failed);
            result.setMessage("Username or password cannot be null.");
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
                result.setStatus(ApiResult.Status.Failed);
                result.setMessage("No user found.");
            } else {
                if (BCrypt.verifyer().verify(password.getBytes(), user.getPassword()).verified) {
                    result.setStatus(ApiResult.Status.OK);
                    result.setMessage("Login successful.");
                    session.setAttribute("user", user);
                } else {
                    result.setStatus(ApiResult.Status.Failed);
                    result.setMessage("Password incorrect.");
                }
            }
        }
        mav.addObject("result", result);
        return mav;
    }

    @PostMapping("/register")
    public ModelAndView doRegister() {
        ModelAndView mav = new ModelAndView(new MappingJackson2JsonView());
        mav.addObject("Hello world.");
        return mav;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void setupFirstUser() {
        if (!userMapper.hasAdminAccount()) {
            // Create default admin account if there is none.
            log.info("Create default admin account.");
            // Hash password twice
            int cost = config.getBcryptCost();
            byte[] hashedPasswd = BCrypt.withDefaults().hash(cost, config.getDefaults().getAdminPassword().getBytes());
            User admin = new User(config.getDefaults().getAdminUsername(), hashedPasswd);
            userMapper.createNewUser(admin);
        }
    }
}
