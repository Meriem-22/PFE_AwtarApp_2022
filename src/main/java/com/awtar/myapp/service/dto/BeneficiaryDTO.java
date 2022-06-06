package com.awtar.myapp.service.dto;

import com.awtar.myapp.domain.enumeration.Beneficiaries;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.awtar.myapp.domain.Beneficiary} entity.
 */
public class BeneficiaryDTO implements Serializable {

    private Long id;

    private String beneficiaryReference;

    @NotNull
    private Beneficiaries beneficiaryType;

    private Boolean archivated;

    private AuthorizingOfficerDTO authorizingOfficer;

    private TutorDTO tutor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBeneficiaryReference() {
        return beneficiaryReference;
    }

    public void setBeneficiaryReference(String beneficiaryReference) {
        this.beneficiaryReference = beneficiaryReference;
    }

    public Beneficiaries getBeneficiaryType() {
        return beneficiaryType;
    }

    public void setBeneficiaryType(Beneficiaries beneficiaryType) {
        this.beneficiaryType = beneficiaryType;
    }

    public Boolean getArchivated() {
        return archivated;
    }

    public void setArchivated(Boolean archivated) {
        this.archivated = archivated;
    }

    public AuthorizingOfficerDTO getAuthorizingOfficer() {
        return authorizingOfficer;
    }

    public void setAuthorizingOfficer(AuthorizingOfficerDTO authorizingOfficer) {
        this.authorizingOfficer = authorizingOfficer;
    }

    public TutorDTO getTutor() {
        return tutor;
    }

    public void setTutor(TutorDTO tutor) {
        this.tutor = tutor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BeneficiaryDTO)) {
            return false;
        }

        BeneficiaryDTO beneficiaryDTO = (BeneficiaryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, beneficiaryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BeneficiaryDTO{" +
            "id=" + getId() +
            ", beneficiaryReference='" + getBeneficiaryReference() + "'" +
            ", beneficiaryType='" + getBeneficiaryType() + "'" +
            ", archivated='" + getArchivated() + "'" +
            ", authorizingOfficer=" + getAuthorizingOfficer() +
            ", tutor=" + getTutor() +
            "}";
    }
}
