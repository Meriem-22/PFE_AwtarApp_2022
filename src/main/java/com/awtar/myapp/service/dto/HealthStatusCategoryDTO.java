package com.awtar.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.awtar.myapp.domain.HealthStatusCategory} entity.
 */
public class HealthStatusCategoryDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private Boolean archivated;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getArchivated() {
        return archivated;
    }

    public void setArchivated(Boolean archivated) {
        this.archivated = archivated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HealthStatusCategoryDTO)) {
            return false;
        }

        HealthStatusCategoryDTO healthStatusCategoryDTO = (HealthStatusCategoryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, healthStatusCategoryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "HealthStatusCategoryDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", archivated='" + getArchivated() + "'" +
            "}";
    }
}
