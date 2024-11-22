package iuh.course.hpt.controller;

import iuh.course.hpt.entity.Author;
import iuh.course.hpt.entity.Course;
import iuh.course.hpt.service.interfaces.AdminCourseService;
import iuh.course.hpt.service.interfaces.AuthorService;
import iuh.course.hpt.service.interfaces.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AdminCourseController {

    @Autowired
    private AdminCourseService adminCourseService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private AuthorService authorService;

    // page admin course
    @GetMapping("/admin-course")
    public String adminCourse(Model model) {
        // List all courses
        List<Course> courses = courseService.getAllCourses();

        model.addAttribute("listCourses", courses);
        return "admin/admin-course";
    }

    // TODO
    // get course information from youtube url
    @PostMapping("/admin-course")
    public String responseYtInfo(@RequestParam String ytUrl, Course course, Model model) {
        String ytId = adminCourseService.extractYoutubeId(ytUrl);
        if (ytId == null) {
            model.addAttribute("error", "Url không hợp lệ");
            return "admin/admin-course";
        }

        // Returns the course information form
        List<String> youtubeInfo = adminCourseService.fetchYoutubeData(ytId);
        if (youtubeInfo == null || youtubeInfo.isEmpty()) {
            model.addAttribute("error", "Không tìm thấy thông tin video");
            return "admin/admin-course";
        }
        // Add course information to the model
        model.addAttribute("success", "Đã tìm thấy thông tin video");
        model.addAttribute("youtubeInfo", youtubeInfo);
        return "admin/admin-course";
    }

    // TODO
    // add new course from youtube info on the same page
    @PostMapping("/admin-course/add")
    public String addNewCourse(@RequestParam String ytId,
                               @RequestParam String courseName,
                               @RequestParam String courseDesc,
                               @RequestParam String courseAuthor,
                               @RequestParam String duration,
                               Model model) {

        Course existingYtId = courseService.findCourseByYtId(ytId);

        if (existingYtId != null) {
            model.addAttribute("error", "Khóa học đã tồn tại");
            return "admin/admin-course";
        }

        // Check author exists
        Author author = authorService.findByAuthorName(courseAuthor);
        if (author == null) {
            Author newAuthor = new Author();
            newAuthor.setAuthorName(courseAuthor);
            newAuthor.setAuthor_intro("Hiện đang cập nhật");
            newAuthor.setAuthor_email(courseAuthor + "@gmail.com");
            authorService.saveAuthor(newAuthor);
        }

        // Add course to the database
        try {
            Course course = new Course();
            course.setCourseName(courseName);
            course.setCourseDesc(courseDesc);
            course.setDuration(duration);
            course.setAuthor(author);

            adminCourseService.addCourse(course);
            model.addAttribute("success", "Thêm khóa học thành công");

        } catch(Exception e) {
            model.addAttribute("error", "Không thể thêm khóa học");
            return "redirect:/admin/admin-course";
        }

        return "redirect:/admin-course";
    }

    // The new page updates the course when the edit button is pressed
    @GetMapping("/admin-course/update/{id}")
    public String updateCourse(@PathVariable int id, Model model) {
        Course course = courseService.findCourseById(id);
        if (course == null) {
            model.addAttribute("error", "Không tìm thấy khóa học");
            return "admin/admin-course";
        }
        model.addAttribute("course", course);
        return "/admin/update-course";
    }

    // Update course information
    @PostMapping("/admin-course/update")
    public String updateCourse(@RequestParam int id, Course course, Model model) {
        Course existingCourse = courseService.findCourseById(id);
        if (existingCourse == null) {
            model.addAttribute("error", "Không tìm thấy khóa học");
            return "/admin/admin-course";
        }

        // Update course information
        try {
            adminCourseService.updateCourse(id, course);
            model.addAttribute("success", "Cập nhật khóa học thành công");
        } catch (Exception e) {
            model.addAttribute("error", "Không thể cập nhật khóa học");
            return "admin/admin-course";
        }

        return "admin/admin-course";
    }

    // delete the course via post method
    @PostMapping("/admin-course/delete")
    public String deleteCourse(@RequestParam int id, Model model) {
        Course existingCourse = courseService.findCourseById(id);
        if (existingCourse == null) {
            model.addAttribute("error", "Không tìm thấy khóa học");
            return "redirect:/admin-course";
        }

        // Delete course
        try {
            adminCourseService.deleteCourse(id);
            model.addAttribute("success", "Xóa khóa học thành công");
        } catch (Exception e) {
            model.addAttribute("error", "Không thể xóa khóa học");
            return "redirect:/admin-course";
        }

        return "redirect:/admin-course";
    }
}
