package com.awtar.myapp.domain;

import com.awtar.myapp.domain.enumeration.MaritalStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Parent.
 */
@Entity
@Table(name = "parent")
public class Parent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "annual_revenue", nullable = false)
    private Double annualRevenue;

    @Column(name = "cnss")
    private Long cnss;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "marital_status", nullable = false)
    private MaritalStatus maritalStatus;

    @NotNull
    @Column(name = "occupation", nullable = false)
    private String occupation;

    @Column(name = "deceased")
    private Boolean deceased;

    @Column(name = "date_of_death")
    private LocalDate dateOfDeath;

    @JsonIgnoreProperties(value = { "parents", "children", "head" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Family familyHead;

    @JsonIgnoreProperties(
        value = { "parent", "child", "authorizingOfficer", "tutor", "statusOfHealths", "birthPlace", "placeOfResidence" },
        allowSetters = true
    )
    @OneToOne(mappedBy = "parent")
    private Profile parentProfile;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "parents", "children", "head" }, allowSetters = true)
    private Family family;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Parent id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAnnualRevenue() {
        return this.annualRevenue;
    }

    public Parent annualRevenue(Double annualRevenue) {
        this.setAnnualRevenue(annualRevenue);
        return this;
    }

    public void setAnnualRevenue(Double annualRevenue) {
        this.annualRevenue = annualRevenue;
    }

    public Long getCnss() {
        return this.cnss;
    }

    public Parent cnss(Long cnss) {
        this.setCnss(cnss);
        return this;
    }

    public void setCnss(Long cnss) {
        this.cnss = cnss;
    }

    public MaritalStatus getMaritalStatus() {
        return this.maritalStatus;
    }

    public Parent maritalStatus(MaritalStatus maritalStatus) {
        this.setMaritalStatus(maritalStatus);
        return this;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getOccupation() {
        return this.occupation;
    }

    public Parent occupation(String occupation) {
        this.setOccupation(occupation);
        return this;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public Boolean getDeceased() {
        return this.deceased;
    }

    public Parent deceased(Boolean deceased) {
        this.setDeceased(deceased);
        return this;
    }

    public void setDeceased(Boolean deceased) {
        this.deceased = deceased;
    }

    public LocalDate getDateOfDeath() {
        return this.dateOfDeath;
    }

    public Parent dateOfDeath(LocalDate dateOfDeath) {
        this.setDateOfDeath(dateOfDeath);
        return this;
    }

    public void setDateOfDeath(LocalDate dateOfDeath) {
        this.dateOfDeath = dateOfDeath;
    }

    public Family getFamilyHead() {
        return this.familyHead;
    }

    public void setFamilyHead(Family family) {
        this.familyHead = family;
    }

    public Parent familyHead(Family family) {
        this.setFamilyHead(family);
        return this;
    }

    public Profile getParentProfile() {
        return this.parentProfile;
    }

    public void setParentProfile(Profile profile) {
        if (this.parentProfile != null) {
            this.parentProfile.setParent(null);
        }
        if (profile != null) {
            profile.setParent(this);
        }
        this.parentProfile = profile;
    }

    public Parent parentProfile(Profile profile) {
        this.setParentProfile(profile);
        return this;
    }

    public Family getFamily() {
        return this.family;
    }

    public void setFamily(Family family) {
        this.family = family;
    }

    public Parent family(Family family) {
        this.setFamily(family);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Parent)) {
            return false;
        }
        return id != null && id.equals(((Parent) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Parent{" +
            "id=" + getId() +
            ", annualRevenue=" + getAnnualRevenue() +
            ", cnss=" + getCnss() +
            ", maritalStatus='" + getMaritalStatus() + "'" +
            ", occupation='" + getOccupation() + "'" +
            ", deceased='" + getDeceased() + "'" +
            ", dateOfDeath='" + getDateOfDeath() + "'" +
            "}";
    }
}
