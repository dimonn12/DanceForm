package by.danceform.app.domain.config.enums;

import by.danceform.app.dto.NamedReferenceDTO;

import java.util.Objects;

/**
 * Created by Dmitry_Shanko on 10/17/2016.
 */
public enum DanceCategoryEnum {

    ST(-1), LA(-2);
    private Long value;

    DanceCategoryEnum(long value) {
        this.value = value;
    }

    public Long getValue() {
        return value;
    }

    public static DanceCategoryEnum valueOf(NamedReferenceDTO danceCategory) {
        for(DanceCategoryEnum danceCategoryEnum : values()) {
            if(Objects.equals(danceCategory.getId(), danceCategoryEnum.getValue())) {
                return danceCategoryEnum;
            }
        }
        for(DanceCategoryEnum danceCategoryEnum : values()) {
            if(Objects.equals(danceCategory.getName(), danceCategoryEnum.name())) {
                return danceCategoryEnum;
            }
        }
        return null;
    }

}
