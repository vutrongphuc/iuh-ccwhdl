package iuh.course.hpt.controller;

import iuh.course.hpt.entity.Course;
import iuh.course.hpt.entity.User;
import iuh.course.hpt.service.interfaces.CourseService;
import iuh.course.hpt.service.interfaces.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired
    private EnrollmentService enrollmentService;

    // retrieve all courses
    @GetMapping("/course")
    public String allCourses(Model model, @RequestParam(required = false) String s) {
        // list courses
        if (s.isEmpty()) {
            List<Course> courses = courseService.getAll();
            model.addAttribute("courses", courses);
            model.addAttribute("count", courses.size());
        } else {
            List<Course> courses = courseService.findByCourseName(s);
            model.addAttribute("courses", courses);
            model.addAttribute("count", courses.size());
        }

        model.addAttribute("keyword", s);
        model.addAttribute("title", "Tìm khóa học");
        model.addAttribute("path", "course");

        return "list-course";
    }

    // retrieve a course by id
    @GetMapping("/course/{id}")
    public String courseDetail(@PathVariable Long id, Model model, @RequestParam(required = false) String error, @RequestParam(required = false) String success) {
        // get course by id
        Course course = courseService.getById(id);

        model.addAttribute("title", course.getCourseName());
        model.addAttribute("course", course);
        return "course-detail";
    }

    // enroll a course
    @GetMapping("/course/{id}/enroll")
    public String enrollCourse(@PathVariable Long id) {
        // get current user
        Authentication authUser = SecurityContextHolder.getContext().getAuthentication();
        if (authUser instanceof AnonymousAuthenticationToken) {
            return "index";
        }

        User currentUser = (User) authUser.getPrincipal();

        // check if user has already enrolled in this course
        if (enrollmentService.isEnrolled(id, currentUser.getId())) {
            return "redirect:/course/" + id + "?error=Khóa này đã được đăng ký!";
        }

        // enroll course
        enrollmentService.enroll(id, currentUser.getId());

        return "redirect:/course/" + id + "?success=Đăng ký khóa học thành công!";
    }

}
