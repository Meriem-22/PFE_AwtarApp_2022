package com.awtar.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.awtar.myapp.domain.SchoolLevel} entity.
 */
public class SchoolLevelDTO implements Serializable {

    private Long id;

    @NotNull
    private String schoolLevel;

    private Boolean archivated;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSchoolLevel() {
        return schoolLevel;
    }

    public void setSchoolLevel(String schoolLevel) {
        this.schoolLevel = schoolLevel;
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
        if (!(o instanceof SchoolLevelDTO)) {
            return false;
        }

        SchoolLevelDTO schoolLevelDTO = (SchoolLevelDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, schoolLevelDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SchoolLevelDTO{" +
            "id=" + getId() +
            ", schoolLevel='" + getSchoolLevel() + "'" +
            ", archivated='" + getArchivated() + "'" +
            "}";
    }
}
