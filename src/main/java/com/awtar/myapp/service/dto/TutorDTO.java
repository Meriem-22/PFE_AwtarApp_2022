package com.awtar.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.awtar.myapp.domain.Tutor} entity.
 */
public class TutorDTO implements Serializable {

    private Long id;

    private String activity;

    private String manager;

    private String managerCin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(o instanceof TutorDTO)) {
            return false;
        }

        TutorDTO tutorDTO = (TutorDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tutorDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TutorDTO{" +
            "id=" + getId() +
            ", activity='" + getActivity() + "'" +
            ", manager='" + getManager() + "'" +
            ", managerCin='" + getManagerCin() + "'" +
            "}";
    }
}
