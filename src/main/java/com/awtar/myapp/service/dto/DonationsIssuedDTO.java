package com.awtar.myapp.service.dto;

import com.awtar.myapp.domain.enumeration.Period;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.awtar.myapp.domain.DonationsIssued} entity.
 */
public class DonationsIssuedDTO implements Serializable {

    private Long id;

    @NotNull
    private String model;

    private Boolean isValidated;

    private LocalDate validationDate;

    private Long validationUser;

    private LocalDate donationsDate;

    private Boolean canceledDonations;

    private LocalDate canceledOn;

    private Long canceledBy;

    private String cancellationReason;

    private Boolean recurringDonations;

    private Period periodicity;

    private Integer recurrence;

    private Boolean archivated;

    private DonationDetailsDTO donationsDetailsN[];

    private String month;

    private Long number;

    public DonationsIssuedDTO() {}

    public DonationsIssuedDTO(String month, Long number) {
        this.month = month;
        this.number = number;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Boolean getIsValidated() {
        return isValidated;
    }

    public void setIsValidated(Boolean isValidated) {
        this.isValidated = isValidated;
    }

    public LocalDate getValidationDate() {
        return validationDate;
    }

    public void setValidationDate(LocalDate validationDate) {
        this.validationDate = validationDate;
    }

    public Long getValidationUser() {
        return validationUser;
    }

    public void setValidationUser(Long validationUser) {
        this.validationUser = validationUser;
    }

    public LocalDate getDonationsDate() {
        return donationsDate;
    }

    public void setDonationsDate(LocalDate donationsDate) {
        this.donationsDate = donationsDate;
    }

    public Boolean getCanceledDonations() {
        return canceledDonations;
    }

    public void setCanceledDonations(Boolean canceledDonations) {
        this.canceledDonations = canceledDonations;
    }

    public LocalDate getCanceledOn() {
        return canceledOn;
    }

    public void setCanceledOn(LocalDate canceledOn) {
        this.canceledOn = canceledOn;
    }

    public Long getCanceledBy() {
        return canceledBy;
    }

    public void setCanceledBy(Long canceledBy) {
        this.canceledBy = canceledBy;
    }

    public String getCancellationReason() {
        return cancellationReason;
    }

    public void setCancellationReason(String cancellationReason) {
        this.cancellationReason = cancellationReason;
    }

    public Boolean getRecurringDonations() {
        return recurringDonations;
    }

    public void setRecurringDonations(Boolean recurringDonations) {
        this.recurringDonations = recurringDonations;
    }

    public Period getPeriodicity() {
        return periodicity;
    }

    public void setPeriodicity(Period periodicity) {
        this.periodicity = periodicity;
    }

    public Integer getRecurrence() {
        return recurrence;
    }

    public void setRecurrence(Integer recurrence) {
        this.recurrence = recurrence;
    }

    public Boolean getArchivated() {
        return archivated;
    }

    public void setArchivated(Boolean archivated) {
        this.archivated = archivated;
    }

    public DonationDetailsDTO[] getDonationsDetailsN() {
        return donationsDetailsN;
    }

    public void setDonationsDetailsN(DonationDetailsDTO[] donationsDetailsN) {
        this.donationsDetailsN = donationsDetailsN;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DonationsIssuedDTO)) {
            return false;
        }

        DonationsIssuedDTO donationsIssuedDTO = (DonationsIssuedDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, donationsIssuedDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DonationsIssuedDTO{" +
            "id=" + getId() +
            ", model='" + getModel() + "'" +
            ", isValidated='" + getIsValidated() + "'" +
            ", validationDate='" + getValidationDate() + "'" +
            ", validationUser=" + getValidationUser() +
            ", donationsDate='" + getDonationsDate() + "'" +
            ", canceledDonations='" + getCanceledDonations() + "'" +
            ", canceledOn='" + getCanceledOn() + "'" +
            ", canceledBy=" + getCanceledBy() +
            ", cancellationReason='" + getCancellationReason() + "'" +
            ", recurringDonations='" + getRecurringDonations() + "'" +
            ", periodicity='" + getPeriodicity() + "'" +
            ", recurrence=" + getRecurrence() +
            ", archivated='" + getArchivated() + "'" +
            "}";
    }
}
