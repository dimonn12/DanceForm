package by.danceform.app.converter.couple;

import by.danceform.app.converter.AbstractConverter;
import by.danceform.app.domain.couple.RegisteredCouple;
import by.danceform.app.dto.couple.RegisteredCoupleDTO;
import by.danceform.app.repository.competition.CompetitionCategoryRepository;
import by.danceform.app.repository.config.DanceClassRepository;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Arrays;

/**
 * Created by dimonn12 on 09.10.2016.
 */
@Component("registeredCoupleConverter")
public class RegisteredCoupleConverter extends AbstractConverter<RegisteredCouple, RegisteredCoupleDTO, Long> {

    @Inject
    private DanceClassRepository danceClassRepository;

    @Override
    protected RegisteredCoupleDTO convertEntityToDto(RegisteredCouple entity, RegisteredCoupleDTO dto) {
        dto.setPartner1Name(entity.getPartner1Name());
        dto.setPartner1Surname(entity.getPartner1Surname());
        dto.setPartner1DateOfBirth(entity.getPartner1DateOfBirth());

        dto.setPartner2Name(entity.getPartner2Name());
        dto.setPartner2Surname(entity.getPartner2Surname());
        dto.setPartner2DateOfBirth(entity.getPartner2DateOfBirth());

        dto.setTrainer1(entity.getTrainer1());
        dto.setLocation(entity.getLocation());
        dto.setOrganization(entity.getOrganization());

        if(null != entity.getPartner1DanceClassST()) {
            dto.setPartner1DanceClassSTId(entity.getPartner1DanceClassST().getId());
        }
        if(null != entity.getPartner2DanceClassST()) {
            dto.setPartner2DanceClassSTId(entity.getPartner2DanceClassST().getId());
        }
        if(null != entity.getPartner1DanceClassLA()) {
            dto.setPartner1DanceClassLAId(entity.getPartner1DanceClassLA().getId());
        }
        if(null != entity.getPartner2DanceClassLA()) {
            dto.setPartner2DanceClassLAId(entity.getPartner2DanceClassLA().getId());
        }
        if(null != entity.getCompetitionCategory()) {
            dto.setCompetitionCategoryIds(Arrays.asList(entity.getCompetitionCategory().getId()));
        }
        return dto;
    }

    @Override
    protected RegisteredCouple convertDtoToEntity(RegisteredCoupleDTO dto, RegisteredCouple entity) {
        entity.setPartner1Name(dto.getPartner1Name());
        entity.setPartner1Surname(dto.getPartner1Surname());
        entity.setPartner1DateOfBirth(dto.getPartner1DateOfBirth());

        entity.setPartner2Name(dto.getPartner2Name());
        entity.setPartner2Surname(dto.getPartner2Surname());
        entity.setPartner2DateOfBirth(dto.getPartner2DateOfBirth());

        entity.setTrainer1(dto.getTrainer1());
        entity.setLocation(dto.getLocation());
        entity.setOrganization(dto.getOrganization());

        entity.setPartner1DanceClassLA(danceClassRepository.findOne(dto.getPartner1DanceClassLAId()));
        entity.setPartner2DanceClassLA(danceClassRepository.findOne(dto.getPartner2DanceClassLAId()));
        entity.setPartner1DanceClassST(danceClassRepository.findOne(dto.getPartner1DanceClassSTId()));
        entity.setPartner2DanceClassST(danceClassRepository.findOne(dto.getPartner2DanceClassSTId()));

        return entity;
    }

    @Override
    protected RegisteredCoupleDTO getNewDTO() {
        return new RegisteredCoupleDTO();
    }

    @Override
    protected RegisteredCouple getNewEntity() {
        return new RegisteredCouple();
    }
}
