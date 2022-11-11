package com.awtar.myapp.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
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

    private DonationItemDetailsDTO donationItemDetails;

    private String model;

    private LocalDate validationDate;

    private LocalDate donationsDate;

    private Long itemQuantities;

    private Double totalPrice;

    private Boolean isValidated;

    private Long[] idsBeneficiary;

    public DonationDetailsDTO() {}

    public DonationDetailsDTO(Long id, String model, Boolean isValidated, LocalDate donationsDate, Long itemsNumber) {
        this.id = id;
        this.model = model;
        this.isValidated = isValidated;
        this.donationsDate = donationsDate;
        this.itemQuantities = itemsNumber;
    }

    public DonationDetailsDTO(Long id, String model, Boolean isValidated, LocalDate donationsDate) {
        this.id = id;
        this.model = model;
        this.isValidated = isValidated;
        this.donationsDate = donationsDate;
    }

    public DonationDetailsDTO(String model, Long itemsNumber) {
        this.model = model;
        this.itemQuantities = itemsNumber;
    }

    public DonationDetailsDTO(Long itemsNumber) {
        this.itemQuantities = itemsNumber;
    }

    public DonationDetailsDTO(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

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

    public DonationItemDetailsDTO getDonationItemDetails() {
        return donationItemDetails;
    }

    public void setDonationItemDetails(DonationItemDetailsDTO donationItemDetails) {
        this.donationItemDetails = donationItemDetails;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public LocalDate getValidationDate() {
        return validationDate;
    }

    public void setValidationDate(LocalDate validationDate) {
        this.validationDate = validationDate;
    }

    public LocalDate getDonationsDate() {
        return donationsDate;
    }

    public void setDonationsDate(LocalDate donationsDate) {
        this.donationsDate = donationsDate;
    }

    public Long getItemQuantities() {
        return itemQuantities;
    }

    public void setItemQuantities(Long itemsNumber) {
        this.itemQuantities = itemsNumber;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Boolean getIsValidated() {
        return isValidated;
    }

    public void setIsValidated(Boolean isValidated) {
        this.isValidated = isValidated;
    }

    public Long[] getIdsBeneficiary() {
        return idsBeneficiary;
    }

    public void setIdsBeneficiary(Long[] idsBeneficiary) {
        this.idsBeneficiary = idsBeneficiary;
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
