package by.danceform.app.dto.document;

import javax.validation.constraints.NotNull;

/**
 * Created by dimonn12 on 22.10.2016.
 */
public class AttachedDocumentDTO extends DocumentDTO {

    @NotNull
    private Long entityId;

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

}
