package com.awtar.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.awtar.myapp.domain.ChildStatusItem} entity.
 */
public class ChildStatusItemDTO implements Serializable {

    private Long id;

    private Boolean affected;

    private Boolean archivated;

    private ItemDTO item;

    private ChildStatusDTO childStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getAffected() {
        return affected;
    }

    public void setAffected(Boolean affected) {
        this.affected = affected;
    }

    public Boolean getArchivated() {
        return archivated;
    }

    public void setArchivated(Boolean archivated) {
        this.archivated = archivated;
    }

    public ItemDTO getItem() {
        return item;
    }

    public void setItem(ItemDTO item) {
        this.item = item;
    }

    public ChildStatusDTO getChildStatus() {
        return childStatus;
    }

    public void setChildStatus(ChildStatusDTO childStatus) {
        this.childStatus = childStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChildStatusItemDTO)) {
            return false;
        }

        ChildStatusItemDTO childStatusItemDTO = (ChildStatusItemDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, childStatusItemDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ChildStatusItemDTO{" +
            "id=" + getId() +
            ", affected='" + getAffected() + "'" +
            ", archivated='" + getArchivated() + "'" +
            ", item=" + getItem() +
            ", childStatus=" + getChildStatus() +
            "}";
    }
}
