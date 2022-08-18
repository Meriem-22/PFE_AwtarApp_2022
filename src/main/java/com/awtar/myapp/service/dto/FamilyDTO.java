package com.awtar.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.awtar.myapp.domain.Family} entity.
 */
public class FamilyDTO implements Serializable {

    private Long id;

    @NotNull
    private String familyName;

    @NotNull
    private String dwelling;

    private String area;

    private Boolean notebookOfPoverty;

    private Boolean notebookOfHandicap;

    private Boolean archivated;

    private ProfileDTO authorizingOfficer;

    private ProfileDTO tutor;

    private BeneficiaryDTO beneficiary;

    private ParentDTO parentsDetails[];

    private ChildDTO childrenDetails[];

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getDwelling() {
        return dwelling;
    }

    public void setDwelling(String dwelling) {
        this.dwelling = dwelling;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Boolean getNotebookOfPoverty() {
        return notebookOfPoverty;
    }

    public void setNotebookOfPoverty(Boolean notebookOfPoverty) {
        this.notebookOfPoverty = notebookOfPoverty;
    }

    public Boolean getNotebookOfHandicap() {
        return notebookOfHandicap;
    }

    public void setNotebookOfHandicap(Boolean notebookOfHandicap) {
        this.notebookOfHandicap = notebookOfHandicap;
    }

    public Boolean getArchivated() {
        return archivated;
    }

    public void setArchivated(Boolean archivated) {
        this.archivated = archivated;
    }

    public ProfileDTO getAuthorizingOfficer() {
        return authorizingOfficer;
    }

    public void setAuthorizingOfficer(ProfileDTO authorizingOfficer) {
        this.authorizingOfficer = authorizingOfficer;
    }

    public ProfileDTO getTutor() {
        return tutor;
    }

    public void setTutor(ProfileDTO tutor) {
        this.tutor = tutor;
    }

    public BeneficiaryDTO getBeneficiary() {
        return beneficiary;
    }

    public void setBeneficiary(BeneficiaryDTO beneficiary) {
        this.beneficiary = beneficiary;
    }

    public ParentDTO[] getParentsDetails() {
        return parentsDetails;
    }

    public void setParentsDetails(ParentDTO[] parentsDetails) {
        this.parentsDetails = parentsDetails;
    }

    public ChildDTO[] getChildrenDetails() {
        return childrenDetails;
    }

    public void setChildrenDetails(ChildDTO[] childrenDetails) {
        this.childrenDetails = childrenDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FamilyDTO)) {
            return false;
        }

        FamilyDTO familyDTO = (FamilyDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, familyDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FamilyDTO{" +
            "id=" + getId() +
            ", familyName='" + getFamilyName() + "'" +
            ", dwelling='" + getDwelling() + "'" +
            ", area='" + getArea() + "'" +
            ", notebookOfPoverty='" + getNotebookOfPoverty() + "'" +
            ", notebookOfHandicap='" + getNotebookOfHandicap() + "'" +
            ", archivated='" + getArchivated() + "'" +
            "}";
    }
}
