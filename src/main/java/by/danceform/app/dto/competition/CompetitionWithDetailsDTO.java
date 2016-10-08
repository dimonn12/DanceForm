package by.danceform.app.dto.competition;

import by.danceform.app.dto.AbstractDomainDTO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;


/**
 * A DTO for the Competition entity.
 */
public class CompetitionWithDetailsDTO extends CompetitionDTO {

    public CompetitionWithDetailsDTO() {
    }

    public CompetitionWithDetailsDTO(Long id) {
        super(id);
    }

}
