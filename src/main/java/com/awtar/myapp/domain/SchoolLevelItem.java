package com.awtar.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A SchoolLevelItem.
 */
@Entity
@Table(name = "school_level_item")
public class SchoolLevelItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "is_school_item")
    private Boolean isSchoolItem;

    @Column(name = "quantity_needed")
    private Integer quantityNeeded;

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
    @JsonIgnoreProperties(value = { "teachingCurricula", "items" }, allowSetters = true)
    private SchoolLevel schoolLevel;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SchoolLevelItem id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsSchoolItem() {
        return this.isSchoolItem;
    }

    public SchoolLevelItem isSchoolItem(Boolean isSchoolItem) {
        this.setIsSchoolItem(isSchoolItem);
        return this;
    }

    public void setIsSchoolItem(Boolean isSchoolItem) {
        this.isSchoolItem = isSchoolItem;
    }

    public Integer getQuantityNeeded() {
        return this.quantityNeeded;
    }

    public SchoolLevelItem quantityNeeded(Integer quantityNeeded) {
        this.setQuantityNeeded(quantityNeeded);
        return this;
    }

    public void setQuantityNeeded(Integer quantityNeeded) {
        this.quantityNeeded = quantityNeeded;
    }

    public Boolean getArchivated() {
        return this.archivated;
    }

    public SchoolLevelItem archivated(Boolean archivated) {
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

    public SchoolLevelItem item(Item item) {
        this.setItem(item);
        return this;
    }

    public SchoolLevel getSchoolLevel() {
        return this.schoolLevel;
    }

    public void setSchoolLevel(SchoolLevel schoolLevel) {
        this.schoolLevel = schoolLevel;
    }

    public SchoolLevelItem schoolLevel(SchoolLevel schoolLevel) {
        this.setSchoolLevel(schoolLevel);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SchoolLevelItem)) {
            return false;
        }
        return id != null && id.equals(((SchoolLevelItem) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SchoolLevelItem{" +
            "id=" + getId() +
            ", isSchoolItem='" + getIsSchoolItem() + "'" +
            ", quantityNeeded=" + getQuantityNeeded() +
            ", archivated='" + getArchivated() + "'" +
            "}";
    }
}
