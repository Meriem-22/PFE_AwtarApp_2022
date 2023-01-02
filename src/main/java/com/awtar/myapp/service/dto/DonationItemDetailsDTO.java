package com.awtar.myapp.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.awtar.myapp.domain.DonationItemDetails} entity.
 */
public class DonationItemDetailsDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer quantity;

    @NotNull
    private LocalDate date;

    private Boolean archivated;

    private DonationDetailsDTO donationDetails;

    private ItemDTO item;

    private Long itemsWithQuantitys[];

    private Integer quantityOfItems[];

    private Long itemsWithoutQuantitys[];

    private String name;

    private byte[] urlPhoto;

    private String urlPhotoContentType;

    private Double price;

    private Long availableStockQuantity;

    private Long bId;

    private String description;

    public DonationItemDetailsDTO() {}

    public DonationItemDetailsDTO(
        Integer quantity,
        String name,
        byte[] urlPhoto,
        String urlPhotoContentType,
        Double price,
        Long availableStockQuantity,
        Long bId,
        String description
    ) {
        this.quantity = quantity;
        this.name = name;
        this.urlPhoto = urlPhoto;
        this.urlPhotoContentType = urlPhotoContentType;
        this.price = price;
        this.availableStockQuantity = availableStockQuantity;
        this.bId = bId;
        this.description = description;
    }

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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Boolean getArchivated() {
        return archivated;
    }

    public void setArchivated(Boolean archivated) {
        this.archivated = archivated;
    }

    public DonationDetailsDTO getDonationDetails() {
        return donationDetails;
    }

    public void setDonationDetails(DonationDetailsDTO donationDetails) {
        this.donationDetails = donationDetails;
    }

    public ItemDTO getItem() {
        return item;
    }

    public void setItem(ItemDTO item) {
        this.item = item;
    }

    public Long[] getItemsWithQuantitys() {
        return itemsWithQuantitys;
    }

    public void setItemsWithQuantitys(Long[] itemsWithQuantitys) {
        this.itemsWithQuantitys = itemsWithQuantitys;
    }

    public Integer[] getQuantityOfItems() {
        return quantityOfItems;
    }

    public void setQuantityOfItems(Integer[] quantityOfItems) {
        this.quantityOfItems = quantityOfItems;
    }

    public Long[] getItemsWithoutQuantitys() {
        return itemsWithoutQuantitys;
    }

    public void setItemsWithoutQuantitys(Long[] itemsWithoutQuantitys) {
        this.itemsWithoutQuantitys = itemsWithoutQuantitys;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getAvailableStockQuantity() {
        return availableStockQuantity;
    }

    public void setAvailableStockQuantity(Long availableStockQuantity) {
        this.availableStockQuantity = availableStockQuantity;
    }

    public Long getbId() {
        return bId;
    }

    public void setbId(Long bId) {
        this.bId = bId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DonationItemDetailsDTO)) {
            return false;
        }

        DonationItemDetailsDTO donationItemDetailsDTO = (DonationItemDetailsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, donationItemDetailsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DonationItemDetailsDTO{" +
            "id=" + getId() +
            ", quantity=" + getQuantity() +
            ", date='" + getDate() + "'" +
            ", archivated='" + getArchivated() + "'" +
            ", donationDetails=" + getDonationDetails() +
            ", item=" + getItem() +
            "}";
    }
}
