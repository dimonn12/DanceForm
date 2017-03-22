package by.danceform.app.dto.couple;

import by.danceform.app.dto.AbstractDomainDTO;
import by.danceform.app.dto.NamedReferenceDTO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;


/**
 * A DTO for the RegisteredCouple entity.
 */
public class RegisteredCoupleDTO extends AbstractDomainDTO<Long> {

    @NotNull
    @Size(min = 1, max = 128)
    private String partner1Name;

    @NotNull
    @Size(min = 1, max = 128)
    private String partner1Surname;

    @Size(max = 128)
    private String partner2Name;

    @Size(max = 128)
    private String partner2Surname;

    @NotNull
    private LocalDate partner1DateOfBirth;

    private LocalDate partner2DateOfBirth;

    @NotNull
    @Size(min = 1, max = 80)
    private String organization;

    @NotNull
    @Size(min = 1, max = 80)
    private String location;

    @NotNull
    @Size(min = 1, max = 80)
    private String trainer1;

    @Size(min = 1, max = 80)
    private String trainer2;

    private NamedReferenceDTO partner1DanceClassST;

    private NamedReferenceDTO partner1DanceClassLA;

    private NamedReferenceDTO partner2DanceClassST;

    private NamedReferenceDTO partner2DanceClassLA;

    @NotNull
    private Long competitionId;

    @NotNull
    private List<Long> competitionCategoryIds;

    private boolean soloCouple;

    private boolean hobbyCouple;

    public String getPartner1Name() {
        return partner1Name;
    }

    public void setPartner1Name(String partner1Name) {
        this.partner1Name = partner1Name;
    }

    public String getPartner1Surname() {
        return partner1Surname;
    }

    public void setPartner1Surname(String partner1Surname) {
        this.partner1Surname = partner1Surname;
    }

    public String getPartner2Name() {
        return partner2Name;
    }

    public void setPartner2Name(String partner2Name) {
        this.partner2Name = partner2Name;
    }

    public String getPartner2Surname() {
        return partner2Surname;
    }

    public void setPartner2Surname(String partner2Surname) {
        this.partner2Surname = partner2Surname;
    }

    public LocalDate getPartner1DateOfBirth() {
        return partner1DateOfBirth;
    }

    public void setPartner1DateOfBirth(LocalDate partner1DateOfBirth) {
        this.partner1DateOfBirth = partner1DateOfBirth;
    }

    public LocalDate getPartner2DateOfBirth() {
        return partner2DateOfBirth;
    }

    public void setPartner2DateOfBirth(LocalDate partner2DateOfBirth) {
        this.partner2DateOfBirth = partner2DateOfBirth;
    }

    public Long getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(Long competitionId) {
        this.competitionId = competitionId;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTrainer1() {
        return trainer1;
    }

    public void setTrainer1(String trainer1) {
        this.trainer1 = trainer1;
    }

    public String getTrainer2() {
        return trainer2;
    }

    public void setTrainer2(String trainer2) {
        this.trainer2 = trainer2;
    }

    public NamedReferenceDTO getPartner1DanceClassST() {
        return partner1DanceClassST;
    }

    public void setPartner1DanceClassST(NamedReferenceDTO danceClass) {
        this.partner1DanceClassST = danceClass;
    }

    public NamedReferenceDTO getPartner1DanceClassLA() {
        return partner1DanceClassLA;
    }

    public void setPartner1DanceClassLA(NamedReferenceDTO danceClass) {
        this.partner1DanceClassLA = danceClass;
    }

    public NamedReferenceDTO getPartner2DanceClassST() {
        return partner2DanceClassST;
    }

    public void setPartner2DanceClassST(NamedReferenceDTO danceClass) {
        this.partner2DanceClassST = danceClass;
    }

    public NamedReferenceDTO getPartner2DanceClassLA() {
        return partner2DanceClassLA;
    }

    public void setPartner2DanceClassLA(NamedReferenceDTO danceClass) {
        this.partner2DanceClassLA = danceClass;
    }

    public List<Long> getCompetitionCategoryIds() {
        return competitionCategoryIds;
    }

    public void setCompetitionCategoryIds(List<Long> competitionCategoryIds) {
        this.competitionCategoryIds = competitionCategoryIds;
    }

    public boolean isSoloCouple() {
        return soloCouple;
    }

    public void setSoloCouple(boolean soloCouple) {
        this.soloCouple = soloCouple;
    }

    public boolean isHobbyCouple() {
        return hobbyCouple;
    }

    public void setHobbyCouple(boolean hobbyCouple) {
        this.hobbyCouple = hobbyCouple;
    }
}
