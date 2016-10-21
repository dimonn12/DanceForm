package by.danceform.app.converter.document;

import by.danceform.app.converter.AbstractConverter;
import by.danceform.app.domain.document.UploadedDocument;
import by.danceform.app.dto.document.UploadedDocumentDTO;
import org.springframework.stereotype.Component;

/**
 * Created by Dmitry_Shanko on 10/21/2016.
 */
@Component("uploadedDocumentConverter")
public class UploadedDocumentConverter extends AbstractConverter<UploadedDocument, UploadedDocumentDTO, Long> {

    @Override
    protected UploadedDocumentDTO convertEntityToDto(UploadedDocument entity, UploadedDocumentDTO dto) {
        dto.setName(entity.getName());
        dto.setContentContentType(entity.getContentContentType());
        dto.setExtension(entity.getExtension());
        dto.setExternalPath(entity.getExternalPath());
        dto.setPath(entity.getPath());
        dto.setUploadedBy(entity.getUploadedBy());
        dto.setUploadedDate(entity.getUploadedDate());
        dto.setContent(entity.getContent());
        return dto;
    }

    @Override
    protected UploadedDocument convertDtoToEntity(UploadedDocumentDTO dto, UploadedDocument entity) {
        entity.setExternalPath(dto.getExternalPath());
        entity.setPath(dto.getPath());
        entity.setContentContentType(dto.getContentContentType());
        entity.setName(dto.getName());
        entity.setExtension(dto.getExtension());
        entity.setContent(dto.getContent());
        return entity;
    }

    @Override
    protected UploadedDocumentDTO getNewDTO() {
        return new UploadedDocumentDTO();
    }

    @Override
    protected UploadedDocument getNewEntity() {
        return new UploadedDocument();
    }
}
