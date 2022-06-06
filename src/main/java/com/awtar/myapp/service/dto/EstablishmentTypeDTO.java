package com.awtar.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.awtar.myapp.domain.EstablishmentType} entity.
 */
public class EstablishmentTypeDTO implements Serializable {

    private Long id;

    @NotNull
    private String typeName;

    private Boolean archivated;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
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
        if (!(o instanceof EstablishmentTypeDTO)) {
            return false;
        }

        EstablishmentTypeDTO establishmentTypeDTO = (EstablishmentTypeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, establishmentTypeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EstablishmentTypeDTO{" +
            "id=" + getId() +
            ", typeName='" + getTypeName() + "'" +
            ", archivated='" + getArchivated() + "'" +
            "}";
    }
}
