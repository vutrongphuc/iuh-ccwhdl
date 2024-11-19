package iuh.course.hpt.controller;

import iuh.course.hpt.entity.Course;
import iuh.course.hpt.service.interfaces.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping("/course")
    public String course(Model model) {
        List<Course> courses = courseService.getAllCourses();

        model.addAttribute("courses", courses);
        return "course";
    }

    @GetMapping("/manage-course")
    public String manageCourse(Model model) {
        model.addAttribute("course", new Course());
        return "admin/manage-course";
    }


    @PostMapping("/manage-course")
    public String fetchCourse(@RequestParam String ytUrl, Model model) {
        String ytId = courseService.extractYoutubeId(ytUrl);
        if (ytId != null) {
            Course course = courseService.fetchYoutubeData(ytId);

            if (course != null) {
                model.addAttribute("course", course);
            } else {
                model.addAttribute("error", "Failed to fetch course details.");
            }

            return "admin/manage-course";
        }

        model.addAttribute("error", "Invalid YouTube URL.");
        return "admin/manage-course";
    }

    @PostMapping("/manage-course/save")
    public String saveCourse(Course course) {
        courseService.saveCourse(course);
        return "redirect:/manage-course";
    }
}
