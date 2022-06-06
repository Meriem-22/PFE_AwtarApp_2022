package com.awtar.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.awtar.myapp.domain.DonationDetails} entity.
 */
public class DonationDetailsDTO implements Serializable {

    private Long id;

    @NotNull
    private String description;

    private Boolean archivated;

    private DonationsIssuedDTO donationsIssued;

    private NatureDTO nature;

    private BeneficiaryDTO beneficiary;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getArchivated() {
        return archivated;
    }

    public void setArchivated(Boolean archivated) {
        this.archivated = archivated;
    }

    public DonationsIssuedDTO getDonationsIssued() {
        return donationsIssued;
    }

    public void setDonationsIssued(DonationsIssuedDTO donationsIssued) {
        this.donationsIssued = donationsIssued;
    }

    public NatureDTO getNature() {
        return nature;
    }

    public void setNature(NatureDTO nature) {
        this.nature = nature;
    }

    public BeneficiaryDTO getBeneficiary() {
        return beneficiary;
    }

    public void setBeneficiary(BeneficiaryDTO beneficiary) {
        this.beneficiary = beneficiary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DonationDetailsDTO)) {
            return false;
        }

        DonationDetailsDTO donationDetailsDTO = (DonationDetailsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, donationDetailsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DonationDetailsDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", archivated='" + getArchivated() + "'" +
            ", donationsIssued=" + getDonationsIssued() +
            ", nature=" + getNature() +
            ", beneficiary=" + getBeneficiary() +
            "}";
    }
}
