package iuh.course.hpt.controller;

import iuh.course.hpt.entity.Course;
import iuh.course.hpt.service.interfaces.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

    // course detail page
    @GetMapping("/course/{id}")
    public String getACourse(@PathVariable("id") Long id, Model model) {
        Course course = courseService.getById(id);

        if (course == null) {
            model.addAttribute("error", "Không tìm thấy khóa học");
            return "course-detail";
        }

        model.addAttribute("title", "Khóa học");
        model.addAttribute("course", course);
        return "course-detail";
    }

}
