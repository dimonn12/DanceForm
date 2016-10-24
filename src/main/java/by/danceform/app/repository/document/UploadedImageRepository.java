package by.danceform.app.repository.document;

import by.danceform.app.domain.document.UploadedImage;

import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the UploadedImage entity.
 */
public interface UploadedImageRepository extends JpaRepository<UploadedImage,Long> {

}
