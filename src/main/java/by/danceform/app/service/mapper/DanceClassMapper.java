package by.danceform.app.service.mapper;

import by.danceform.app.domain.*;
import by.danceform.app.service.dto.DanceClassDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity DanceClass and its DTO DanceClassDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DanceClassMapper {

    @Mapping(source = "danceClass.id", target = "danceClassId")
    @Mapping(source = "danceClass.name", target = "danceClassName")
    DanceClassDTO danceClassToDanceClassDTO(DanceClass danceClass);

    List<DanceClassDTO> danceClassesToDanceClassDTOs(List<DanceClass> danceClasses);

    @Mapping(source = "danceClassId", target = "danceClass")
    DanceClass danceClassDTOToDanceClass(DanceClassDTO danceClassDTO);

    List<DanceClass> danceClassDTOsToDanceClasses(List<DanceClassDTO> danceClassDTOs);

    default DanceClass danceClassFromId(Long id) {
        if (id == null) {
            return null;
        }
        DanceClass danceClass = new DanceClass();
        danceClass.setId(id);
        return danceClass;
    }
}
