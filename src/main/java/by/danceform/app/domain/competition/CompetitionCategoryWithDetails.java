package by.danceform.app.domain.competition;

import by.danceform.app.domain.AbstractEntity;
import org.apache.commons.lang3.ObjectUtils;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by Dmitry_Shanko on 10/10/2016.
 */
@Entity
public class CompetitionCategoryWithDetails extends AbstractEntity<Long>
    implements Comparable<CompetitionCategoryWithDetails> {

    private static final long serialVersionUID = 1347401563146165129L;

    @Id
    private CompetitionCategory competitionCategory;

    @Id
    private Long registeredCouplesCount;

    public CompetitionCategoryWithDetails(CompetitionCategory competitionCategory, Long registeredCouplesCount) {
        this.competitionCategory = competitionCategory;
        this.registeredCouplesCount = registeredCouplesCount;
    }

    public Long getRegisteredCouplesCount() {
        return registeredCouplesCount;
    }

    public void setRegisteredCouplesCount(Long registeredCouplesCount) {
        this.registeredCouplesCount = registeredCouplesCount;
    }

    public CompetitionCategory getCompetitionCategory() {
        return competitionCategory;
    }

    public void setCompetitionCategory(CompetitionCategory competitionCategory) {
        this.competitionCategory = competitionCategory;
    }

    @Override
    public void setId(Long id) {
        competitionCategory.setId(id);
    }

    @Override
    public Long getId() {
        return competitionCategory.getId();
    }

    @Override
    public int compareTo(CompetitionCategoryWithDetails o) {
        return ObjectUtils.compare(this.getCompetitionCategory(), o.getCompetitionCategory());
    }
}
