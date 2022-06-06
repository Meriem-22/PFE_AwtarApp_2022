package com.awtar.myapp.service.dto;

import com.awtar.myapp.domain.enumeration.MaritalStatus;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.awtar.myapp.domain.Parent} entity.
 */
public class ParentDTO implements Serializable {

    private Long id;

    @NotNull
    private Double annualRevenue;

    private Long cnss;

    @NotNull
    private MaritalStatus maritalStatus;

    @NotNull
    private String occupation;

    private Boolean deceased;

    private LocalDate dateOfDeath;

    private FamilyDTO familyHead;

    private FamilyDTO family;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAnnualRevenue() {
        return annualRevenue;
    }

    public void setAnnualRevenue(Double annualRevenue) {
        this.annualRevenue = annualRevenue;
    }

    public Long getCnss() {
        return cnss;
    }

    public void setCnss(Long cnss) {
        this.cnss = cnss;
    }

    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public Boolean getDeceased() {
        return deceased;
    }

    public void setDeceased(Boolean deceased) {
        this.deceased = deceased;
    }

    public LocalDate getDateOfDeath() {
        return dateOfDeath;
    }

    public void setDateOfDeath(LocalDate dateOfDeath) {
        this.dateOfDeath = dateOfDeath;
    }

    public FamilyDTO getFamilyHead() {
        return familyHead;
    }

    public void setFamilyHead(FamilyDTO familyHead) {
        this.familyHead = familyHead;
    }

    public FamilyDTO getFamily() {
        return family;
    }

    public void setFamily(FamilyDTO family) {
        this.family = family;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ParentDTO)) {
            return false;
        }

        ParentDTO parentDTO = (ParentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, parentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ParentDTO{" +
            "id=" + getId() +
            ", annualRevenue=" + getAnnualRevenue() +
            ", cnss=" + getCnss() +
            ", maritalStatus='" + getMaritalStatus() + "'" +
            ", occupation='" + getOccupation() + "'" +
            ", deceased='" + getDeceased() + "'" +
            ", dateOfDeath='" + getDateOfDeath() + "'" +
            ", familyHead=" + getFamilyHead() +
            ", family=" + getFamily() +
            "}";
    }
}
