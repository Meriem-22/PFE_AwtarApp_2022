package com.awtar.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A DonationsReceivedItem.
 */
@Entity
@Table(name = "donations_received_item")
public class DonationsReceivedItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "archivated")
    private Boolean archivated;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "itemValues", "nature", "donationDetails", "composers", "composants", "donationsReceiveds", "childStatuses", "schoolLevels",
        },
        allowSetters = true
    )
    private Item item;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "authorizingOfficer", "items" }, allowSetters = true)
    private DonationsReceived donationsReceived;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DonationsReceivedItem id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public DonationsReceivedItem quantity(Integer quantity) {
        this.setQuantity(quantity);
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public DonationsReceivedItem date(LocalDate date) {
        this.setDate(date);
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Boolean getArchivated() {
        return this.archivated;
    }

    public DonationsReceivedItem archivated(Boolean archivated) {
        this.setArchivated(archivated);
        return this;
    }

    public void setArchivated(Boolean archivated) {
        this.archivated = archivated;
    }

    public Item getItem() {
        return this.item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public DonationsReceivedItem item(Item item) {
        this.setItem(item);
        return this;
    }

    public DonationsReceived getDonationsReceived() {
        return this.donationsReceived;
    }

    public void setDonationsReceived(DonationsReceived donationsReceived) {
        this.donationsReceived = donationsReceived;
    }

    public DonationsReceivedItem donationsReceived(DonationsReceived donationsReceived) {
        this.setDonationsReceived(donationsReceived);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DonationsReceivedItem)) {
            return false;
        }
        return id != null && id.equals(((DonationsReceivedItem) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DonationsReceivedItem{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            ", date='" + getDate() + "'" +
            ", archivated='" + getArchivated() + "'" +
            "}";
    }
}
