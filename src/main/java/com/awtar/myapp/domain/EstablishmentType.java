package com.awtar.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A EstablishmentType.
 */
@Entity
@Table(name = "establishment_type")
public class EstablishmentType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "type_name", nullable = false, unique = true)
    private String typeName;

    @Column(name = "archivated")
    private Boolean archivated;

    @OneToMany(mappedBy = "establishmentType")
    @JsonIgnoreProperties(value = { "reports", "establishmentType", "city" }, allowSetters = true)
    private Set<Establishment> establishments = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public EstablishmentType id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeName() {
        return this.typeName;
    }

    public EstablishmentType typeName(String typeName) {
        this.setTypeName(typeName);
        return this;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Boolean getArchivated() {
        return this.archivated;
    }

    public EstablishmentType archivated(Boolean archivated) {
        this.setArchivated(archivated);
        return this;
    }

    public void setArchivated(Boolean archivated) {
        this.archivated = archivated;
    }

    public Set<Establishment> getEstablishments() {
        return this.establishments;
    }

    public void setEstablishments(Set<Establishment> establishments) {
        if (this.establishments != null) {
            this.establishments.forEach(i -> i.setEstablishmentType(null));
        }
        if (establishments != null) {
            establishments.forEach(i -> i.setEstablishmentType(this));
        }
        this.establishments = establishments;
    }

    public EstablishmentType establishments(Set<Establishment> establishments) {
        this.setEstablishments(establishments);
        return this;
    }

    public EstablishmentType addEstablishment(Establishment establishment) {
        this.establishments.add(establishment);
        establishment.setEstablishmentType(this);
        return this;
    }

    public EstablishmentType removeEstablishment(Establishment establishment) {
        this.establishments.remove(establishment);
        establishment.setEstablishmentType(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EstablishmentType)) {
            return false;
        }
        return id != null && id.equals(((EstablishmentType) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EstablishmentType{" +
            "id=" + getId() +
            ", typeName='" + getTypeName() + "'" +
            ", archivated='" + getArchivated() + "'" +
            "}";
    }
}
