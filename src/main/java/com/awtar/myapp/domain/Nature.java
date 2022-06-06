package com.awtar.myapp.domain;

import com.awtar.myapp.domain.enumeration.Beneficiaries;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Nature.
 */
@Entity
@Table(name = "nature")
public class Nature implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "destined_to", nullable = false)
    private Beneficiaries destinedTo;

    @Column(name = "necessity_value")
    private Boolean necessityValue;

    @Column(name = "archivated")
    private Boolean archivated;

    @OneToMany(mappedBy = "nature")
    @JsonIgnoreProperties(
        value = {
            "itemValues", "nature", "donationDetails", "composers", "composants", "donationsReceiveds", "childStatuses", "schoolLevels",
        },
        allowSetters = true
    )
    private Set<Item> items = new HashSet<>();

    @OneToMany(mappedBy = "nature")
    @JsonIgnoreProperties(value = { "donationsIssued", "nature", "beneficiary", "items" }, allowSetters = true)
    private Set<DonationDetails> donationdetails = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Nature id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Nature name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Beneficiaries getDestinedTo() {
        return this.destinedTo;
    }

    public Nature destinedTo(Beneficiaries destinedTo) {
        this.setDestinedTo(destinedTo);
        return this;
    }

    public void setDestinedTo(Beneficiaries destinedTo) {
        this.destinedTo = destinedTo;
    }

    public Boolean getNecessityValue() {
        return this.necessityValue;
    }

    public Nature necessityValue(Boolean necessityValue) {
        this.setNecessityValue(necessityValue);
        return this;
    }

    public void setNecessityValue(Boolean necessityValue) {
        this.necessityValue = necessityValue;
    }

    public Boolean getArchivated() {
        return this.archivated;
    }

    public Nature archivated(Boolean archivated) {
        this.setArchivated(archivated);
        return this;
    }

    public void setArchivated(Boolean archivated) {
        this.archivated = archivated;
    }

    public Set<Item> getItems() {
        return this.items;
    }

    public void setItems(Set<Item> items) {
        if (this.items != null) {
            this.items.forEach(i -> i.setNature(null));
        }
        if (items != null) {
            items.forEach(i -> i.setNature(this));
        }
        this.items = items;
    }

    public Nature items(Set<Item> items) {
        this.setItems(items);
        return this;
    }

    public Nature addItem(Item item) {
        this.items.add(item);
        item.setNature(this);
        return this;
    }

    public Nature removeItem(Item item) {
        this.items.remove(item);
        item.setNature(null);
        return this;
    }

    public Set<DonationDetails> getDonationdetails() {
        return this.donationdetails;
    }

    public void setDonationdetails(Set<DonationDetails> donationDetails) {
        if (this.donationdetails != null) {
            this.donationdetails.forEach(i -> i.setNature(null));
        }
        if (donationDetails != null) {
            donationDetails.forEach(i -> i.setNature(this));
        }
        this.donationdetails = donationDetails;
    }

    public Nature donationdetails(Set<DonationDetails> donationDetails) {
        this.setDonationdetails(donationDetails);
        return this;
    }

    public Nature addDonationdetails(DonationDetails donationDetails) {
        this.donationdetails.add(donationDetails);
        donationDetails.setNature(this);
        return this;
    }

    public Nature removeDonationdetails(DonationDetails donationDetails) {
        this.donationdetails.remove(donationDetails);
        donationDetails.setNature(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Nature)) {
            return false;
        }
        return id != null && id.equals(((Nature) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Nature{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", destinedTo='" + getDestinedTo() + "'" +
            ", necessityValue='" + getNecessityValue() + "'" +
            ", archivated='" + getArchivated() + "'" +
            "}";
    }
}
