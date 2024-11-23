package iuh.course.hpt.controller;

import iuh.course.hpt.entity.Author;
import iuh.course.hpt.entity.Category;
import iuh.course.hpt.entity.Course;
import iuh.course.hpt.service.interfaces.AuthorService;
import iuh.course.hpt.service.interfaces.CategoryService;
import iuh.course.hpt.service.interfaces.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Controller
public class AdminController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private CategoryService categoryService;

    @Value("${yt_api_url}")
    private String ytApiUrl;

    @Value("${yt_api_key}")
    private String ytApiKey;

    @Value("${yt_api_part}")
    private String ytApiPart;
    @Autowired
    private AuthorService authorService;

    @GetMapping("/admin/category")
    public String category(Model model) {
        model.addAttribute("title", "Quản lý danh mục");

        // get all categories
        model.addAttribute("categories", categoryService.getAll());

        return "admin/category";
    }

    // Create new category
    @RequestMapping(value = "/admin/category/create", method = RequestMethod.POST)
    public String createCategory(@ModelAttribute("category") Category category, Model model) {

        // check category is existed
        boolean isCategoryExisted = categoryService.isCategoryExisted(category.getCategoryName());

        if (isCategoryExisted) {
            model.addAttribute("error", "Danh mục đã tồn tại");
            return "admin/category";
        }

        Category result = categoryService.save(category);
        if (result != null) {
            model.addAttribute("success", "Thêm danh mục thành công");
        } else {
            model.addAttribute("error", "Thêm danh mục thất bại");
        }

        return "admin/category";
    }

    // Edit category
    @RequestMapping(value = "/admin/category/update", method = RequestMethod.POST)
    public String editCategory(@ModelAttribute("category") Category category, Model model) {

        // check category is existed
        boolean isCategoryExisted = categoryService.isCategoryExisted(category.getCategoryName());

        if (isCategoryExisted) {
            model.addAttribute("error", "Danh mục đã tồn tại");
            return "admin/category";
        }

        Category result = categoryService.save(category);
        if (result != null) {
            model.addAttribute("success", "Cập nhật danh mục thành công");
        } else {
            model.addAttribute("error", "Cập nhật danh mục thất bại");
        }

        return "admin/category";
    }

    // Delete category
    @RequestMapping(value = "/admin/category/delete", method = RequestMethod.GET)
    public String deleteCategory(@ModelAttribute("category") Category category, Model model) {

        categoryService.deleteById(category.getId());

        model.addAttribute("success", "Xóa danh mục thành công");

        return "admin/category";
    }

    @GetMapping("/admin/course")
    public String course(Model model) {
        model.addAttribute("title", "Quản lý khóa học");

        // get all courses
        model.addAttribute("courses", courseService.getAll());

        return "admin/admin-course";
    }

    // Create new course
    @RequestMapping(value = "/admin/course/create", method = RequestMethod.POST)
    public String createCourse(@ModelAttribute("course") Course course, Model model,
                               @RequestParam("authorName") String authorName,
                               @RequestParam("ytId") String ytId,
                               @RequestParam("duration") String duration,
                               @RequestParam("courseName") String courseName,
                               @RequestParam("courseDesc") String courseDesc) {

        // check course is existed
        boolean isCourseExisted = courseService.isCourseExisted(course.getCourseName());

        if (isCourseExisted) {
            model.addAttribute("error", "Khóa học đã tồn tại");
            return "admin/admin-course";
        }

        // check author is existed
        Author author = authorService.findByAuthorName(authorName);

        if (author == null) {
            author = new Author();
            author.setAuthorName(authorName);
            author.setAuthor_intro("Hiện đang cập nhật");
            author.setAuthor_email(authorName + "@gmail.com");

            authorService.createAuthor(author);
        }
        course.setYtId(ytId);
        course.setDuration(duration);
        course.setCourseName(courseName);
        course.setCourseDesc(courseDesc);
        course.setAuthor(author);

        Course result = courseService.save(course);

        if (result != null) {
            model.addAttribute("success", "Thêm khóa học thành công");
        } else {
            model.addAttribute("error", "Thêm khóa học thất bại");
        }

        return "admin/admin-course";
    }

    // get info youtube
    @RequestMapping(value = "/admin/course/info", method = RequestMethod.POST)
    public String getCourseInfo(@RequestParam("url") String url, Model model) {

        String youtubeId = courseService.extractYoutubeId(url);
        String apiUrl = ytApiUrl.replace("{0}", ytApiKey).replace("{1}", ytApiPart).replace("{2}", youtubeId);

        // Making an HTTP GET Request to Obtain the JSON Response from the YouTube API
        try {
            RestTemplate restTemplate = new RestTemplate();
            Map response = restTemplate.getForObject(apiUrl, Map.class);
            Map videoDetails = ((List<Map>) response.get("items")).get(0);

            String courseName = (String) ((Map) videoDetails.get("snippet")).get("title");
            String courseDesc = (String) ((Map) videoDetails.get("snippet")).get("description");
            String courseAuthor = (String) ((Map) videoDetails.get("snippet")).get("channelTitle");
            String duration = (String) ((Map) videoDetails.get("contentDetails")).get("duration");

            model.addAttribute("success", "Đã tìm thấy thông tin video");

            model.addAttribute("courseData", Map.of("ytId", youtubeId, "courseName", courseName, "courseDesc", courseDesc, "courseAuthor", courseAuthor, "duration", duration));

        } catch (Exception e) {
            model.addAttribute("error", "Không kết nối được video youtube");
            return "admin/admin-course";
        }

        return "admin/admin-course";
    }


}
