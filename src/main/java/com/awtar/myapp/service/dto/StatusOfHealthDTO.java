package com.awtar.myapp.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.awtar.myapp.domain.StatusOfHealth} entity.
 */
public class StatusOfHealthDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate healthStatusDate;

    @Lob
    private byte[] urlDetailsAttached;

    private String urlDetailsAttachedContentType;
    private Boolean archivated;

    private ProfileDTO person;

    private HealthStatusCategoryDTO healthStatusCategory;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getHealthStatusDate() {
        return healthStatusDate;
    }

    public void setHealthStatusDate(LocalDate healthStatusDate) {
        this.healthStatusDate = healthStatusDate;
    }

    public byte[] getUrlDetailsAttached() {
        return urlDetailsAttached;
    }

    public void setUrlDetailsAttached(byte[] urlDetailsAttached) {
        this.urlDetailsAttached = urlDetailsAttached;
    }

    public String getUrlDetailsAttachedContentType() {
        return urlDetailsAttachedContentType;
    }

    public void setUrlDetailsAttachedContentType(String urlDetailsAttachedContentType) {
        this.urlDetailsAttachedContentType = urlDetailsAttachedContentType;
    }

    public Boolean getArchivated() {
        return archivated;
    }

    public void setArchivated(Boolean archivated) {
        this.archivated = archivated;
    }

    public ProfileDTO getPerson() {
        return person;
    }

    public void setPerson(ProfileDTO person) {
        this.person = person;
    }

    public HealthStatusCategoryDTO getHealthStatusCategory() {
        return healthStatusCategory;
    }

    public void setHealthStatusCategory(HealthStatusCategoryDTO healthStatusCategory) {
        this.healthStatusCategory = healthStatusCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StatusOfHealthDTO)) {
            return false;
        }

        StatusOfHealthDTO statusOfHealthDTO = (StatusOfHealthDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, statusOfHealthDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StatusOfHealthDTO{" +
            "id=" + getId() +
            ", healthStatusDate='" + getHealthStatusDate() + "'" +
            ", urlDetailsAttached='" + getUrlDetailsAttached() + "'" +
            ", archivated='" + getArchivated() + "'" +
            ", person=" + getPerson() +
            ", healthStatusCategory=" + getHealthStatusCategory() +
            "}";
    }
}
