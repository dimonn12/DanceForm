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
        return null;
    }

    @Override
    protected UploadedDocument convertDtoToEntity(UploadedDocumentDTO dto, UploadedDocument entity) {
        return null;
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
