package by.danceform.app.service.mapper;

import by.danceform.app.domain.config.AgeCategory;
import by.danceform.app.dto.config.AgeCategoryDTO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Mapper for the entity AgeCategory and its DTO AgeCategoryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AgeCategoryMapper {

    AgeCategoryDTO ageCategoryToAgeCategoryDTO(AgeCategory ageCategory);

    List<AgeCategoryDTO> ageCategoriesToAgeCategoryDTOs(List<AgeCategory> ageCategories);

    AgeCategory ageCategoryDTOToAgeCategory(AgeCategoryDTO ageCategoryDTO);

    List<AgeCategory> ageCategoryDTOsToAgeCategories(List<AgeCategoryDTO> ageCategoryDTOs);
}
