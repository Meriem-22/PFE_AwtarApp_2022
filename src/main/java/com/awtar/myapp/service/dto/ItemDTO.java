package com.awtar.myapp.service.dto;

import com.awtar.myapp.domain.enumeration.ItemGender;
import java.io.Serializable;
import java.time.LocalDate;
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

    private Double price;

    private LocalDate priceDate;

    private Long availableStockQuantity;

    private Boolean isSchoolItem;

    private String schoolLevel;

    private Integer quantityNeeded;

    private Integer quantity;

    public ItemDTO() {}

    public ItemDTO(
        Long id,
        String name,
        byte[] urlPhoto,
        String urlPhotoContentType,
        ItemGender gender,
        Boolean composed,
        Double price,
        LocalDate priceDate,
        Long availableStockQuantity
    ) {
        this.id = id;
        this.name = name;
        this.urlPhoto = urlPhoto;
        this.urlPhotoContentType = urlPhotoContentType;
        this.gender = gender;
        this.composed = composed;
        this.price = price;
        this.priceDate = priceDate;
        this.price = price;
        this.availableStockQuantity = availableStockQuantity;
    }

    public ItemDTO(
        Long id,
        String name,
        byte[] urlPhoto,
        String urlPhotoContentType,
        ItemGender gender,
        Boolean composed,
        Double price,
        LocalDate priceDate,
        Long availableStockQuantity,
        Boolean isSchoolItem,
        String schoolLevel,
        Integer quantityNeeded
    ) {
        this.id = id;
        this.name = name;
        this.urlPhoto = urlPhoto;
        this.urlPhotoContentType = urlPhotoContentType;
        this.gender = gender;
        this.composed = composed;
        this.price = price;
        this.priceDate = priceDate;
        this.price = price;
        this.availableStockQuantity = availableStockQuantity;
        this.isSchoolItem = isSchoolItem;
        this.schoolLevel = schoolLevel;
        this.quantityNeeded = quantityNeeded;
    }

    public ItemDTO(
        Long id,
        String name,
        byte[] urlPhoto,
        String urlPhotoContentType,
        ItemGender gender,
        Boolean composed,
        Double price,
        LocalDate priceDate,
        Long availableStockQuantity,
        Integer quantity
    ) {
        this.id = id;
        this.name = name;
        this.urlPhoto = urlPhoto;
        this.urlPhotoContentType = urlPhotoContentType;
        this.gender = gender;
        this.composed = composed;
        this.price = price;
        this.priceDate = priceDate;
        this.price = price;
        this.availableStockQuantity = availableStockQuantity;
        this.quantity = quantity;
    }

    public ItemDTO(
        Long id,
        String name,
        byte[] urlPhoto,
        String urlPhotoContentType,
        ItemGender gender,
        Boolean composed,
        Double price,
        LocalDate priceDate,
        Long availableStockQuantity,
        Boolean isSchoolItem,
        String schoolLevel,
        Integer quantityNeeded,
        Integer quantity
    ) {
        this.id = id;
        this.name = name;
        this.urlPhoto = urlPhoto;
        this.urlPhotoContentType = urlPhotoContentType;
        this.gender = gender;
        this.composed = composed;
        this.price = price;
        this.priceDate = priceDate;
        this.price = price;
        this.availableStockQuantity = availableStockQuantity;
        this.isSchoolItem = isSchoolItem;
        this.schoolLevel = schoolLevel;
        this.quantityNeeded = quantityNeeded;
        this.quantity = quantity;
    }

    public ItemDTO(
        Long id,
        String name,
        byte[] urlPhoto,
        String urlPhotoContentType,
        ItemGender gender,
        Boolean composed,
        Integer quantity,
        LocalDate priceDate,
        Double price,
        Long availableStockQuantity,
        Boolean isSchoolItem,
        String schoolLevel,
        Integer quantityNeeded
    ) {
        this.id = id;
        this.name = name;
        this.urlPhoto = urlPhoto;
        this.urlPhotoContentType = urlPhotoContentType;
        this.gender = gender;
        this.composed = composed;
        this.price = price;
        this.priceDate = priceDate;
        this.price = price;
        this.availableStockQuantity = availableStockQuantity;
        this.isSchoolItem = isSchoolItem;
        this.schoolLevel = schoolLevel;
        this.quantityNeeded = quantityNeeded;
        this.quantity = quantity;
    }

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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDate getPriceDate() {
        return priceDate;
    }

    public void setPriceDate(LocalDate priceDate) {
        this.priceDate = priceDate;
    }

    public Long getAvailableStockQuantity() {
        return availableStockQuantity;
    }

    public void setAvailableStockQuantity(Long availableStockQuantity) {
        this.availableStockQuantity = availableStockQuantity;
    }

    public Boolean getIsSchoolItem() {
        return isSchoolItem;
    }

    public void setIsSchoolItem(Boolean isSchoolItem) {
        this.isSchoolItem = isSchoolItem;
    }

    public String getSchoolLevel() {
        return schoolLevel;
    }

    public void setSchoolLevel(String schoolLevel) {
        this.schoolLevel = schoolLevel;
    }

    public Integer getQuantityNeeded() {
        return quantityNeeded;
    }

    public void setQuantityNeeded(Integer quantityNeeded) {
        this.quantityNeeded = quantityNeeded;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
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
