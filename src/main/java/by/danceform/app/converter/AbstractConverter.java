package by.danceform.app.converter;

import by.danceform.app.domain.AbstractEntity;
import by.danceform.app.dto.AbstractDomainDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dimonn12 on 07.10.2016.
 */
public abstract class AbstractConverter<T extends AbstractEntity<I>, D extends AbstractDomainDTO<I>, I extends Serializable>
    extends Converter<T, D> {

    @Override
    public final D convertToDto(final T entity) {
        if(entity == null) {
            return null;
        }
        final D dto = getNewDtoWithEntityId(entity);
        convertEntityToDto(entity, dto);
        return dto;
    }

    private D getNewDtoWithEntityId(final T entity) {
        final D dto = getNewDTO();
        setEntityIdToDto(entity, dto);
        return dto;
    }

    @Override
    public final List<D> convertToDtos(List<T> entities) {
        List<D> dtos = null;
        if(entities != null) {
            dtos = new ArrayList<>();
            for(T entity : entities) {
                dtos.add(getNewDtoWithEntityId(entity));
            }
            convertEntitiesToDtos(entities, dtos);
        }
        return dtos;
    }

    @Override
    public final T convertToEntity(D dto) {
        if(dto == null) {
            return null;
        }
        final T entity = getNewEntity();
        setDtoIdToEntity(entity, dto);
        convertDtoToEntity(dto, entity);
        return entity;
    }

    @Override
    public final List<T> convertToEntities(List<D> dtos) {
        List<T> result = null;
        if(dtos != null) {
            final List<T> entities = new ArrayList<>();
            dtos.forEach(e -> entities.add(getNewEntity()));
            convertDtosToEntities(entities, dtos);
            result = entities;
        }
        return result;
    }

    /**
     * Convert a list of not-null entity objects to a list of not-null DTOs.
     *
     * @param dtos     a list of empty DTOs with entity object id, not <code>null</code>
     * @param entities a list of entity objects, not <code>null</code>
     */
    protected List<D> convertEntitiesToDtos(List<T> entities, List<D> dtos) {
        int index = 0;
        for(D dto : dtos) {
            convertEntityToDto(entities.get(index++), dto);
        }
        return dtos;
    }

    /**
     * Convert not-null entity object to not-null DTO.
     *
     * @param dto    empty DTO with entity object id, not <code>null</code>
     * @param entity entity object, not <code>null</code>
     */
    protected abstract D convertEntityToDto(T entity, D dto);

    /**
     * Converts a list of not-null DTOs to a list of not-null entity objects.
     *
     * @param dtos     a list of empty DTOs with entity object id, not <code>null</code>
     * @param entities a list of entity objects, not <code>null</code>
     */
    protected List<T> convertDtosToEntities(List<T> entities, List<D> dtos) {
        int index = 0;
        for(D dto : dtos) {
            convertDtoToEntity(dto, entities.get(index++));
        }
        return entities;
    }

    /**
     * Convert DTO object to entity.
     *
     * @param dto    DTO object
     * @param entity entity object
     */
    protected abstract T convertDtoToEntity(D dto, T entity);

    /**
     * Gets an empty DTO.
     *
     * @return DTO object
     */
    protected abstract D getNewDTO();

    /**
     * Gets a new entity.
     *
     * @return entity object
     */
    protected abstract T getNewEntity();

    /**
     * Sets the entity id to the dto.
     *
     * @param entity entity object
     * @param dto    DTO object
     */
    protected void setEntityIdToDto(T entity, D dto) {
        dto.setId(entity.getId());
    }

    /**
     * Sets the dto id to the entity.
     *
     * @param entity entity object
     * @param dto    DTO object
     */
    protected void setDtoIdToEntity(T entity, D dto) {
        entity.setId(dto.getId());
    }
}
