package by.danceform.app.dto.competition;

/**
 * Created by Dmitry_Shanko on 10/10/2016.
 */
public class CompetitionCategoryWithDetailsDTO extends CompetitionCategoryDTO {

    private Long registeredCouplesCount;

    public CompetitionCategoryWithDetailsDTO() {
    }

    public CompetitionCategoryWithDetailsDTO(Long id) {
        super(id);
    }

    public Long getRegisteredCouplesCount() {
        return registeredCouplesCount;
    }

    public void setRegisteredCouplesCount(Long registeredCouplesCount) {
        this.registeredCouplesCount = registeredCouplesCount;
    }

}
