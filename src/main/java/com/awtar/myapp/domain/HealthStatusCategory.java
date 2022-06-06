package com.awtar.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A HealthStatusCategory.
 */
@Entity
@Table(name = "health_status_category")
public class HealthStatusCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "archivated")
    private Boolean archivated;

    @OneToMany(mappedBy = "healthStatusCategory")
    @JsonIgnoreProperties(value = { "person", "healthStatusCategory" }, allowSetters = true)
    private Set<StatusOfHealth> stateOfHealths = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public HealthStatusCategory id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public HealthStatusCategory name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getArchivated() {
        return this.archivated;
    }

    public HealthStatusCategory archivated(Boolean archivated) {
        this.setArchivated(archivated);
        return this;
    }

    public void setArchivated(Boolean archivated) {
        this.archivated = archivated;
    }

    public Set<StatusOfHealth> getStateOfHealths() {
        return this.stateOfHealths;
    }

    public void setStateOfHealths(Set<StatusOfHealth> statusOfHealths) {
        if (this.stateOfHealths != null) {
            this.stateOfHealths.forEach(i -> i.setHealthStatusCategory(null));
        }
        if (statusOfHealths != null) {
            statusOfHealths.forEach(i -> i.setHealthStatusCategory(this));
        }
        this.stateOfHealths = statusOfHealths;
    }

    public HealthStatusCategory stateOfHealths(Set<StatusOfHealth> statusOfHealths) {
        this.setStateOfHealths(statusOfHealths);
        return this;
    }

    public HealthStatusCategory addStateOfHealth(StatusOfHealth statusOfHealth) {
        this.stateOfHealths.add(statusOfHealth);
        statusOfHealth.setHealthStatusCategory(this);
        return this;
    }

    public HealthStatusCategory removeStateOfHealth(StatusOfHealth statusOfHealth) {
        this.stateOfHealths.remove(statusOfHealth);
        statusOfHealth.setHealthStatusCategory(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HealthStatusCategory)) {
            return false;
        }
        return id != null && id.equals(((HealthStatusCategory) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HealthStatusCategory{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", archivated='" + getArchivated() + "'" +
            "}";
    }
}
