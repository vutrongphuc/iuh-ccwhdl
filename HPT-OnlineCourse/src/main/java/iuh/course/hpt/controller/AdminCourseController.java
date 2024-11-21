package iuh.course.hpt.controller;

import iuh.course.hpt.entity.Course;
import iuh.course.hpt.service.interfaces.AdminCourseService;
import iuh.course.hpt.service.interfaces.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AdminCourseController {

    @Autowired
    private AdminCourseService adminCourseService;

    @Autowired
    private CourseService courseService;

    // admin course page with role admin
    @GetMapping("/admin-course")
    public String adminCourse(Model model, Pageable pageable) {
        // Title page
        model.addAttribute("title", "Quản lý khóa học");

        // List all courses
        List<Course> courses = courseService.getAllCourses();
        model.addAttribute("courses", courses);

        return "admin";
    }

    // add course page with role admin
    @PostMapping("/admin-course")
    public String addCourse(@RequestParam String ytURL, Model model) {
        // extract YouTube id from YouTube url
        String ytId = adminCourseService.extractYoutubeId(ytURL);

        if (ytId == null) {
            model.addAttribute("error", "URL không hợp lệ hoặc không chứa ID YouTube.");
            return "admin";
        }

        // fetch YouTube data
        Course course = adminCourseService.fetchYoutubeData(ytId);

        if (course != null) {
            model.addAttribute("success", "Lấy thông tin khóa học thành công.");
            model.addAttribute("course", course);
        } else {
            model.addAttribute("error", "Không thể lấy thông tin khóa học hoặc khóa học đã tồn tại.");
        }

        return "admin";
    }

    @PostMapping("/admin-course/add")
    public String addCourse(Model model, Course course) {
        // Ensure the ID is null for new courses
        course.setId(null);
        try {
            adminCourseService.saveCourse(course);
            model.addAttribute("success", "Thêm khóa học thành công.");
        } catch (Exception e) {
            model.addAttribute("error", "Đã xảy ra lỗi khi lưu khóa học. Vui lòng thử lại.");
        }
        // redirect to admin course page
        return "redirect:/admin-course";
    }

    // delete course with role admin
    @DeleteMapping("/admin-course/{id}")
    public String deleteCourse(@PathVariable int id, Model model) {
        try {
            adminCourseService.deleteCourse(id);
            model.addAttribute("success", "Xóa khóa học thành công.");
        } catch (Exception e) {
            model.addAttribute("error", "Đã xảy ra lỗi khi xóa khóa học. Vui lòng thử lại.");
        }
        // redirect to admin course page
        return "redirect:/admin-course";
    }
}