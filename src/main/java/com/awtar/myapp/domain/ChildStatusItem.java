package com.awtar.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A ChildStatusItem.
 */
@Entity
@Table(name = "child_status_item")
public class ChildStatusItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "affected")
    private Boolean affected;

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
    @JsonIgnoreProperties(value = { "items" }, allowSetters = true)
    private ChildStatus childStatus;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ChildStatusItem id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getAffected() {
        return this.affected;
    }

    public ChildStatusItem affected(Boolean affected) {
        this.setAffected(affected);
        return this;
    }

    public void setAffected(Boolean affected) {
        this.affected = affected;
    }

    public Boolean getArchivated() {
        return this.archivated;
    }

    public ChildStatusItem archivated(Boolean archivated) {
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

    public ChildStatusItem item(Item item) {
        this.setItem(item);
        return this;
    }

    public ChildStatus getChildStatus() {
        return this.childStatus;
    }

    public void setChildStatus(ChildStatus childStatus) {
        this.childStatus = childStatus;
    }

    public ChildStatusItem childStatus(ChildStatus childStatus) {
        this.setChildStatus(childStatus);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChildStatusItem)) {
            return false;
        }
        return id != null && id.equals(((ChildStatusItem) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ChildStatusItem{" +
            "id=" + getId() +
            ", affected='" + getAffected() + "'" +
            ", archivated='" + getArchivated() + "'" +
            "}";
    }
}
