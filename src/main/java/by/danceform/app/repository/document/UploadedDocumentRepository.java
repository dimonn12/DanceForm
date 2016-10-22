package by.danceform.app.repository.document;

import by.danceform.app.domain.document.UploadedDocument;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the UploadedDocument entity.
 */
public interface UploadedDocumentRepository extends JpaRepository<UploadedDocument, Long> {

}
