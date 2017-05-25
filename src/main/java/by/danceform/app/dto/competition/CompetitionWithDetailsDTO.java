package by.danceform.app.dto.competition;

import by.danceform.app.dto.document.UploadedDocumentDTO;

import java.util.List;


/**
 * A DTO for the Competition entity.
 */
public class CompetitionWithDetailsDTO extends CompetitionDTO {

    private List<CompetitionCategoryWithDetailsDTO> competitionCategoryDTOs;

    private List<CompetitionNotificationDTO> competitionNotificationDTOs;

    private UploadedDocumentDTO uploadedDocumentDTO;

    private Integer amountOfUniqueRegisteredPairs;

    public CompetitionWithDetailsDTO() {
    }

    public CompetitionWithDetailsDTO(Long id) {
        super(id);
    }

    public List<CompetitionCategoryWithDetailsDTO> getCompetitionCategoryDTOs() {
        return competitionCategoryDTOs;
    }

    public void setCompetitionCategoryDTOs(List<CompetitionCategoryWithDetailsDTO> competitionCategoryDTOs) {
        this.competitionCategoryDTOs = competitionCategoryDTOs;
    }

    public List<CompetitionNotificationDTO> getCompetitionNotificationDTOs() {
        return competitionNotificationDTOs;
    }

    public void setCompetitionNotificationDTOs(List<CompetitionNotificationDTO> competitionNotificationDTOs) {
        this.competitionNotificationDTOs = competitionNotificationDTOs;
    }

    public UploadedDocumentDTO getUploadedDocumentDTO() {
        return uploadedDocumentDTO;
    }

    public void setUploadedDocumentDTO(UploadedDocumentDTO uploadedDocumentDTO) {
        this.uploadedDocumentDTO = uploadedDocumentDTO;
    }

    public Integer getAmountOfUniqueRegisteredPairs() {
        return amountOfUniqueRegisteredPairs;
    }

    public void setAmountOfUniqueRegisteredPairs(Integer amountOfUniqueRegisteredPairs) {
        this.amountOfUniqueRegisteredPairs = amountOfUniqueRegisteredPairs;
    }

}
