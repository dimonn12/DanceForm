package by.danceform.app.dto.document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.ZonedDateTime;


/**
 * A DTO for the UploadedDocument entity.
 */
public class UploadedDocumentDTO extends DocumentDTO {

    @NotNull
    @Size(min = 1, max = 128)
    private String name;

    @NotNull
    @Size(min = 1, max = 8)
    private String extension;

    @NotNull
    @Size(min = 1, max = 512)
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
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
               ", name='" + name + "'" +
               ", extension='" + extension + "'" +
               ", path='" + path + "'" +
               ", externalPath='" + externalPath + "'" +
               ", uploadedDate='" + uploadedDate + "'" +
               ", uploadedBy='" + uploadedBy + "'" +
               '}';
    }
}
