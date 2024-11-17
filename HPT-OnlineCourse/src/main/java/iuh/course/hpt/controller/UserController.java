package iuh.course.hpt.controller;

import iuh.course.hpt.entity.User;
import iuh.course.hpt.service.implement.UserDetailsServiceImpl;
import iuh.course.hpt.service.implement.UserServiceImpl;
import iuh.course.hpt.service.interfaces.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.thymeleaf.util.StringUtils;


@Data
@Controller
public class UserController {

    // inject UserService
    @Autowired
    private UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("title", "Đăng ký tài khoản");
        model.addAttribute("user", new User());
        return "register";
    }

    // save user
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String saveUser(@ModelAttribute("user") User user, Model model) {

        // check repeat password
        if (!StringUtils.equalsIgnoreCase(user.getPassword(), user.getRepeatPassword())) {
            model.addAttribute("error", "Mật khẩu không khớp");
            return "register";
        }

        boolean isUserExisted = userService.isUserExisted(user.getUsername());

        if (isUserExisted) {
            model.addAttribute("error", "User đã tồn tại");
            return "register";
        }

        String usr_pwd = user.getPassword();

        User result = userService.save(user);

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(result.getUsername(), usr_pwd));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        model.addAttribute("user", result);
        model.addAttribute("success", "Chúc mừng bạn <b>" + result.getFullName() + "</b> đã bị dụ vào hệ thống học tập trực tuyến của HPT!<br />"
                + "Cám ơn đã đăng ký, chúc bạn học tốt và thành công!");
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (error != null) {
            model.addAttribute("error", "Tên đăng nhập hoặc mật khẩu không đúng.");
        }

        if (logout != null) {
            model.addAttribute("success", "Sớm quay lại bạn nhé!");
        }

        model.addAttribute("title", "Đăng nhập");
        model.addAttribute("user", new User());
        return "login";
    }

    @GetMapping("/login-successful")
    public String loginSuccessful(Model model) {
        Authentication authUser = SecurityContextHolder.getContext().getAuthentication();
        if (authUser instanceof AnonymousAuthenticationToken) {
            return "index";
        }

        User currentUser = (User) authUser.getPrincipal();
        model.addAttribute("title", "HPT Online Course");
        model.addAttribute("success", "Welcome <b>" + currentUser.getFullName() + "</b>!");
        return "index";
    }

    // Login cách cũ
    /*@RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginUser(@ModelAttribute("user") User user, Model model) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User currentUser = (User) authUser.getPrincipal();
        model.addAttribute("title", "HPT Online Course");
        model.addAttribute("success", "Welcome <b>" + currentUser.getFullName() + "</b>!");
        return "index";
    }

    @GetMapping(value = "/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authUser = SecurityContextHolder.getContext().getAuthentication();
        if (authUser instanceof AnonymousAuthenticationToken) {
            return "index";
        }

        new SecurityContextLogoutHandler().logout(request, response, authUser);

        return "redirect:/login?logout";
    }*/

    // Profile
    @GetMapping(value = "/profile")
    public String profile(Model model) {
        // get current user
        Authentication authUser = SecurityContextHolder.getContext().getAuthentication();
        if (authUser instanceof AnonymousAuthenticationToken) {
            return "login";
        }

        User currentUser = (User) authUser.getPrincipal();

        model.addAttribute("title", "Profile");
        model.addAttribute("user", currentUser);
        return "profile";
    }

    @RequestMapping(value = "/update-profile", method = RequestMethod.POST)
    public String updateUser(@ModelAttribute("user") User user, Model model) {
        Authentication authUser = SecurityContextHolder.getContext().getAuthentication();
        if (authUser instanceof AnonymousAuthenticationToken) {
            return "login";
        }
        User currentUser = (User) authUser.getPrincipal();

        if (StringUtils.equalsIgnoreCase(currentUser.getUsername(), user.getUsername())) {
            user.setId(currentUser.getId());
            if (StringUtils.isEmpty(user.getPassword())) {
                user.setPassword(currentUser.getPassword());
            }
            userService.save(user);
            model.addAttribute("success", "Cập nhật thông tin thành công.");
            model.addAttribute("title", "HPT Online Course");
            return "index";
        }
        model.addAttribute("error", "Bạn không có quền cập nhật thông tin người khác.");
        return "profile";
    }

    @GetMapping(value = "/delete-profile")
    public String deleteUser(Model model) {
        Authentication authUser = SecurityContextHolder.getContext().getAuthentication();
        if (authUser instanceof AnonymousAuthenticationToken) {
            return "login";
        }

        User currentUser = (User) authUser.getPrincipal();

        userService.deleteById(currentUser.getId());
        model.addAttribute("success", "Xóa tài khoản thành công.");
        model.addAttribute("title", "HPT Online Course");
        return "index";
    }
}
