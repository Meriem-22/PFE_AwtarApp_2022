package com.awtar.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A ItemValue.
 */
@Entity
@Table(name = "item_value")
public class ItemValue implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "price", nullable = false)
    private Double price;

    @NotNull
    @Column(name = "price_date", nullable = false)
    private LocalDate priceDate;

    @Column(name = "available_stock_quantity")
    private Long availableStockQuantity;

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

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ItemValue id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrice() {
        return this.price;
    }

    public ItemValue price(Double price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDate getPriceDate() {
        return this.priceDate;
    }

    public ItemValue priceDate(LocalDate priceDate) {
        this.setPriceDate(priceDate);
        return this;
    }

    public void setPriceDate(LocalDate priceDate) {
        this.priceDate = priceDate;
    }

    public Long getAvailableStockQuantity() {
        return this.availableStockQuantity;
    }

    public ItemValue availableStockQuantity(Long availableStockQuantity) {
        this.setAvailableStockQuantity(availableStockQuantity);
        return this;
    }

    public void setAvailableStockQuantity(Long availableStockQuantity) {
        this.availableStockQuantity = availableStockQuantity;
    }

    public Boolean getArchivated() {
        return this.archivated;
    }

    public ItemValue archivated(Boolean archivated) {
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

    public ItemValue item(Item item) {
        this.setItem(item);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ItemValue)) {
            return false;
        }
        return id != null && id.equals(((ItemValue) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ItemValue{" +
            "id=" + getId() +
            ", price=" + getPrice() +
            ", priceDate='" + getPriceDate() + "'" +
            ", availableStockQuantity=" + getAvailableStockQuantity() +
            ", archivated='" + getArchivated() + "'" +
            "}";
    }
}
