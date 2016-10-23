package by.danceform.app.dto.document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by dimonn12 on 22.10.2016.
 */
public class AttachedDocumentDTO extends DocumentDTO {

    @NotNull
    private Long entityId;

    private String contentContentType;

    public AttachedDocumentDTO() {
    }

    public AttachedDocumentDTO(Long id) {
        super(id);
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public String getContentContentType() {
        return contentContentType;
    }

    public void setContentContentType(String contentContentType) {
        this.contentContentType = contentContentType;
    }
}
