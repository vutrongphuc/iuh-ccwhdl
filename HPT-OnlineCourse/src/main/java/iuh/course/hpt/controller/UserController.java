package iuh.course.hpt.controller;

import iuh.course.hpt.entity.User;
import iuh.course.hpt.entity.enums.Role;
import iuh.course.hpt.service.interfaces.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/register")
    public String register(HttpSession session, Model model) {
        // redirect to home page if user already logged in
        if (session.getAttribute("user") != null) {
            return "index";
        }
        
        model.addAttribute("title", "Sign Up");
        model.addAttribute("user", new User());
        return "register";
    }

    // save user
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String saveUser(HttpSession session, @ModelAttribute("user") User user, Model model) {
        
        // check repeat password
        if (!StringUtils.equalsIgnoreCase(user.getPassword(), user.getRepeatPassword())) {
            model.addAttribute("error", "Mật khẩu không khớp");
            return "register";
        }
        
        user.setUserName(user.getUserName().toLowerCase());
        user.setRole(Role.USER); // default role is USER
        
        boolean isUserExisted = userService.isUserExisted(user.getUserName());

        if (isUserExisted) {
            model.addAttribute("error", "User đã tồn tại");
            return "register";
        }

        User result = userService.save(user);
        
        session.setAttribute("user", result);
        model.addAttribute("user", result);
        model.addAttribute("success", "Chúc mừng bạn " + result.getFullName() + " đã bị dụ vào hệ thống học tập trực tuyến của HPT!<br />"
                + "Cám ơn đã đăng ký, chúc bạn học tốt và thành công!");
        return "index";
    }

    @GetMapping("/login")
    public String login(HttpSession session, Model model) {
        // redirect to home page if user already logged in
        if (session.getAttribute("user") != null) {
            return "index";
        }
        model.addAttribute("title", "Login");
        model.addAttribute("user", new User());
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginUser(HttpSession session, @ModelAttribute("user") User user, Model model) {
        boolean isValidUser = userService.isValidUser(user);

        if (isValidUser) {
            model.addAttribute("user ", user);
            session.setAttribute("user", user);
            model.addAttribute("success", "Welcome " + user.getFullName() + "!");
            return "index";
        }

        model.addAttribute("error", "Sai Tên đăng nhập hoặc Password");
        return "login";
    }

    @GetMapping(value = "/logout")
    public String logout(HttpSession session, Model model) {
        session.invalidate();
        model.addAttribute("success", "Sớm quay lại bạn nhé!");
        return "index";
    }

    // Profile
    @GetMapping(value = "/profile")
    public String profile(HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("user");
        model.addAttribute("title", "Profile");
        model.addAttribute("user", currentUser);
        return "profile";
    }

    @RequestMapping(value = "/update-profile", method = RequestMethod.POST)
    public String updateUser(HttpSession session, @ModelAttribute("user") User user, Model model) {
        User currentUser = (User) session.getAttribute("user");
        if (StringUtils.equalsIgnoreCase(currentUser.getUserName(), user.getUserName())) {
            user.setId(currentUser.getId());
            if (StringUtils.isEmpty(user.getPassword())) {
                user.setPassword(currentUser.getPassword());
            }
            userService.save(user);
            session.setAttribute("user", user);
            model.addAttribute("success", "Cập nhật thông tin thành công.");
            return "index";
        }
        model.addAttribute("error", "Bạn không có quền cập nhật thông tin người khác.");
        return "profile";
    }

    @GetMapping(value = "/delete-profile")
    public String deleteUser(HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("user");
        userService.deleteById(currentUser.getId());
        session.invalidate();
        model.addAttribute("success", "Xóa tài khoản thành công.");
        return "index";
    }
}
