package by.danceform.app.converter.config;

import by.danceform.app.converter.AbstractConverter;
import by.danceform.app.converter.NamedEntityConverter;
import by.danceform.app.domain.config.DanceClass;
import by.danceform.app.dto.config.DanceClassDTO;
import by.danceform.app.repository.config.DanceClassRepository;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

/**
 * Created by dimonn12 on 07.10.2016.
 */
@Component("danceClassConverter")
public class DanceClassConverter extends AbstractConverter<DanceClass, DanceClassDTO, Long> {

    @Inject
    private DanceClassRepository danceClassRepository;

    @Inject
    private NamedEntityConverter<DanceClass> namedEntityConverter;

    @Override
    protected DanceClassDTO convertEntityToDto(DanceClass entity, DanceClassDTO dto) {
        dto.setName(trimIfNull(entity.getName()));
        dto.setDescription(trimIfNull(entity.getDescription()));
        dto.setSymbol(trimIfNull(entity.getSymbol()));
        dto.setTransferScore(entity.getTransferScore());
        dto.setWeight(entity.getWeight());
        if(null != entity.getNextDanceClass()) {
            dto.setNextDanceClass(namedEntityConverter.convertToDto(entity.getNextDanceClass()));
        }
        return dto;
    }

    @Override
    protected DanceClass convertDtoToEntity(DanceClassDTO dto, DanceClass entity) {
        entity.setDescription(trimIfNull(dto.getDescription()));
        entity.setName(trimIfNull(dto.getName()));
        entity.setWeight(dto.getWeight());
        entity.setTransferScore(dto.getTransferScore());
        entity.setSymbol(trimIfNull(dto.getSymbol()));
        if(null != dto.getNextDanceClass()) {
            entity.setNextDanceClass(danceClassRepository.findOne(dto.getNextDanceClass().getId()));
        }
        return entity;
    }

    @Override
    protected DanceClassDTO getNewDTO() {
        return new DanceClassDTO();
    }

    @Override
    protected DanceClass getNewEntity() {
        return new DanceClass();
    }
}
