package com.awtar.myapp.service.dto;

import com.awtar.myapp.domain.enumeration.Status;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.awtar.myapp.domain.ChildStatus} entity.
 */
public class ChildStatusDTO implements Serializable {

    private Long id;

    @NotNull
    private Status staus;

    private Boolean archivated;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Status getStaus() {
        return staus;
    }

    public void setStaus(Status staus) {
        this.staus = staus;
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
        if (!(o instanceof ChildStatusDTO)) {
            return false;
        }

        ChildStatusDTO childStatusDTO = (ChildStatusDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, childStatusDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ChildStatusDTO{" +
            "id=" + getId() +
            ", staus='" + getStaus() + "'" +
            ", archivated='" + getArchivated() + "'" +
            "}";
    }
}
