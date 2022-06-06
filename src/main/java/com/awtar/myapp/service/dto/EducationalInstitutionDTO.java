package com.awtar.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.awtar.myapp.domain.EducationalInstitution} entity.
 */
public class EducationalInstitutionDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String adress;

    private Boolean archivated;

    private CityDTO city;

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

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public Boolean getArchivated() {
        return archivated;
    }

    public void setArchivated(Boolean archivated) {
        this.archivated = archivated;
    }

    public CityDTO getCity() {
        return city;
    }

    public void setCity(CityDTO city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EducationalInstitutionDTO)) {
            return false;
        }

        EducationalInstitutionDTO educationalInstitutionDTO = (EducationalInstitutionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, educationalInstitutionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EducationalInstitutionDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", adress='" + getAdress() + "'" +
            ", archivated='" + getArchivated() + "'" +
            ", city=" + getCity() +
            "}";
    }
}
