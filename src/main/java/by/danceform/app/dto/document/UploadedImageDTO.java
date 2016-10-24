package by.danceform.app.dto.document;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;


/**
 * A DTO for the UploadedImage entity.
 */
public class UploadedImageDTO extends UploadedDocumentDTO {

    @Override
    public String toString() {
        return "UploadedImageDTO{" +
            "id=" + id +
            ", fullName='" + getFullName() + "'" +
            ", path='" + getPath() + "'" +
            ", externalPath='" + getExternalPath() + "'" +
            ", uploadedBy='" + getUploadedBy() + "'" +
            ", uploadedDate='" + getUploadedDate() + "'" +
            ", contentType='" + getContentContentType() + "'" +
            '}';
    }
}
