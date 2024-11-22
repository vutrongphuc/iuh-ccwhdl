package iuh.course.hpt.controller;

import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Data
@Controller
public class HomeController {
    
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "HPT Online Course");
        return "index";
    }
    
    // 404 page
    @GetMapping("/404")
    public String error404(Model model) {
        model.addAttribute("title", "404 - Page Not Found");
        return "404";
    }
    
    // 403 page
    @GetMapping("/403")
    public String error403(Model model) {
        model.addAttribute("title", "403 - Access Denied");
        return "403";
    }
    
}
