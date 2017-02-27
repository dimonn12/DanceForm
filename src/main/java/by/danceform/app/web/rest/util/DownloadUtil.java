package by.danceform.app.web.rest.util;

import by.danceform.app.domain.document.UploadedDocument;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

/**
 * Created by USER on 22.02.2017.
 */
public class DownloadUtil {

    public static HttpEntity<byte[]> download(UploadedDocument doc, boolean doCache) {
        return download(doc.getFullName(),
            StringUtils.split(doc.getContentContentType(), "/"),
            doc.getContent(),
            doCache);
    }

    public static HttpEntity<byte[]> download(String fileName, String[] contentTypes, byte[] content, boolean doCache) {
        return download(fileName, new MediaType(contentTypes[0], contentTypes[1]), content, doCache);
    }

    public static HttpEntity<byte[]> download(String fileName, MediaType contentType, byte[] content, boolean doCache) {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(contentType);
        header.set("Content-Disposition", "inline; filename=" + fileName);
        header.setContentLength(content.length);
        if(doCache) {
            header.setCacheControl("public");
        }
        return new HttpEntity<>(content, header);
    }
}
