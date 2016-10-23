package by.danceform.app.domain.competition;

import by.danceform.app.domain.AbstractEntity;
import by.danceform.app.domain.document.UploadedDocument;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by dimonn12 on 23.10.2016.
 */
@Entity
public class CompetitionWithDetails extends AbstractEntity<Long> {

    @Id
    private Competition competition;

    @Id
    private UploadedDocument uploadedDocument;

    public CompetitionWithDetails(Competition competition, UploadedDocument uploadedDocument) {
        this.competition = competition;
        this.uploadedDocument = uploadedDocument;
    }

    public UploadedDocument getUploadedDocument() {
        return uploadedDocument;
    }

    public void setUploadedDocument(UploadedDocument uploadedDocument) {
        this.uploadedDocument = uploadedDocument;
    }

    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    @Override
    public void setId(Long id) {
    }

    @Override
    public Long getId() {
        return competition.getId();
    }
}
