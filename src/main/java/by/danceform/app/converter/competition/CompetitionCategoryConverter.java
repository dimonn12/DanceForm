package by.danceform.app.converter.competition;

import by.danceform.app.converter.AbstractConverter;
import by.danceform.app.converter.config.AgeCategoryConverter;
import by.danceform.app.converter.config.DanceClassConverter;
import by.danceform.app.domain.competition.CompetitionCategory;
import by.danceform.app.dto.competition.CompetitionCategoryDTO;
import by.danceform.app.dto.config.AgeCategoryDTO;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by dimonn12 on 09.10.2016.
 */
@Component("competitionCategoryConverter")
public class CompetitionCategoryConverter extends AbstractConverter<CompetitionCategory, CompetitionCategoryDTO, Long> {

    @Inject
    private AgeCategoryConverter ageCategoryConverter;

    @Inject
    private DanceClassConverter danceClassConverter;

    @Override
    protected CompetitionCategoryDTO convertEntityToDto(CompetitionCategory entity, CompetitionCategoryDTO dto) {
        dto.setDescription(entity.getDescription());
        dto.setName(entity.getName());
        dto.setActive(entity.isActive());
        dto.setCheckMaxAge(entity.isCheckMaxAge());
        dto.setCheckMinAge(entity.isCheckMinAge());
        dto.setCompetitionId(entity.getCompetitionId());
        dto.setAgeCategories(new HashSet<>(ageCategoryConverter.convertToDtos(new ArrayList<>(entity.getAgeCategories()))));
        dto.setDanceClasses(new HashSet<>(danceClassConverter.convertToDtos(new ArrayList<>(entity.getDanceClasses()))));
        return dto;
    }

    @Override
    protected CompetitionCategory convertDtoToEntity(CompetitionCategoryDTO dto, CompetitionCategory entity) {
        entity.setDescription(dto.getDescription());
        entity.setName(dto.getName());
        entity.setActive(dto.getActive());
        entity.setCheckMinAge(dto.getCheckMinAge());
        entity.setCheckMaxAge(dto.getCheckMaxAge());
        entity.setCompetitionId(dto.getCompetitionId());
        entity.setAgeCategories(new HashSet<>(ageCategoryConverter.convertToEntities(new ArrayList<>(dto.getAgeCategories()))));
        entity.setDanceClasses(new HashSet<>(danceClassConverter.convertToEntities(new ArrayList<>(dto.getDanceClasses()))));
        return entity;
    }

    @Override
    protected CompetitionCategoryDTO getNewDTO() {
        return new CompetitionCategoryDTO();
    }

    @Override
    protected CompetitionCategory getNewEntity() {
        return new CompetitionCategory();
    }
}
