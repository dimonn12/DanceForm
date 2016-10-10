package by.danceform.app.dto.competition;

import by.danceform.app.domain.competition.CompetitionCategoryWithDetails;
import by.danceform.app.dto.AbstractDomainDTO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;


/**
 * A DTO for the Competition entity.
 */
public class CompetitionWithDetailsDTO extends CompetitionDTO {

    private List<CompetitionCategoryWithDetailsDTO> competitionCategoryDTOs;

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

}
