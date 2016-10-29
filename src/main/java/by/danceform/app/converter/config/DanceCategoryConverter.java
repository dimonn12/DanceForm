package by.danceform.app.converter.config;

import by.danceform.app.converter.AbstractConverter;
import by.danceform.app.domain.config.DanceCategory;
import by.danceform.app.dto.config.DanceCategoryDTO;
import org.springframework.stereotype.Component;

/**
 * Created by Dmitry_Shanko on 10/17/2016.
 */
@Component("danceCategoryConverter")
public class DanceCategoryConverter extends AbstractConverter<DanceCategory, DanceCategoryDTO, Long> {

    @Override
    protected DanceCategoryDTO convertEntityToDto(DanceCategory entity, DanceCategoryDTO dto) {
        dto.setName(trimIfNull(entity.getName()));
        return dto;
    }

    @Override
    protected DanceCategory convertDtoToEntity(DanceCategoryDTO dto, DanceCategory entity) {
        entity.setName(trimIfNull(dto.getName()));
        return entity;
    }

    @Override
    protected DanceCategoryDTO getNewDTO() {
        return new DanceCategoryDTO();
    }

    @Override
    protected DanceCategory getNewEntity() {
        return new DanceCategory();
    }
}
