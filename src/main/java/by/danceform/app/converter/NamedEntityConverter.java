package by.danceform.app.converter;

import by.danceform.app.domain.INamedEntity;
import by.danceform.app.dto.NamedReferenceDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by dimonn12 on 10.10.2016.
 */
@Component("namedEntityConverter")
public class NamedEntityConverter<T extends INamedEntity> {

    public NamedReferenceDTO convertToDto(T entity) {
        NamedReferenceDTO namedReferenceDTO = new NamedReferenceDTO();
        namedReferenceDTO.setName(entity.getName());
        namedReferenceDTO.setId(entity.getId());
        return namedReferenceDTO;
    }

    public List<NamedReferenceDTO> convertToDtos(Collection<T> entities) {
        List<NamedReferenceDTO> dtos = new ArrayList<>();
        for(T entity : entities) {
            dtos.add(convertToDto(entity));
        }
        return dtos;
    }

}
