package by.danceform.app.domain.document;


import by.danceform.app.domain.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A UploadedDocument.
 */
@Entity
@Table(name = "uploaded_document")
public class UploadedDocument extends AbstractEntity<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "name", length = 128, nullable = false)
    private String name;

    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "extension", length = 8, nullable = false)
    private String extension;

    @NotNull
    @Size(min = 1, max = 512)
    @Column(name = "path", length = 512, nullable = false)
    private String path;

    @Column(name = "external_path")
    private String externalPath;

    @Lob
    @Column(name = "content")
    private byte[] content;

    @Column(name = "content_content_type")
    private String contentContentType;

    @Column(name = "uploaded_date")
    private ZonedDateTime uploadedDate;

    @Column(name = "uploaded_by", length = 64)
    private String uploadedBy;

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

    public String getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    @Override
    public String toString() {
        return "UploadedDocument{" +
               "id=" + id +
               ", name='" + name + "'" +
               ", extension='" + extension + "'" +
               ", path='" + path + "'" +
               ", externalPath='" + externalPath + "'" +
               ", content='" + content + "'" +
               ", contentContentType='" + contentContentType + "'" +
               ", uploadedDate='" + uploadedDate + "'" +
               ", uploadedBy='" + uploadedBy + "'" +
               '}';
    }
}
