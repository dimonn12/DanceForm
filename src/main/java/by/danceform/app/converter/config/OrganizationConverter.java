package by.danceform.app.converter.config;

import by.danceform.app.converter.AbstractConverter;
import by.danceform.app.domain.config.Organization;
import by.danceform.app.dto.config.OrganizationDTO;
import org.springframework.stereotype.Component;

/**
 * Created by Dmitry_Shanko on 10/17/2016.
 */
@Component("organizationConverter")
public class OrganizationConverter extends AbstractConverter<Organization, OrganizationDTO, Long> {

    @Override
    protected OrganizationDTO convertEntityToDto(Organization entity, OrganizationDTO dto) {
        dto.setName(entity.getName());
        dto.setVisible(entity.isVisible());
        return dto;
    }

    @Override
    protected Organization convertDtoToEntity(OrganizationDTO dto, Organization entity) {
        entity.setName(dto.getName());
        entity.setVisible(dto.isVisible());
        return entity;
    }

    @Override
    protected OrganizationDTO getNewDTO() {
        return new OrganizationDTO();
    }

    @Override
    protected Organization getNewEntity() {
        return new Organization();
    }
}
