package iuh.course.hpt.service.implement;

import iuh.course.hpt.entity.Author;
import iuh.course.hpt.entity.Course;
import iuh.course.hpt.repository.CourseRepository;
import iuh.course.hpt.service.interfaces.AdminCourseService;
import iuh.course.hpt.service.interfaces.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class AdminCourseServiceImpl implements AdminCourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private AuthorService authorService;

    @Value("${yt_api_url}")
    private String ytApiUrl;

    @Value("${yt_api_key}")
    private String ytApiKey;

    @Value("${yt_api_part}")
    private String ytApiPart;

    // extract YouTube id from YouTube url
    @Override
    public String extractYoutubeId(String youtubeUrl) {
        if (youtubeUrl != null && youtubeUrl.contains("v=")) {
            int startIndex = youtubeUrl.indexOf("v=") + 2;
            int endIndex = youtubeUrl.indexOf("&", startIndex);
            return (endIndex == -1) ? youtubeUrl.substring(startIndex) : youtubeUrl.substring(startIndex, endIndex);
        }
        return null;
    }

    // Gọi API
    @Override
    public Course fetchYoutubeData(String ytId) {
        boolean existingYtId = courseRepository.existsByYtId(ytId);

        if (ytId != null && !existingYtId) {
            String apiURL = ytApiUrl.replace("{0}", ytApiKey)
                    .replace("{1}", ytApiPart)
                    .replace("{2}", ytId);


            RestTemplate restTemplate = new RestTemplate();

            try {
                Map response = restTemplate.getForObject(apiURL, Map.class);
                Map videoDetails = ((List<Map>) response.get("items")).get(0);

                // Lấy thông tin API
                String courseName = (String) ((Map) videoDetails.get("snippet")).get("title");
                String courseDesc = (String) ((Map) videoDetails.get("snippet")).get("description");
                String courseAuthor = (String) ((Map) videoDetails.get("snippet")).get("channelTitle");
                String duration = (String) ((Map) videoDetails.get("contentDetails")).get("duration");

                Author author = authorService.findByAuthorName(courseAuthor);
                if (author == null) {
                    author = new Author();
                    author.setAuthorName(courseAuthor);
                    author.setAuthor_intro("Thông tin tác giả hiện chưa được cập nhật.");

                    String email = courseAuthor.replace(" ", "") + "@gmail.com";
                    author.setAuthor_email(email);

                    authorService.saveAuthor(author);
                }

                // Tạo mới khóa học
                Course course = new Course();
                course.setYtId(ytId);
                course.setCourseName(courseName);
                course.setCourseDesc(courseDesc);
                course.setDuration(duration);
                course.setAuthor(author);

                return course;

            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    @Override
    public void saveCourse(Course course) {
        if (course.getId() == null) {
            courseRepository.save(course);
        } else {
            throw new IllegalArgumentException("The given id must not be null");
        }
    }

    @Override
    public void deleteCourse(int id) {
        courseRepository.deleteById(id);
    }

    @Override
    public Course findCourseById(int id) {
        return courseRepository.findById(id).orElse(null);
    }

}
