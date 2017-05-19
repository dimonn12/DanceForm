package by.danceform.app.web.rest.util;

import by.danceform.app.config.ApplicationProperties;
import by.danceform.app.domain.document.UploadedDocument;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * Created by USER on 22.02.2017.
 */
@Service
public class DownloadService {

    @Inject
    private ApplicationProperties applicationProperties;

    public HttpEntity<byte[]> download(UploadedDocument doc, boolean doCache) {
        return download(doc.getFullName(),
            StringUtils.split(doc.getContentContentType(), "/"),
            doc.getContent(),
            doCache);
    }

    public HttpEntity<byte[]> download(String fileName, String[] contentTypes, byte[] content, boolean doCache) {
        return download(fileName, new MediaType(contentTypes[0], contentTypes[1]), content, doCache);
    }

    public HttpEntity<byte[]> download(String fileName, MediaType contentType, byte[] content, boolean doCache) {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(contentType);
        header.set("Content-Disposition", "inline; filename=" + fileName);
        header.setContentLength(content.length);
        if(doCache) {
            header.setCacheControl("max-age=" +
                                   applicationProperties.getHttp().getCache().getTimeToLiveInDays() * 24 * 60 * 60 +
                                   ", public");
        }
        return new HttpEntity<>(content, header);
    }
}
