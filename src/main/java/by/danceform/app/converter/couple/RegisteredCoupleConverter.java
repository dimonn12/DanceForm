package by.danceform.app.converter.couple;

import by.danceform.app.converter.AbstractConverter;
import by.danceform.app.converter.NamedEntityConverter;
import by.danceform.app.domain.config.DanceClass;
import by.danceform.app.domain.couple.RegisteredCouple;
import by.danceform.app.dto.couple.RegisteredCoupleDTO;
import by.danceform.app.repository.config.DanceClassRepository;
import java.util.Arrays;
import java.util.Objects;
import javax.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * Created by dimonn12 on 09.10.2016.
 */
@Component("registeredCoupleConverter")
public class RegisteredCoupleConverter extends AbstractConverter<RegisteredCouple, RegisteredCoupleDTO, Long> {

    @Inject
    private DanceClassRepository danceClassRepository;

    @Inject
    private NamedEntityConverter<DanceClass> danceClassNamedEntityConverter;

    @Override
    protected RegisteredCoupleDTO convertEntityToDto(RegisteredCouple entity, RegisteredCoupleDTO dto) {
        fillSoloCoupleFields(dto);
        fillHobbyCoupleFields(dto);
        dto.setPartner1Name(trimIfNull(entity.getPartner1Name()));
        dto.setPartner1Surname(trimIfNull(entity.getPartner1Surname()));
        dto.setPartner1DateOfBirth(entity.getPartner1DateOfBirth());

        dto.setPartner2Name(trimIfNull(entity.getPartner2Name()));
        dto.setPartner2Surname(trimIfNull(entity.getPartner2Surname()));
        dto.setPartner2DateOfBirth(entity.getPartner2DateOfBirth());

        dto.setTrainer1(trimIfNull(entity.getTrainer1()));
        dto.setTrainer2(trimIfNull(entity.getTrainer2()));
        dto.setLocation(trimIfNull(entity.getLocation()));
        dto.setOrganization(trimIfNull(entity.getOrganization()));

        if (null != entity.getPartner1DanceClassST()) {
            dto.setPartner1DanceClassST(danceClassNamedEntityConverter.convertToDto(entity.getPartner1DanceClassST()));
        }
        if (null != entity.getPartner2DanceClassST()) {
            dto.setPartner2DanceClassST(danceClassNamedEntityConverter.convertToDto(entity.getPartner2DanceClassST()));
        }
        if (null != entity.getPartner1DanceClassLA()) {
            dto.setPartner1DanceClassLA(danceClassNamedEntityConverter.convertToDto(entity.getPartner1DanceClassLA()));
        }
        if (null != entity.getPartner2DanceClassLA()) {
            dto.setPartner2DanceClassLA(danceClassNamedEntityConverter.convertToDto(entity.getPartner2DanceClassLA()));
        }
        if (null != entity.getCompetitionCategory()) {
            dto.setCompetitionCategoryIds(Arrays.asList(entity.getCompetitionCategory().getId()));
        }
        setSoloCouple(entity, dto);
        setHobbyCouple(entity, dto);
        return dto;
    }

    @Override
    protected RegisteredCouple convertDtoToEntity(RegisteredCoupleDTO dto, RegisteredCouple entity) {
        entity.setPartner1Name(trimIfNull(dto.getPartner1Name()));
        entity.setPartner1Surname(trimIfNull(dto.getPartner1Surname()));
        entity.setPartner1DateOfBirth(dto.getPartner1DateOfBirth());

        entity.setPartner2Name(trimIfNull(dto.getPartner2Name()));
        entity.setPartner2Surname(trimIfNull(dto.getPartner2Surname()));
        entity.setPartner2DateOfBirth(dto.getPartner2DateOfBirth());

        entity.setTrainer1(trimIfNull(dto.getTrainer1()));
        entity.setTrainer2(trimIfNull(dto.getTrainer2()));
        entity.setLocation(trimIfNull(dto.getLocation()));
        entity.setOrganization(trimIfNull(dto.getOrganization()));

        if (null != dto.getPartner1DanceClassLA()) {
            entity.setPartner1DanceClassLA(danceClassRepository.findOne(dto.getPartner1DanceClassLA().getId()));
        }
        if (null != dto.getPartner2DanceClassLA()) {
            entity.setPartner2DanceClassLA(danceClassRepository.findOne(dto.getPartner2DanceClassLA().getId()));
        }
        if (null != dto.getPartner1DanceClassST()) {
            entity.setPartner1DanceClassST(danceClassRepository.findOne(dto.getPartner1DanceClassST().getId()));
        }
        if (null != dto.getPartner2DanceClassST()) {
            entity.setPartner2DanceClassST(danceClassRepository.findOne(dto.getPartner2DanceClassST().getId()));
        }

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

    protected void fillSoloCoupleFields(RegisteredCoupleDTO dto) {
        if (dto.isSoloCouple()) {
            dto.setPartner2DanceClassLA(null);
            dto.setPartner2DanceClassST(null);
            dto.setPartner2DateOfBirth(null);
            dto.setPartner2Name(null);
            dto.setPartner2Surname(null);
        }
    }

    protected void fillHobbyCoupleFields(RegisteredCoupleDTO dto) {
        if (dto.isHobbyCouple()) {
            DanceClass defaultDanceClass = danceClassRepository.findDefault();
            dto.setPartner1DanceClassLA(danceClassNamedEntityConverter.convertToDto(defaultDanceClass));
            dto.setPartner2DanceClassLA(danceClassNamedEntityConverter.convertToDto(defaultDanceClass));
            if (dto.isSoloCouple()) {
                dto.setPartner1DanceClassST(danceClassNamedEntityConverter.convertToDto(defaultDanceClass));
                dto.setPartner2DanceClassST(danceClassNamedEntityConverter.convertToDto(defaultDanceClass));
            }
        }
    }

    protected void setSoloCouple(RegisteredCouple entity, RegisteredCoupleDTO dto) {
        dto.setSoloCouple(StringUtils.isBlank(entity.getPartner2Name()) &&
            StringUtils.isBlank(entity.getPartner2Surname()) &&
            null == entity.getPartner2DateOfBirth() &&
            null == entity.getPartner2DanceClassST() &&
            null == entity.getPartner2DanceClassLA());
    }

    protected void setHobbyCouple(RegisteredCouple entity, RegisteredCoupleDTO dto) {
        boolean isHobbyCouple =
            Objects.equals(entity.getPartner1DanceClassLA().getId(), DanceClassRepository.DEFAULT_DANCE_CLASS_ID)
                && Objects
                .equals(entity.getPartner2DanceClassLA().getId(), DanceClassRepository.DEFAULT_DANCE_CLASS_ID);
        if (isHobbyCouple) {
            if (dto.isSoloCouple()) {
                dto.setHobbyCouple(true);
            } else {
                dto.setHobbyCouple(Objects.equals(entity.getPartner1DanceClassST().getId(),
                    DanceClassRepository.DEFAULT_DANCE_CLASS_ID) && Objects
                    .equals(entity.getPartner2DanceClassST().getId(),
                        DanceClassRepository.DEFAULT_DANCE_CLASS_ID));
            }
        } else {
            dto.setHobbyCouple(false);
        }
    }
}
