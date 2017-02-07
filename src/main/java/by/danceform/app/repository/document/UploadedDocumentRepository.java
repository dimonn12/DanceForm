package by.danceform.app.repository.document;

import by.danceform.app.domain.document.UploadedDocument;
import by.danceform.app.repository.AbstractEntityRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data JPA repository for the UploadedDocument entity.
 */
public interface UploadedDocumentRepository extends AbstractEntityRepository<UploadedDocument, Long> {

}
