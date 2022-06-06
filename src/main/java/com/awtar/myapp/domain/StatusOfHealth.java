package com.awtar.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A StatusOfHealth.
 */
@Entity
@Table(name = "status_of_health")
public class StatusOfHealth implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "health_status_date", nullable = false)
    private LocalDate healthStatusDate;

    @Lob
    @Column(name = "url_details_attached")
    private byte[] urlDetailsAttached;

    @Column(name = "url_details_attached_content_type")
    private String urlDetailsAttachedContentType;

    @Column(name = "archivated")
    private Boolean archivated;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "parent", "child", "authorizingOfficer", "tutor", "statusOfHealths", "birthPlace", "placeOfResidence" },
        allowSetters = true
    )
    private Profile person;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "stateOfHealths" }, allowSetters = true)
    private HealthStatusCategory healthStatusCategory;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public StatusOfHealth id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getHealthStatusDate() {
        return this.healthStatusDate;
    }

    public StatusOfHealth healthStatusDate(LocalDate healthStatusDate) {
        this.setHealthStatusDate(healthStatusDate);
        return this;
    }

    public void setHealthStatusDate(LocalDate healthStatusDate) {
        this.healthStatusDate = healthStatusDate;
    }

    public byte[] getUrlDetailsAttached() {
        return this.urlDetailsAttached;
    }

    public StatusOfHealth urlDetailsAttached(byte[] urlDetailsAttached) {
        this.setUrlDetailsAttached(urlDetailsAttached);
        return this;
    }

    public void setUrlDetailsAttached(byte[] urlDetailsAttached) {
        this.urlDetailsAttached = urlDetailsAttached;
    }

    public String getUrlDetailsAttachedContentType() {
        return this.urlDetailsAttachedContentType;
    }

    public StatusOfHealth urlDetailsAttachedContentType(String urlDetailsAttachedContentType) {
        this.urlDetailsAttachedContentType = urlDetailsAttachedContentType;
        return this;
    }

    public void setUrlDetailsAttachedContentType(String urlDetailsAttachedContentType) {
        this.urlDetailsAttachedContentType = urlDetailsAttachedContentType;
    }

    public Boolean getArchivated() {
        return this.archivated;
    }

    public StatusOfHealth archivated(Boolean archivated) {
        this.setArchivated(archivated);
        return this;
    }

    public void setArchivated(Boolean archivated) {
        this.archivated = archivated;
    }

    public Profile getPerson() {
        return this.person;
    }

    public void setPerson(Profile profile) {
        this.person = profile;
    }

    public StatusOfHealth person(Profile profile) {
        this.setPerson(profile);
        return this;
    }

    public HealthStatusCategory getHealthStatusCategory() {
        return this.healthStatusCategory;
    }

    public void setHealthStatusCategory(HealthStatusCategory healthStatusCategory) {
        this.healthStatusCategory = healthStatusCategory;
    }

    public StatusOfHealth healthStatusCategory(HealthStatusCategory healthStatusCategory) {
        this.setHealthStatusCategory(healthStatusCategory);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StatusOfHealth)) {
            return false;
        }
        return id != null && id.equals(((StatusOfHealth) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StatusOfHealth{" +
            "id=" + getId() +
            ", healthStatusDate='" + getHealthStatusDate() + "'" +
            ", urlDetailsAttached='" + getUrlDetailsAttached() + "'" +
            ", urlDetailsAttachedContentType='" + getUrlDetailsAttachedContentType() + "'" +
            ", archivated='" + getArchivated() + "'" +
            "}";
    }
}
