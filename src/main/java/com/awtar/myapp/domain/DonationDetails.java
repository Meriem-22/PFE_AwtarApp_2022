package com.awtar.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A DonationDetails.
 */
@Entity
@Table(name = "donation_details")
public class DonationDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "archivated")
    private Boolean archivated;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "donationsDetails" }, allowSetters = true)
    private DonationsIssued donationsIssued;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "items", "donationdetails" }, allowSetters = true)
    private Nature nature;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "donationdetails", "visits", "authorizingOfficer", "tutor" }, allowSetters = true)
    private Beneficiary beneficiary;

    @OneToMany(mappedBy = "donationDetails")
    @JsonIgnoreProperties(value = { "donationDetails", "item" }, allowSetters = true)
    private Set<DonationItemDetails> items = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DonationDetails id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return this.description;
    }

    public DonationDetails description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getArchivated() {
        return this.archivated;
    }

    public DonationDetails archivated(Boolean archivated) {
        this.setArchivated(archivated);
        return this;
    }

    public void setArchivated(Boolean archivated) {
        this.archivated = archivated;
    }

    public DonationsIssued getDonationsIssued() {
        return this.donationsIssued;
    }

    public void setDonationsIssued(DonationsIssued donationsIssued) {
        this.donationsIssued = donationsIssued;
    }

    public DonationDetails donationsIssued(DonationsIssued donationsIssued) {
        this.setDonationsIssued(donationsIssued);
        return this;
    }

    public Nature getNature() {
        return this.nature;
    }

    public void setNature(Nature nature) {
        this.nature = nature;
    }

    public DonationDetails nature(Nature nature) {
        this.setNature(nature);
        return this;
    }

    public Beneficiary getBeneficiary() {
        return this.beneficiary;
    }

    public void setBeneficiary(Beneficiary beneficiary) {
        this.beneficiary = beneficiary;
    }

    public DonationDetails beneficiary(Beneficiary beneficiary) {
        this.setBeneficiary(beneficiary);
        return this;
    }

    public Set<DonationItemDetails> getItems() {
        return this.items;
    }

    public void setItems(Set<DonationItemDetails> donationItemDetails) {
        if (this.items != null) {
            this.items.forEach(i -> i.setDonationDetails(null));
        }
        if (donationItemDetails != null) {
            donationItemDetails.forEach(i -> i.setDonationDetails(this));
        }
        this.items = donationItemDetails;
    }

    public DonationDetails items(Set<DonationItemDetails> donationItemDetails) {
        this.setItems(donationItemDetails);
        return this;
    }

    public DonationDetails addItem(DonationItemDetails donationItemDetails) {
        this.items.add(donationItemDetails);
        donationItemDetails.setDonationDetails(this);
        return this;
    }

    public DonationDetails removeItem(DonationItemDetails donationItemDetails) {
        this.items.remove(donationItemDetails);
        donationItemDetails.setDonationDetails(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DonationDetails)) {
            return false;
        }
        return id != null && id.equals(((DonationDetails) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DonationDetails{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", archivated='" + getArchivated() + "'" +
            "}";
    }
}
