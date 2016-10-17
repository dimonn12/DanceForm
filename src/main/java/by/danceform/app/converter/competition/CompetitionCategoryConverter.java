package by.danceform.app.converter.competition;

import by.danceform.app.converter.AbstractConverter;
import by.danceform.app.converter.NamedEntityConverter;
import by.danceform.app.converter.config.AgeCategoryConverter;
import by.danceform.app.converter.config.DanceCategoryConverter;
import by.danceform.app.converter.config.DanceClassConverter;
import by.danceform.app.domain.competition.CompetitionCategory;
import by.danceform.app.domain.config.AgeCategory;
import by.danceform.app.domain.config.DanceCategory;
import by.danceform.app.domain.config.DanceClass;
import by.danceform.app.domain.config.enums.DanceCategoryEnum;
import by.danceform.app.dto.competition.CompetitionCategoryDTO;
import by.danceform.app.repository.config.AgeCategoryRepository;
import by.danceform.app.repository.config.DanceCategoryRepository;
import by.danceform.app.repository.config.DanceClassRepository;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by dimonn12 on 09.10.2016.
 */
@Component("competitionCategoryConverter")
public class CompetitionCategoryConverter extends AbstractConverter<CompetitionCategory, CompetitionCategoryDTO, Long> {

    @Inject
    private DanceCategoryRepository danceCategoryRepository;

    @Inject
    private DanceClassRepository danceClassRepository;

    @Inject
    private AgeCategoryRepository ageCategoryRepository;

    @Inject
    private AgeCategoryConverter ageCategoryConverter;

    @Inject
    private DanceClassConverter danceClassConverter;

    @Inject
    private NamedEntityConverter<DanceCategory> danceCategoryNamedEntityConverter;

    @Inject
    private NamedEntityConverter<DanceClass> namedEntityDanceClassConverter;

    @Inject
    private NamedEntityConverter<AgeCategory> namedEntityAgeCategoryConverter;

    @Override
    protected CompetitionCategoryDTO convertEntityToDto(CompetitionCategory entity, CompetitionCategoryDTO dto) {
        dto.setDescription(entity.getDescription());
        dto.setName(entity.getName());
        dto.setActive(entity.isActive());
        dto.setCheckMaxAge(entity.isCheckMaxAge());
        dto.setCheckMinAge(entity.isCheckMinAge());
        dto.setCompetitionId(entity.getCompetitionId());
        dto.setDanceCategory(danceCategoryNamedEntityConverter.convertToDto(entity.getDanceCategory()));
        dto.setAgeCategories(new HashSet<>(namedEntityAgeCategoryConverter.convertToDtos(entity.getAgeCategories())));
        dto.setDanceClasses(new HashSet<>(namedEntityDanceClassConverter.convertToDtos(entity.getDanceClasses())));
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

        if(null != dto.getDanceCategory()) {
            entity.setDanceCategory(new DanceCategory(DanceCategoryEnum.valueOf(dto.getDanceCategory())));
        }

        if(null != dto.getAgeCategories()) {
            Set<Long> ageCategoriesIds = dto.getAgeCategories()
                .stream()
                .map(ac -> ac.getId())
                .collect(Collectors.toSet());
            entity.setAgeCategories(new HashSet<>(ageCategoryRepository.findAll(ageCategoriesIds)));
        } else {
            entity.setAgeCategories(new HashSet<>());
        }
        if(null != dto.getDanceClasses()) {
            Set<Long> danceClassesIds = dto.getDanceClasses()
                .stream()
                .map(dc -> dc.getId())
                .collect(Collectors.toSet());
            entity.setDanceClasses(new HashSet<>(danceClassRepository.findAll(danceClassesIds)));
        } else {
            entity.setDanceClasses(new HashSet<>());
        }

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
