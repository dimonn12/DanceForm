package by.danceform.app.dto.document;

import by.danceform.app.dto.AbstractDomainDTO;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;


/**
 * A DTO for the UploadedDocument entity.
 */
public class UploadedDocumentDTO extends AbstractDomainDTO<Long> {

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

    @Lob
    private byte[] content;

    private String contentContentType;
    private ZonedDateTime uploadedDate;

    private Integer uploadedBy;

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

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
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

    public Integer getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(Integer uploadedBy) {
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
               ", content='" + content + "'" +
               ", uploadedDate='" + uploadedDate + "'" +
               ", uploadedBy='" + uploadedBy + "'" +
               '}';
    }
}
