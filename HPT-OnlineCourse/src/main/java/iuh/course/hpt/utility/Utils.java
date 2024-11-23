package iuh.course.hpt.utility;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public final class Utils {
    private static Utils instance;

    private Utils() {
    }

    public static Utils getInstance() {
        if (instance == null) {
            instance = new Utils();
        }
        return instance;
    }
    
    public String encodeUrlSafe(String value) {
        try {
            return encodeUrl(value);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public String decodeUrlSafe(String value) {
        try {
            return decodeUrl(value);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String encodeUrl(String value) throws UnsupportedEncodingException {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }

    private String decodeUrl(String value) throws UnsupportedEncodingException {
        return URLDecoder.decode(value, StandardCharsets.UTF_8);
    }

}
