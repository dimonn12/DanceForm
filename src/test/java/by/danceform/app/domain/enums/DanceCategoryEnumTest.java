package by.danceform.app.domain.enums;

import by.danceform.app.domain.config.enums.DanceCategoryEnum;
import by.danceform.app.dto.NamedReferenceDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by Dmitry_Shanko on 1/4/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class DanceCategoryEnumTest {

    private NamedReferenceDTO namedReferenceDTO = new NamedReferenceDTO();

    @Test
    public void testValueOf() {
        namedReferenceDTO.setId(DanceCategoryEnum.LA.getValue());
        namedReferenceDTO.setName(DanceCategoryEnum.ST.name());
        DanceCategoryEnum foundById = DanceCategoryEnum.valueOf(namedReferenceDTO);
        assertThat(foundById, notNullValue());
        assertThat(foundById, equalTo(DanceCategoryEnum.LA));

        namedReferenceDTO.setId(null);
        namedReferenceDTO.setName(DanceCategoryEnum.ST.name());
        DanceCategoryEnum foundByName = DanceCategoryEnum.valueOf(namedReferenceDTO);
        assertThat(foundByName, notNullValue());
        assertThat(foundByName, equalTo(DanceCategoryEnum.ST));

        namedReferenceDTO.setId(9999L);
        namedReferenceDTO.setName("Test");
        DanceCategoryEnum notFound = DanceCategoryEnum.valueOf(namedReferenceDTO);
        assertThat(notFound, nullValue());
    }
}
