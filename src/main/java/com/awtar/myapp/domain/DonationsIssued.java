package com.awtar.myapp.domain;

import com.awtar.myapp.domain.enumeration.Period;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A DonationsIssued.
 */
@Entity
@Table(name = "donations_issued")
public class DonationsIssued implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "is_validated")
    private Boolean isValidated;

    @Column(name = "validation_date")
    private LocalDate validationDate;

    @Column(name = "validation_user")
    private Long validationUser;

    @Column(name = "donations_date")
    private LocalDate donationsDate;

    @Column(name = "canceled_donations")
    private Boolean canceledDonations;

    @Column(name = "canceled_on")
    private LocalDate canceledOn;

    @Column(name = "canceled_by")
    private Long canceledBy;

    @Column(name = "cancellation_reason")
    private String cancellationReason;

    @Column(name = "recurring_donations")
    private Boolean recurringDonations;

    @Enumerated(EnumType.STRING)
    @Column(name = "periodicity")
    private Period periodicity;

    @Column(name = "recurrence")
    private Integer recurrence;

    @Column(name = "archivated")
    private Boolean archivated;

    @OneToMany(mappedBy = "donationsIssued")
    @JsonIgnoreProperties(value = { "donationsIssued", "nature", "beneficiary", "items" }, allowSetters = true)
    private Set<DonationDetails> donationsDetails = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DonationsIssued id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModel() {
        return this.model;
    }

    public DonationsIssued model(String model) {
        this.setModel(model);
        return this;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Boolean getIsValidated() {
        return this.isValidated;
    }

    public DonationsIssued isValidated(Boolean isValidated) {
        this.setIsValidated(isValidated);
        return this;
    }

    public void setIsValidated(Boolean isValidated) {
        this.isValidated = isValidated;
    }

    public LocalDate getValidationDate() {
        return this.validationDate;
    }

    public DonationsIssued validationDate(LocalDate validationDate) {
        this.setValidationDate(validationDate);
        return this;
    }

    public void setValidationDate(LocalDate validationDate) {
        this.validationDate = validationDate;
    }

    public Long getValidationUser() {
        return this.validationUser;
    }

    public DonationsIssued validationUser(Long validationUser) {
        this.setValidationUser(validationUser);
        return this;
    }

    public void setValidationUser(Long validationUser) {
        this.validationUser = validationUser;
    }

    public LocalDate getDonationsDate() {
        return this.donationsDate;
    }

    public DonationsIssued donationsDate(LocalDate donationsDate) {
        this.setDonationsDate(donationsDate);
        return this;
    }

    public void setDonationsDate(LocalDate donationsDate) {
        this.donationsDate = donationsDate;
    }

    public Boolean getCanceledDonations() {
        return this.canceledDonations;
    }

    public DonationsIssued canceledDonations(Boolean canceledDonations) {
        this.setCanceledDonations(canceledDonations);
        return this;
    }

    public void setCanceledDonations(Boolean canceledDonations) {
        this.canceledDonations = canceledDonations;
    }

    public LocalDate getCanceledOn() {
        return this.canceledOn;
    }

    public DonationsIssued canceledOn(LocalDate canceledOn) {
        this.setCanceledOn(canceledOn);
        return this;
    }

    public void setCanceledOn(LocalDate canceledOn) {
        this.canceledOn = canceledOn;
    }

    public Long getCanceledBy() {
        return this.canceledBy;
    }

    public DonationsIssued canceledBy(Long canceledBy) {
        this.setCanceledBy(canceledBy);
        return this;
    }

    public void setCanceledBy(Long canceledBy) {
        this.canceledBy = canceledBy;
    }

    public String getCancellationReason() {
        return this.cancellationReason;
    }

    public DonationsIssued cancellationReason(String cancellationReason) {
        this.setCancellationReason(cancellationReason);
        return this;
    }

    public void setCancellationReason(String cancellationReason) {
        this.cancellationReason = cancellationReason;
    }

    public Boolean getRecurringDonations() {
        return this.recurringDonations;
    }

    public DonationsIssued recurringDonations(Boolean recurringDonations) {
        this.setRecurringDonations(recurringDonations);
        return this;
    }

    public void setRecurringDonations(Boolean recurringDonations) {
        this.recurringDonations = recurringDonations;
    }

    public Period getPeriodicity() {
        return this.periodicity;
    }

    public DonationsIssued periodicity(Period periodicity) {
        this.setPeriodicity(periodicity);
        return this;
    }

    public void setPeriodicity(Period periodicity) {
        this.periodicity = periodicity;
    }

    public Integer getRecurrence() {
        return this.recurrence;
    }

    public DonationsIssued recurrence(Integer recurrence) {
        this.setRecurrence(recurrence);
        return this;
    }

    public void setRecurrence(Integer recurrence) {
        this.recurrence = recurrence;
    }

    public Boolean getArchivated() {
        return this.archivated;
    }

    public DonationsIssued archivated(Boolean archivated) {
        this.setArchivated(archivated);
        return this;
    }

    public void setArchivated(Boolean archivated) {
        this.archivated = archivated;
    }

    public Set<DonationDetails> getDonationsDetails() {
        return this.donationsDetails;
    }

    public void setDonationsDetails(Set<DonationDetails> donationDetails) {
        if (this.donationsDetails != null) {
            this.donationsDetails.forEach(i -> i.setDonationsIssued(null));
        }
        if (donationDetails != null) {
            donationDetails.forEach(i -> i.setDonationsIssued(this));
        }
        this.donationsDetails = donationDetails;
    }

    public DonationsIssued donationsDetails(Set<DonationDetails> donationDetails) {
        this.setDonationsDetails(donationDetails);
        return this;
    }

    public DonationsIssued addDonationsDetails(DonationDetails donationDetails) {
        this.donationsDetails.add(donationDetails);
        donationDetails.setDonationsIssued(this);
        return this;
    }

    public DonationsIssued removeDonationsDetails(DonationDetails donationDetails) {
        this.donationsDetails.remove(donationDetails);
        donationDetails.setDonationsIssued(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DonationsIssued)) {
            return false;
        }
        return id != null && id.equals(((DonationsIssued) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DonationsIssued{" +
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
