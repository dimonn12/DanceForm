package by.danceform.app.converter.config;

import by.danceform.app.converter.AbstractConverter;
import by.danceform.app.domain.config.AgeCategory;
import by.danceform.app.dto.config.AgeCategoryDTO;
import org.springframework.stereotype.Component;

/**
 * Created by dimonn12 on 07.10.2016.
 */
@Component("ageCategoryConverter")
public class AgeCategoryConverter extends AbstractConverter<AgeCategory, AgeCategoryDTO, Long> {

    @Override
    protected AgeCategoryDTO convertEntityToDto(AgeCategory entity, AgeCategoryDTO dto) {
        dto.setName(trimIfNull(entity.getName()));
        dto.setMaxAge(entity.getMaxAge());
        dto.setMinAge(entity.getMinAge());
        return dto;
    }

    @Override
    protected AgeCategory convertDtoToEntity(AgeCategoryDTO dto, AgeCategory entity) {
        entity.setName(trimIfNull(dto.getName()));
        entity.setMinAge(dto.getMinAge());
        entity.setMaxAge(dto.getMaxAge());
        return entity;
    }

    @Override
    protected AgeCategoryDTO getNewDTO() {
        return new AgeCategoryDTO();
    }

    @Override
    protected AgeCategory getNewEntity() {
        return new AgeCategory();
    }
}
