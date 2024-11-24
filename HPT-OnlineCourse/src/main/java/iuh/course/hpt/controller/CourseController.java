package iuh.course.hpt.controller;

import iuh.course.hpt.entity.Course;
import iuh.course.hpt.service.interfaces.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CourseController {
    @Autowired
    private CourseService courseService;

    // retrieve all courses
    @GetMapping("/course")
    public String allCourses(Model model) {
        // list courses
        List<Course> courses = courseService.getAll();

        model.addAttribute("title", "Khóa học");
        model.addAttribute("courses", courses);
        return "list-course";
    }

    // retrieve a course by id
    @GetMapping("/course/{id}")
    public String courseDetail(@PathVariable Long id, Model model) {
        // get course by id
        Course course = courseService.getById(id);

        model.addAttribute("title", "Chi tiết khóa học");
        model.addAttribute("success", "Chào mừng bạn đến với khóa học");
        model.addAttribute("course", course);
        return "course-detail";
    }

}
