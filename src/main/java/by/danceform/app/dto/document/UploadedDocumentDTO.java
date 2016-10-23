package by.danceform.app.dto.document;

import by.danceform.app.dto.AbstractDomainDTO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.ZonedDateTime;


/**
 * A DTO for the UploadedDocument entity.
 */
public class UploadedDocumentDTO extends AbstractDomainDTO<Long> {

    @NotNull
    private String fullName;

    private String path;

    private String externalPath;

    private String contentContentType;
    private ZonedDateTime uploadedDate;

    private String uploadedBy;

    public UploadedDocumentDTO() {
    }

    public UploadedDocumentDTO(Long id) {
        super(id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getExternalPath() {
        return externalPath;
    }

    public void setExternalPath(String externalPath) {
        this.externalPath = externalPath;
    }

    public String getContentContentType() {
        return contentContentType;
    }

    public void setContentContentType(String contentContentType) {
        this.contentContentType = contentContentType;
    }

    public ZonedDateTime getUploadedDate() {
        return uploadedDate;
    }

    public void setUploadedDate(ZonedDateTime uploadedDate) {
        this.uploadedDate = uploadedDate;
    }

    public String getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    @Override
    public String toString() {
        return "UploadedDocumentDTO{" +
               "id=" + id +
               ", fullName='" + fullName + "'" +
               ", path='" + path + "'" +
               ", externalPath='" + externalPath + "'" +
               ", uploadedDate='" + uploadedDate + "'" +
               ", uploadedBy='" + uploadedBy + "'" +
               '}';
    }
}
