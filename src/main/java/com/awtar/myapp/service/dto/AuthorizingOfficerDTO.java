package com.awtar.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.awtar.myapp.domain.AuthorizingOfficer} entity.
 */
public class AuthorizingOfficerDTO implements Serializable {

    private Long id;

    @NotNull
    private String abbreviation;

    private String activity;

    private String manager;

    private String managerCin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getManagerCin() {
        return managerCin;
    }

    public void setManagerCin(String managerCin) {
        this.managerCin = managerCin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AuthorizingOfficerDTO)) {
            return false;
        }

        AuthorizingOfficerDTO authorizingOfficerDTO = (AuthorizingOfficerDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, authorizingOfficerDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AuthorizingOfficerDTO{" +
            "id=" + getId() +
            ", abbreviation='" + getAbbreviation() + "'" +
            ", activity='" + getActivity() + "'" +
            ", manager='" + getManager() + "'" +
            ", managerCin='" + getManagerCin() + "'" +
            "}";
    }
}
