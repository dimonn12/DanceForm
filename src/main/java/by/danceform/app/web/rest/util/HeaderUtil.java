package by.danceform.app.web.rest.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

/**
 * Utility class for HTTP headers creation.
 */
public class HeaderUtil {

    private static final Logger log = LoggerFactory.getLogger(HeaderUtil.class);

    public static HttpHeaders createAlert(String message, String param) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-danceFormApp-alert", message);
        headers.add("X-danceFormApp-params", param);
        return headers;
    }

    public static HttpHeaders createEntityCreationAlert(String entityName, String param) {
        return createAlert("danceFormApp." + entityName + ".created", param);
    }

    public static HttpHeaders createEntityUpdateAlert(String entityName, String param) {
        return createAlert("danceFormApp." + entityName + ".updated", param);
    }

    public static HttpHeaders createEntityDeletionAlert(String entityName, String param) {
        return createAlert("danceFormApp." + entityName + ".deleted", param);
    }

    public static HttpHeaders createFailureAlert(String entityName, String errorKey, String defaultMessage) {
        log.error("Entity creation failed, {}", defaultMessage);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-danceFormApp-error", "error." + errorKey);
        headers.add("X-danceFormApp-params", entityName);
        return headers;
    }
}
