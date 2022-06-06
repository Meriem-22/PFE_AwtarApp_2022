package com.awtar.myapp.service.dto;

import com.awtar.myapp.domain.enumeration.Beneficiaries;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.awtar.myapp.domain.Nature} entity.
 */
public class NatureDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Beneficiaries destinedTo;

    private Boolean necessityValue;

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

    public Beneficiaries getDestinedTo() {
        return destinedTo;
    }

    public void setDestinedTo(Beneficiaries destinedTo) {
        this.destinedTo = destinedTo;
    }

    public Boolean getNecessityValue() {
        return necessityValue;
    }

    public void setNecessityValue(Boolean necessityValue) {
        this.necessityValue = necessityValue;
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
        if (!(o instanceof NatureDTO)) {
            return false;
        }

        NatureDTO natureDTO = (NatureDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, natureDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NatureDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", destinedTo='" + getDestinedTo() + "'" +
            ", necessityValue='" + getNecessityValue() + "'" +
            ", archivated='" + getArchivated() + "'" +
            "}";
    }
}
