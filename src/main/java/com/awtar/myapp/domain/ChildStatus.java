package com.awtar.myapp.domain;

import com.awtar.myapp.domain.enumeration.Status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A ChildStatus.
 */
@Entity
@Table(name = "child_status")
public class ChildStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "staus", nullable = false)
    private Status staus;

    @Column(name = "archivated")
    private Boolean archivated;

    @OneToMany(mappedBy = "childStatus")
    @JsonIgnoreProperties(value = { "item", "childStatus" }, allowSetters = true)
    private Set<ChildStatusItem> items = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ChildStatus id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Status getStaus() {
        return this.staus;
    }

    public ChildStatus staus(Status staus) {
        this.setStaus(staus);
        return this;
    }

    public void setStaus(Status staus) {
        this.staus = staus;
    }

    public Boolean getArchivated() {
        return this.archivated;
    }

    public ChildStatus archivated(Boolean archivated) {
        this.setArchivated(archivated);
        return this;
    }

    public void setArchivated(Boolean archivated) {
        this.archivated = archivated;
    }

    public Set<ChildStatusItem> getItems() {
        return this.items;
    }

    public void setItems(Set<ChildStatusItem> childStatusItems) {
        if (this.items != null) {
            this.items.forEach(i -> i.setChildStatus(null));
        }
        if (childStatusItems != null) {
            childStatusItems.forEach(i -> i.setChildStatus(this));
        }
        this.items = childStatusItems;
    }

    public ChildStatus items(Set<ChildStatusItem> childStatusItems) {
        this.setItems(childStatusItems);
        return this;
    }

    public ChildStatus addItem(ChildStatusItem childStatusItem) {
        this.items.add(childStatusItem);
        childStatusItem.setChildStatus(this);
        return this;
    }

    public ChildStatus removeItem(ChildStatusItem childStatusItem) {
        this.items.remove(childStatusItem);
        childStatusItem.setChildStatus(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChildStatus)) {
            return false;
        }
        return id != null && id.equals(((ChildStatus) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ChildStatus{" +
            "id=" + getId() +
            ", staus='" + getStaus() + "'" +
            ", archivated='" + getArchivated() + "'" +
            "}";
    }
}
