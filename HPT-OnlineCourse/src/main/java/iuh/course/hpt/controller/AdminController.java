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

import java.text.MessageFormat;
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
    
    @Value("${yt_thumbnail_url}")
    private String ytThumbnailUrl;
    
    @Autowired
    private AuthorService authorService;

    /* ------------------- Category Controller Start ------------------- */
    @GetMapping("/admin/category")
    public String category(Model model, @ModelAttribute("error") String error, @ModelAttribute("success") String success) {
        model.addAttribute("title", "Quản lý danh mục");

        // get all categories
        model.addAttribute("categories", categoryService.getAll());

        // create new category
        model.addAttribute("category", new Category());

        model.addAttribute("path", "category");

        return "admin/category";
    }

    // Create - Update category
    @RequestMapping(value = "/admin/category", method = RequestMethod.POST)
    public String createCategory(@ModelAttribute("category") Category category) {

        // check category is existed
        boolean isCategoryExisted = categoryService.isCategoryExisted(category.getCategoryName());

        if (isCategoryExisted) {
            return "redirect:/admin/category?error=" + Utils.getInstance().encodeUrlSafe("Danh mục đã tồn tại");
        }

        Category result = categoryService.save(category);
        if (result != null) {
            return "redirect:/admin/category?success=" + Utils.getInstance().encodeUrlSafe("Tạo danh mục thành công");
        } else {
            return "redirect:/admin/category?error=" + Utils.getInstance().encodeUrlSafe("Tạo danh mục thất bại");
        }

    }

    // Delete category
    @RequestMapping(value = "/admin/category/delete/{id}", method = RequestMethod.GET)
    public String deleteCategory(@ModelAttribute("id") Long id) {

        categoryService.deleteById(id);

        return "redirect:/admin/category?success=" + Utils.getInstance().encodeUrlSafe("Xóa danh mục thành công");
    }

    /* ------------------- Category Controller End ------------------- */


    /* ------------------- Course Controller Start ------------------- */
    @GetMapping("/admin/course")
    public String course(Model model, @ModelAttribute("error") String error, @ModelAttribute("success") String success) {
        model.addAttribute("title", "Quản lý khóa học");

        // get all courses
        model.addAttribute("courses", courseService.getAll());

        model.addAttribute("path", "admin-course");

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
            author.setAuthorEmail(authorName.toLowerCase().replace(" ", "") + "@gmail.com");

            authorService.createAuthor(author);
        }

        course.setAuthor(author);

        Course result = courseService.save(course);

        if (result != null) {
            return "redirect:/admin/course?success=" + Utils.getInstance().encodeUrlSafe("Thêm khóa học thành công");
        } else {
            return "redirect:/admin/course?error=" + Utils.getInstance().encodeUrlSafe("Thêm khóa học thất bại");
        }
    }

    // get info youtube
    @RequestMapping(value = "/admin/course/info", method = RequestMethod.POST)
    public String getCourseInfo(@RequestParam("url") String url, Model model, @ModelAttribute("error") String error) {

        String youtubeId = courseService.extractYoutubeId(url);
        String apiUrl = MessageFormat.format(ytApiUrl, ytApiKey, ytApiPart, youtubeId);

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
                            "duration", duration,
                            "thumbnail", MessageFormat.format(ytThumbnailUrl, youtubeId)));
            
            // category
            model.addAttribute("categories", categoryService.getAll());
            
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

    // form update course information
    @GetMapping("/admin/course/update")
    public String updateInfo(@RequestParam("id") Long id, Model model) {
        model.addAttribute("title", "Cập nhật khóa học");

        Course course = courseService.getById(id);
        // type object update
        model.addAttribute("type", "course");
        model.addAttribute("course", course);
        return "admin/update";
    }

    // edit course
    @RequestMapping(value = "/admin/course/update", method = RequestMethod.POST)
    public String editCourse(@RequestParam("id") Long id,
                             @RequestParam("courseName") String name,
                             @RequestParam("courseDesc") String desc,
                             Model model) {


        Course course = courseService.getById(id);
        if (!course.getCourseName().equals(name)) {

            // check course is existed
            boolean isCourseExisted = courseService.isCourseExisted(name);

            if (isCourseExisted) {
                model.addAttribute("error", "Khóa học đã tồn tại");
                return "admin/admin-course";
            }
        }

        course.setCourseName(name);
        course.setCourseDesc(desc);

        Course result = courseService.save(course);
        if (result != null) {
            model.addAttribute("success", "Cập nhật khóa học thành công");
        } else {
            model.addAttribute("error", "Cập nhật khóa học thất bại");
        }

        return "admin/admin-course";
    }

    /* ------------------- Course Controller End ------------------- */


    /* ------------------- Author Controller Start ------------------- */

    @GetMapping("/admin/author")
    public String allAuthors(Model model, @ModelAttribute("error") String error, @ModelAttribute("success") String success) {
        model.addAttribute("title", "Quản lý tác giả");

        model.addAttribute("authors", authorService.getAll());

        model.addAttribute("path", "admin-author");

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

    // form update author information
    @GetMapping("/admin/author/update")
    public String updateAuthor(@RequestParam("id") Long id, Model model) {
        model.addAttribute("title", "Cập nhật tác giả");

        Author author = authorService.getById(id);
        // type object update
        model.addAttribute("type", "author");
        model.addAttribute("author", author);
        return "admin/update";
    }

    // edit author
    @RequestMapping(value = "/admin/author/update", method = RequestMethod.POST)
    public String editAuthor(@RequestParam("id") Long id,
                             @RequestParam("authorName") String name,
                             @RequestParam("author_email") String email,
                             @RequestParam("author_intro") String intro,
                             Model model) {

        Author author = authorService.getById(id);

        // check email
        if (!author.getAuthorEmail().equals(name)) {

            boolean emailExisting = authorService.isAuthorEmailExisted(email);

            if (emailExisting) {
                model.addAttribute("error", "Email đã tồn tại");
                return "admin/author";
            }
        }

        author.setAuthorName(name);
        author.setAuthorEmail(email);
        author.setAuthor_intro(intro);

        Author result = authorService.updateAuthor(author);
        if (result != null) {
            model.addAttribute("success", "Cập nhật tác giả thành công");
        } else {
            model.addAttribute("error", "Cập nhật tác giả thất bại");
        }

        return "admin/author";
    }

    /* ------------------- Author Controller End ------------------- */

}