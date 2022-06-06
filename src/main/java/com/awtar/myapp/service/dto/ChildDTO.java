package com.awtar.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.awtar.myapp.domain.Child} entity.
 */
public class ChildDTO implements Serializable {

    private Long id;

    private FamilyDTO family;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FamilyDTO getFamily() {
        return family;
    }

    public void setFamily(FamilyDTO family) {
        this.family = family;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChildDTO)) {
            return false;
        }

        ChildDTO childDTO = (ChildDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, childDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ChildDTO{" +
            "id=" + getId() +
            ", family=" + getFamily() +
            "}";
    }
}