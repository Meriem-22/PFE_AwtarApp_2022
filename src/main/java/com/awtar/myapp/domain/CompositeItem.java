package com.awtar.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A CompositeItem.
 */
@Entity
@Table(name = "composite_item")
public class CompositeItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

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
    private Item composant;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "itemValues", "nature", "donationDetails", "composers", "composants", "donationsReceiveds", "childStatuses", "schoolLevels",
        },
        allowSetters = true
    )
    private Item composer;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CompositeItem id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public CompositeItem quantity(Integer quantity) {
        this.setQuantity(quantity);
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Boolean getArchivated() {
        return this.archivated;
    }

    public CompositeItem archivated(Boolean archivated) {
        this.setArchivated(archivated);
        return this;
    }

    public void setArchivated(Boolean archivated) {
        this.archivated = archivated;
    }

    public Item getComposant() {
        return this.composant;
    }

    public void setComposant(Item item) {
        this.composant = item;
    }

    public CompositeItem composant(Item item) {
        this.setComposant(item);
        return this;
    }

    public Item getComposer() {
        return this.composer;
    }

    public void setComposer(Item item) {
        this.composer = item;
    }

    public CompositeItem composer(Item item) {
        this.setComposer(item);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompositeItem)) {
            return false;
        }
        return id != null && id.equals(((CompositeItem) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompositeItem{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            ", archivated='" + getArchivated() + "'" +
            "}";
    }
}
