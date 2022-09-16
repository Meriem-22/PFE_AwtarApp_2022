package com.awtar.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.awtar.myapp.domain.SchoolLevelItem} entity.
 */
public class SchoolLevelItemDTO implements Serializable {

    private Long id;

    private Boolean isSchoolItem;

    private Integer quantityNeeded;

    private Boolean archivated;

    private ItemDTO item;

    private SchoolLevelDTO schoolLevel;

    private String schoolLevelItem;

    public SchoolLevelItemDTO() {}

    public SchoolLevelItemDTO(String schoolLevelItem, Integer quantityNeeded) {
        this.schoolLevelItem = schoolLevelItem;
        this.quantityNeeded = quantityNeeded;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsSchoolItem() {
        return isSchoolItem;
    }

    public void setIsSchoolItem(Boolean isSchoolItem) {
        this.isSchoolItem = isSchoolItem;
    }

    public Integer getQuantityNeeded() {
        return quantityNeeded;
    }

    public void setQuantityNeeded(Integer quantityNeeded) {
        this.quantityNeeded = quantityNeeded;
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

    public SchoolLevelDTO getSchoolLevel() {
        return schoolLevel;
    }

    public void setSchoolLevel(SchoolLevelDTO schoolLevel) {
        this.schoolLevel = schoolLevel;
    }

    public String getSchoolLevelItem() {
        return schoolLevelItem;
    }

    public void setSchoolLevelItem(String schoolLevelItem) {
        this.schoolLevelItem = schoolLevelItem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SchoolLevelItemDTO)) {
            return false;
        }

        SchoolLevelItemDTO schoolLevelItemDTO = (SchoolLevelItemDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, schoolLevelItemDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SchoolLevelItemDTO{" +
            "id=" + getId() +
            ", isSchoolItem='" + getIsSchoolItem() + "'" +
            ", quantityNeeded=" + getQuantityNeeded() +
            ", archivated='" + getArchivated() + "'" +
            ", item=" + getItem() +
            ", schoolLevel=" + getSchoolLevel() +
            "}";
    }
}
