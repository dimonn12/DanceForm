package by.danceform.app.dto.competition;

import by.danceform.app.dto.AbstractDomainDTO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;


/**
 * A DTO for the Competition entity.
 */
public class CompetitionDTO extends AbstractDomainDTO<Long> {

    @NotNull
    @Size(min = 1, max = 256)
    private String name;

    @NotNull
    private LocalDate startDate;

    private LocalDate endDate;

    private LocalDateTime registrationClosesTime;

    private boolean visible;

    private String organizer;

    @NotNull
    @Size(min = 1, max = 512)
    private String location;

    private boolean isResultsAvailable;

    private boolean isRegistrationOpen;

    private boolean isRegistrationClosed;

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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDateTime getRegistrationClosesTime() {
        return registrationClosesTime;
    }

    public void setRegistrationClosesTime(LocalDateTime registrationClosesTime) {
        this.registrationClosesTime = registrationClosesTime;
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
               ", startDate='" + startDate + "'" +
               ", endDate='" + endDate + "'" +
               ", visible='" + visible + "'" +
               ", organizer='" + organizer + "'" +
               ", location='" + location + "'" +
               '}';
    }
}
