package by.danceform.app.converter.config;

import by.danceform.app.converter.AbstractConverter;
import by.danceform.app.domain.config.Location;
import by.danceform.app.dto.config.LocationDTO;
import org.springframework.stereotype.Component;

/**
 * Created by Dmitry_Shanko on 10/17/2016.
 */
@Component("locationConverter")
public class LocationConverter extends AbstractConverter<Location, LocationDTO, Long> {

    @Override
    protected LocationDTO convertEntityToDto(Location entity, LocationDTO dto) {
        dto.setName(entity.getName());
        return dto;
    }

    @Override
    protected Location convertDtoToEntity(LocationDTO dto, Location entity) {
        entity.setName(dto.getName());
        return entity;
    }

    @Override
    protected LocationDTO getNewDTO() {
        return new LocationDTO();
    }

    @Override
    protected Location getNewEntity() {
        return new Location();
    }
}
