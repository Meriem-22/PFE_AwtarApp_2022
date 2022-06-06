package com.awtar.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.awtar.myapp.domain.CompositeItem} entity.
 */
public class CompositeItemDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer quantity;

    private Boolean archivated;

    private ItemDTO composant;

    private ItemDTO composer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Boolean getArchivated() {
        return archivated;
    }

    public void setArchivated(Boolean archivated) {
        this.archivated = archivated;
    }

    public ItemDTO getComposant() {
        return composant;
    }

    public void setComposant(ItemDTO composant) {
        this.composant = composant;
    }

    public ItemDTO getComposer() {
        return composer;
    }

    public void setComposer(ItemDTO composer) {
        this.composer = composer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompositeItemDTO)) {
            return false;
        }

        CompositeItemDTO compositeItemDTO = (CompositeItemDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, compositeItemDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompositeItemDTO{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            ", archivated='" + getArchivated() + "'" +
            ", composant=" + getComposant() +
            ", composer=" + getComposer() +
            "}";
    }
}
