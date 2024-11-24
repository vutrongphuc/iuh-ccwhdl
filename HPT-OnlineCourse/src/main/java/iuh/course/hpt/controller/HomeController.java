package iuh.course.hpt.controller;

import iuh.course.hpt.service.interfaces.CourseService;
import iuh.course.hpt.utility.Utils;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Data
@Controller
public class HomeController {
    
    @Autowired
    private CourseService courseService;
    
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "HPT Online Course");
        model.addAttribute("path", "home");
        
        // List course
        model.addAttribute("courses", courseService.getAll());
        
        return "index";
    }
    
    // search page
    @PostMapping("/search")
    public String search(Model model, String s) {
        model.addAttribute("title", "Tìm kiếm");
        model.addAttribute("path", "search");
        return "redirect:/course?s=" + Utils.getInstance().encodeUrlSafe(s);
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
