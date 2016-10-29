package by.danceform.app.converter.system;

import by.danceform.app.converter.AbstractConverter;
import by.danceform.app.domain.system.SystemSetting;
import by.danceform.app.dto.system.SystemSettingDTO;
import org.springframework.stereotype.Component;

/**
 * Created by dimonn12 on 07.10.2016.
 */
@Component("systemSettingConverter")
public class SystemSettingConverter extends AbstractConverter<SystemSetting, SystemSettingDTO, Long> {

    @Override
    protected SystemSettingDTO convertEntityToDto(SystemSetting entity, SystemSettingDTO dto) {
        dto.setName(trimIfNull(entity.getName()));
        dto.setValue(trimIfNull(entity.getValue()));
        return dto;
    }

    @Override
    public SystemSetting convertDtoToEntity(SystemSettingDTO dto, SystemSetting entity) {
        entity.setName(trimIfNull(dto.getName()));
        entity.setValue(trimIfNull(dto.getValue()));
        return entity;
    }

    @Override
    protected SystemSettingDTO getNewDTO() {
        return new SystemSettingDTO();
    }

    @Override
    protected SystemSetting getNewEntity() {
        return new SystemSetting();
    }
}
