package com.awtar.myapp.service.dto;

import com.awtar.myapp.domain.enumeration.ItemGender;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.awtar.myapp.domain.Item} entity.
 */
public class ItemDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @Lob
    private byte[] urlPhoto;

    private String urlPhotoContentType;

    @NotNull
    private ItemGender gender;

    private Boolean composed;

    private Boolean archivated;

    private NatureDTO nature;

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

    public byte[] getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(byte[] urlPhoto) {
        this.urlPhoto = urlPhoto;
    }

    public String getUrlPhotoContentType() {
        return urlPhotoContentType;
    }

    public void setUrlPhotoContentType(String urlPhotoContentType) {
        this.urlPhotoContentType = urlPhotoContentType;
    }

    public ItemGender getGender() {
        return gender;
    }

    public void setGender(ItemGender gender) {
        this.gender = gender;
    }

    public Boolean getComposed() {
        return composed;
    }

    public void setComposed(Boolean composed) {
        this.composed = composed;
    }

    public Boolean getArchivated() {
        return archivated;
    }

    public void setArchivated(Boolean archivated) {
        this.archivated = archivated;
    }

    public NatureDTO getNature() {
        return nature;
    }

    public void setNature(NatureDTO nature) {
        this.nature = nature;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ItemDTO)) {
            return false;
        }

        ItemDTO itemDTO = (ItemDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, itemDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ItemDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", urlPhoto='" + getUrlPhoto() + "'" +
            ", gender='" + getGender() + "'" +
            ", composed='" + getComposed() + "'" +
            ", archivated='" + getArchivated() + "'" +
            ", nature=" + getNature() +
            "}";
    }
}
