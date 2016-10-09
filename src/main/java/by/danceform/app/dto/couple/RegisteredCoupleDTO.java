package by.danceform.app.dto.couple;

import by.danceform.app.dto.AbstractDomainDTO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;


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

    @NotNull
    @Size(min = 1, max = 128)
    private String partner2Name;

    @NotNull
    @Size(min = 1, max = 128)
    private String partner2Surname;

    @NotNull
    private LocalDate partner1DateOfBirth;

    @NotNull
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

    private Long partner1DanceClassSTId;

    private Long partner1DanceClassLAId;

    private Long partner2DanceClassSTId;

    private Long partner2DanceClassLAId;

    private Long competitionCategoryId;

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

    public Long getPartner1DanceClassSTId() {
        return partner1DanceClassSTId;
    }

    public void setPartner1DanceClassSTId(Long danceClassId) {
        this.partner1DanceClassSTId = danceClassId;
    }

    public Long getPartner1DanceClassLAId() {
        return partner1DanceClassLAId;
    }

    public void setPartner1DanceClassLAId(Long danceClassId) {
        this.partner1DanceClassLAId = danceClassId;
    }

    public Long getPartner2DanceClassSTId() {
        return partner2DanceClassSTId;
    }

    public void setPartner2DanceClassSTId(Long danceClassId) {
        this.partner2DanceClassSTId = danceClassId;
    }

    public Long getPartner2DanceClassLAId() {
        return partner2DanceClassLAId;
    }

    public void setPartner2DanceClassLAId(Long danceClassId) {
        this.partner2DanceClassLAId = danceClassId;
    }

    public Long getCompetitionCategoryId() {
        return competitionCategoryId;
    }

    public void setCompetitionCategoryId(Long competitionCategoryId) {
        this.competitionCategoryId = competitionCategoryId;
    }

}
