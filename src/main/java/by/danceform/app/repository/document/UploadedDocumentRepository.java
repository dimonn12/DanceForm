package by.danceform.app.repository.document;

import by.danceform.app.domain.document.UploadedDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data JPA repository for the UploadedDocument entity.
 */
public interface UploadedDocumentRepository extends JpaRepository<UploadedDocument, Long> {

    @Query("SELECT doc FROM UploadedDocument doc WHERE doc.fullName = :fileName")
    UploadedDocument findByName(@Param("fileName") String fileName);
}
