package by.danceform.app.web.rest.util;

import by.danceform.app.domain.document.UploadedDocument;
import javax.servlet.http.HttpServletResponse;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

/**
 * Created by USER on 22.02.2017.
 */
public class DownloadUtil {

    public static HttpEntity<byte[]> download(HttpServletResponse response, UploadedDocument doc) {
        return download(response,
            doc.getFullName(),
            StringUtils.split(doc.getContentContentType(), "/"),
            doc.getContent());
    }


    public static HttpEntity<byte[]> download(HttpServletResponse response,
        String fileName,
        String[] contentTypes,
        byte[] content) {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType(contentTypes[0], contentTypes[1]));
        header.set("Content-Disposition", "inline; filename=" + fileName);
        header.setContentLength(content.length);
        return new HttpEntity<>(content, header);
    }
}
