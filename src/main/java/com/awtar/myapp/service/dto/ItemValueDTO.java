package com.awtar.myapp.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.awtar.myapp.domain.ItemValue} entity.
 */
public class ItemValueDTO implements Serializable {

    private Long id;

    @NotNull
    private Double price;

    @NotNull
    private LocalDate priceDate;

    private Long availableStockQuantity;

    private Boolean archivated;

    private ItemDTO item;

    private Long idPrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDate getPriceDate() {
        return priceDate;
    }

    public void setPriceDate(LocalDate priceDate) {
        this.priceDate = priceDate;
    }

    public Long getAvailableStockQuantity() {
        return availableStockQuantity;
    }

    public void setAvailableStockQuantity(Long availableStockQuantity) {
        this.availableStockQuantity = availableStockQuantity;
    }

    public Boolean getArchivated() {
        return archivated;
    }

    public void setArchivated(Boolean archivated) {
        this.archivated = archivated;
    }

    public ItemDTO getItem() {
        return item;
    }

    public void setItem(ItemDTO item) {
        this.item = item;
    }

    public Long getIdPrice() {
        return idPrice;
    }

    public void setIdPrice(Long idPrice) {
        this.idPrice = idPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ItemValueDTO)) {
            return false;
        }

        ItemValueDTO itemValueDTO = (ItemValueDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, itemValueDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ItemValueDTO{" +
            "id=" + getId() +
            ", price=" + getPrice() +
            ", priceDate='" + getPriceDate() + "'" +
            ", availableStockQuantity=" + getAvailableStockQuantity() +
            ", archivated='" + getArchivated() + "'" +
            ", item=" + getItem() +
            "}";
    }
}
