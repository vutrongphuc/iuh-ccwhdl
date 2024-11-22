package iuh.course.hpt.service.implement;

import iuh.course.hpt.entity.Course;
import iuh.course.hpt.repository.CourseRepository;
import iuh.course.hpt.service.interfaces.AdminCourseService;
import iuh.course.hpt.service.interfaces.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
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
    public List<String> fetchYoutubeData(String ytId) {
        List<String> youtubeInfo = new ArrayList<>();
        Course existingYtId = courseRepository.findByYtId(ytId);

        if (ytId != null && existingYtId != null) {
            String apiURL = ytApiUrl.replace("{0}", ytApiKey)
                    .replace("{1}", ytApiPart)
                    .replace("{2}", ytId);


            try {
                RestTemplate restTemplate = new RestTemplate();

                Map response = restTemplate.getForObject(apiURL, Map.class);
                Map videoDetails = ((List<Map>) response.get("items")).get(0);

                // Lấy thông tin API
                String courseName = (String) ((Map) videoDetails.get("snippet")).get("title");
                String courseDesc = (String) ((Map) videoDetails.get("snippet")).get("description");
                String courseAuthor = (String) ((Map) videoDetails.get("snippet")).get("channelTitle");
                String duration = (String) ((Map) videoDetails.get("contentDetails")).get("duration");

                youtubeInfo.add(courseName);
                youtubeInfo.add(courseDesc);
                youtubeInfo.add(courseAuthor);
                youtubeInfo.add(duration);
                youtubeInfo.add(ytId);

                return youtubeInfo;

            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    @Override
    public void addCourse(Course course) {
        Course existingCourse = courseRepository.findByYtId(course.getYtId());
        if (existingCourse == null) {
            courseRepository.save(course);
        }
    }

    @Override
    public void updateCourse(int id, Course course) {
        // update info course
        Course updatedCourse = courseRepository.findById(id).orElse(null);
        if (updatedCourse != null) {
            updatedCourse.setCourseName(course.getCourseName());
            updatedCourse.setCourseDesc(course.getCourseDesc());
            courseRepository.save(updatedCourse);
        }
    }

    @Override
    public void deleteCourse(int id) {
        courseRepository.deleteById(id);
    }

}
