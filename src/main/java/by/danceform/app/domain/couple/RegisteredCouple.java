package by.danceform.app.domain.couple;


import by.danceform.app.domain.SoftDeletedEntity;
import by.danceform.app.domain.competition.CompetitionCategory;
import by.danceform.app.domain.config.DanceClass;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * A RegisteredCouple.
 */
@Entity
@Table(name = "registered_couple")
public class RegisteredCouple extends SoftDeletedEntity<Long> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "partner_1_name", length = 128, nullable = false)
    private String partner1Name;

    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "partner_1_surname", length = 128, nullable = false)
    private String partner1Surname;

    @Size(min = 1, max = 128)
    @Column(name = "partner_2_name", length = 128, nullable = false)
    private String partner2Name;

    @Size(min = 1, max = 128)
    @Column(name = "partner_2_surname", length = 128, nullable = false)
    private String partner2Surname;

    @NotNull
    @Column(name = "partner_1_date_of_birth", nullable = false)
    private LocalDate partner1DateOfBirth;

    @Column(name = "partner_2_date_of_birth", nullable = false)
    private LocalDate partner2DateOfBirth;

    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "organization", length = 80, nullable = false)
    private String organization;

    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "location", length = 80, nullable = false)
    private String location;

    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "trainer_1", length = 80, nullable = false)
    private String trainer1;

    @Size(min = 1, max = 80)
    @Column(name = "trainer_2", length = 80, nullable = false)
    private String trainer2;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "partner_1_dance_class_st_id")
    private DanceClass partner1DanceClassST;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "partner_1_dance_class_la_id")
    private DanceClass partner1DanceClassLA;

    @ManyToOne
    @JoinColumn(name = "partner_2_dance_class_st_id")
    private DanceClass partner2DanceClassST;

    @ManyToOne
    @JoinColumn(name = "partner_2_dance_class_la_id")
    private DanceClass partner2DanceClassLA;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "competition_category_id")
    private CompetitionCategory competitionCategory;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getTrainer2() {
        return trainer2;
    }

    public void setTrainer2(String trainer2) {
        this.trainer2 = trainer2;
    }

    public DanceClass getPartner1DanceClassST() {
        return partner1DanceClassST;
    }

    public void setPartner1DanceClassST(DanceClass danceClass) {
        this.partner1DanceClassST = danceClass;
    }

    public DanceClass getPartner1DanceClassLA() {
        return partner1DanceClassLA;
    }

    public void setPartner1DanceClassLA(DanceClass danceClass) {
        this.partner1DanceClassLA = danceClass;
    }

    public DanceClass getPartner2DanceClassST() {
        return partner2DanceClassST;
    }

    public void setPartner2DanceClassST(DanceClass danceClass) {
        this.partner2DanceClassST = danceClass;
    }

    public DanceClass getPartner2DanceClassLA() {
        return partner2DanceClassLA;
    }

    public void setPartner2DanceClassLA(DanceClass danceClass) {
        this.partner2DanceClassLA = danceClass;
    }

    public CompetitionCategory getCompetitionCategory() {
        return competitionCategory;
    }

    public void setCompetitionCategory(CompetitionCategory competitionCategory) {
        this.competitionCategory = competitionCategory;
    }

}
