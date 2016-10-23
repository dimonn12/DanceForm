package by.danceform.app.repository.competition;

import by.danceform.app.domain.competition.Competition;
import by.danceform.app.domain.competition.CompetitionWithDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Competition entity.
 */
public interface CompetitionRepository extends JpaRepository<Competition, Long> {

    @Query(
        "SELECT NEW CompetitionWithDetails(competition," +
        "(SELECT uploadedDocument FROM UploadedDocument uploadedDocument WHERE uploadedDocument.id = competition.detailsDocumentId))" +
        " FROM Competition competition WHERE competition.id = :id")
    CompetitionWithDetails findOneWithDetails(@Param("id") Long id);

    @Query("SELECT competition FROM Competition competition WHERE competition.visible = true")
    List<Competition> findVisible();

    @Modifying(clearAutomatically = true)
    @Query(
        "UPDATE FROM Competition comp SET comp.detailsDocumentId = null WHERE comp.detailsDocumentId = :detailsDocumentId")
    void updateDocumentAttachment(@Param("detailsDocumentId") Long detailsDocumentId);

}
