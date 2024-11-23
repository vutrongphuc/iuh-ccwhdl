package iuh.course.hpt.controller;

import iuh.course.hpt.entity.Author;
import iuh.course.hpt.entity.Category;
import iuh.course.hpt.entity.Course;
import iuh.course.hpt.service.interfaces.AuthorService;
import iuh.course.hpt.service.interfaces.CategoryService;
import iuh.course.hpt.service.interfaces.CourseService;
import iuh.course.hpt.utility.Utils;
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

    /* ------------------- Category Controller Start ------------------- */
    @GetMapping("/admin/category")
    public String category(@ModelAttribute("error") String error,
                           @ModelAttribute("success") String success,
                           Model model) {

        model.addAttribute("title", "Quản lý danh mục");

        // get all categories
        model.addAttribute("categories", categoryService.getAll());

        return "admin/category";
    }

    // Create new category
    @RequestMapping(value = "/admin/category", method = RequestMethod.POST)
    public String createCategory(@ModelAttribute("category") Category category,
                                 @ModelAttribute("error") String error,
                                 @ModelAttribute("success") String success) {

        // check category is existed
        boolean isCategoryExisted = categoryService.isCategoryExisted(category.getCategoryName());

        if (isCategoryExisted) {
            return "redirect:/admin/category?error=" + Utils.getInstance().encodeUrlSafe("Danh mục đã tồn tại");
        }

        Category result = categoryService.save(category);
        if (result != null) {
            return "redirect:/admin/category?success=" + Utils.getInstance().encodeUrlSafe("Thêm danh mục thành công");
        } else {
            return "redirect:/admin/category?error=" + Utils.getInstance().encodeUrlSafe("Tạo danh mục thất bại");
        }

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

    /* ------------------- Category Controller End ------------------- */


    /* ------------------- Course Controller Start ------------------- */
    @GetMapping("/admin/course")
    public String course(Model model) {
        model.addAttribute("title", "Quản lý khóa học");

        // get all courses
        model.addAttribute("courses", courseService.getAll());

        return "admin/admin-course";
    }

    // Create new course
    @RequestMapping(value = "/admin/course", method = RequestMethod.POST)
    public String createCourse(@ModelAttribute("course") Course course,
                               @RequestParam("authorName") String authorName,
                               Model model) {

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
            author.setAuthor_email(authorName.toLowerCase().replace(" ", "") + "@gmail.com");

            authorService.createAuthor(author);
        }

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
    public String getCourseInfo(@RequestParam("url") String url, Model model, @ModelAttribute("error") String error) {

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

            model.addAttribute("success", "Lấy thông tin khóa học thành công");
            model.addAttribute("courseData",
                    Map.of("ytId", youtubeId,
                            "courseName", courseName,
                            "courseDesc", courseDesc,
                            "courseAuthor", courseAuthor,
                            "duration", duration));
            return "admin/admin-course";

        } catch (Exception e) {
            return "redirect:/admin/course?error=" + Utils.getInstance().encodeUrlSafe("Không kết nối được video youtube");
        }

    }

    // delete course
    @RequestMapping(value = "/admin/course/delete", method = RequestMethod.GET)
    public String deleteCourse(@RequestParam("id") Long id, @RequestParam("authorId") Long authorId,
                               @ModelAttribute("error") String error,
                               @ModelAttribute("success") String success) {

        courseService.deleteById(id);

        // if author has no course, delete author
        if (courseService.countByAuthorId(authorId) == 0) {
            authorService.deleteAuthor(authorId);
            return "redirect:/admin/course?success=" + Utils.getInstance().encodeUrlSafe("Xóa khóa học thành công");
        }

        return "redirect:/admin/course?error=" + Utils.getInstance().encodeUrlSafe("Không thể xóa khóa học");
    }

    /* ------------------- Course Controller End ------------------- */


    /* ------------------- Author Controller Start ------------------- */

    @GetMapping("/admin/author")
    public String allAuthors(Model model) {
        model.addAttribute("title", "Quản lý tác giả");

        model.addAttribute("authors", authorService.getAll());
        return "admin/author";
    }

    // delete author and all course by author
    @RequestMapping(value = "/admin/author/delete", method = RequestMethod.GET)
    public String deleteAuthor(@RequestParam("authorId") Long authorId, @ModelAttribute("success") String success) {

        List<Course> courseList = courseService.findByAuthor(authorId);
        for (Course c : courseList) {
            courseService.deleteById(c.getId());
        }

        authorService.deleteAuthor(authorId);
        return "redirect:/admin/author?success=" + Utils.getInstance().encodeUrlSafe("Xóa tác giả thành công");
    }



    /* ------------------- Author Controller End ------------------- */
}
