package by.danceform.app.dto.competition;

import by.danceform.app.dto.AbstractDomainDTO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;


/**
 * A DTO for the Competition entity.
 */
public class CompetitionDTO extends AbstractDomainDTO<Long> {

    @NotNull
    @Size(min = 1, max = 256)
    private String name;

    @NotNull
    private LocalDate date;

    private boolean visible;

    private String organizer;

    @NotNull
    @Size(min = 1, max = 512)
    private String location;

    private boolean isResultsAvailable;

    private boolean isRegistrationOpen;

    private boolean isRegistrationClosed;

    private boolean isDetailsAvailable;

    private Long bannerImageId;

    private Long detailsDocumentId;

    public CompetitionDTO() {
    }

    public CompetitionDTO(Long id) {
        super(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isRegistrationOpen() {
        return isRegistrationOpen;
    }

    public void setRegistrationOpen(boolean registrationOpen) {
        isRegistrationOpen = registrationOpen;
    }

    public boolean isResultsAvailable() {
        return isResultsAvailable;
    }

    public void setResultsAvailable(boolean resultsAvailable) {
        isResultsAvailable = resultsAvailable;
    }

    public boolean isRegistrationClosed() {
        return isRegistrationClosed;
    }

    public void setRegistrationClosed(boolean registrationClosed) {
        isRegistrationClosed = registrationClosed;
    }

    public boolean isDetailsAvailable() {
        return isDetailsAvailable;
    }

    public void setDetailsAvailable(boolean detailsAvailable) {
        isDetailsAvailable = detailsAvailable;
    }

    public Long getDetailsDocumentId() {
        return detailsDocumentId;
    }

    public void setDetailsDocumentId(Long detailsDocumentId) {
        this.detailsDocumentId = detailsDocumentId;
    }

    public Long getBannerImageId() {
        return bannerImageId;
    }

    public void setBannerImageId(Long bannerImageId) {
        this.bannerImageId = bannerImageId;
    }

    @Override
    public String toString() {
        return "CompetitionDTO{" +
               "id=" + id +
               ", name='" + name + "'" +
               ", date='" + date + "'" +
               ", visible='" + visible + "'" +
               ", organizer='" + organizer + "'" +
               ", location='" + location + "'" +
               '}';
    }
}
