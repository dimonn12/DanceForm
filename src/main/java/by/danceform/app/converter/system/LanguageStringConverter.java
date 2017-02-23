package by.danceform.app.converter.system;

import by.danceform.app.converter.AbstractConverter;
import by.danceform.app.domain.system.LanguageString;
import by.danceform.app.dto.system.LanguageStringDTO;
import org.springframework.stereotype.Component;

/**
 * Created by dimonn12 on 07.10.2016.
 */
@Component("languageStringConverter")
public class LanguageStringConverter extends AbstractConverter<LanguageString, LanguageStringDTO, Long> {

    @Override
    protected LanguageStringDTO convertEntityToDto(LanguageString entity, LanguageStringDTO dto) {
        dto.setLang(trimIfNull(entity.getLang()));
        dto.setName(trimIfNull(entity.getName()));
        dto.setValue(trimIfNull(entity.getValue()));
        return dto;
    }

    @Override
    public LanguageString convertDtoToEntity(LanguageStringDTO dto, LanguageString entity) {
        entity.setLang(trimIfNull(dto.getLang()));
        entity.setName(trimIfNull(dto.getName()));
        entity.setValue(trimIfNull(dto.getValue()));
        return entity;
    }

    @Override
    protected LanguageStringDTO getNewDTO() {
        return new LanguageStringDTO();
    }

    @Override
    protected LanguageString getNewEntity() {
        return new LanguageString();
    }
}
