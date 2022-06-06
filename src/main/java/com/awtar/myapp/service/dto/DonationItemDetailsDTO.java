package com.awtar.myapp.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.awtar.myapp.domain.DonationItemDetails} entity.
 */
public class DonationItemDetailsDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer quantity;

    @NotNull
    private LocalDate date;

    private Boolean archivated;

    private DonationDetailsDTO donationDetails;

    private ItemDTO item;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Boolean getArchivated() {
        return archivated;
    }

    public void setArchivated(Boolean archivated) {
        this.archivated = archivated;
    }

    public DonationDetailsDTO getDonationDetails() {
        return donationDetails;
    }

    public void setDonationDetails(DonationDetailsDTO donationDetails) {
        this.donationDetails = donationDetails;
    }

    public ItemDTO getItem() {
        return item;
    }

    public void setItem(ItemDTO item) {
        this.item = item;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DonationItemDetailsDTO)) {
            return false;
        }

        DonationItemDetailsDTO donationItemDetailsDTO = (DonationItemDetailsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, donationItemDetailsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DonationItemDetailsDTO{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            ", date='" + getDate() + "'" +
            ", archivated='" + getArchivated() + "'" +
            ", donationDetails=" + getDonationDetails() +
            ", item=" + getItem() +
            "}";
    }
}
