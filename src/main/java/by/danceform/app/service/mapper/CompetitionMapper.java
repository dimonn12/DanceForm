package by.danceform.app.service.mapper;

import by.danceform.app.domain.*;
import by.danceform.app.service.dto.CompetitionDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Competition and its DTO CompetitionDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CompetitionMapper {

    CompetitionDTO competitionToCompetitionDTO(Competition competition);

    List<CompetitionDTO> competitionsToCompetitionDTOs(List<Competition> competitions);

    Competition competitionDTOToCompetition(CompetitionDTO competitionDTO);

    List<Competition> competitionDTOsToCompetitions(List<CompetitionDTO> competitionDTOs);
}
